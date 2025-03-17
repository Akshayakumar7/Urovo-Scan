const { withMainApplication, withPlugins } = require("@expo/config-plugins");

const modifyMainApplication = (config) => {
  return withMainApplication(config, (config) => {
    let fileContents = config.modResults.contents;

    // Ensure the file starts with the package declaration
    if (!fileContents.trim().startsWith("package ")) {
      const packageIndex = fileContents.indexOf("package ");
      if (packageIndex !== -1) {
        console.log(
          "Cleaning up file: removing content before package declaration."
        );
        fileContents = fileContents.slice(packageIndex);
        config.modResults.contents = fileContents;
      }
    }

    // 1. Insert the BarcodePackage import immediately after the package declaration.
    const barcodeImport = `import com.akshaysoraganvi07.scanapp.BarcodePackage`;
    const packageRegex = /^(package\s+.*\n)/m;
    if (!fileContents.includes(barcodeImport)) {
      config.modResults.contents = fileContents.replace(
        packageRegex,
        (match) => {
          return match + barcodeImport + "\n";
        }
      );
      console.log("✅ BarcodePackage import added.");
    } else {
      console.log("ℹ️ BarcodePackage import already present.");
    }

    // 2. Insert the BarcodePackage instantiation inside getPackages().
    // We want to insert: packages.add(BarcodePackage())
    const instantiation = `packages.add(BarcodePackage())`;
    if (!config.modResults.contents.includes(instantiation)) {
      // Try to insert after the marker comment if present.
      const marker =
        "// Packages that cannot be autolinked yet can be added manually here";
      if (config.modResults.contents.includes(marker)) {
        config.modResults.contents = config.modResults.contents.replace(
          marker,
          `${marker}\n    ${instantiation}`
        );
        console.log(
          "✅ BarcodePackage instantiation added after marker comment."
        );
      } else {
        // Fallback: insert before the return statement in getPackages()
        const returnRegex = /return\s+packages/m;
        if (returnRegex.test(config.modResults.contents)) {
          config.modResults.contents = config.modResults.contents.replace(
            returnRegex,
            `    ${instantiation}\n\n    return packages`
          );
          console.log(
            "✅ BarcodePackage instantiation added at fallback location."
          );
        } else {
          console.error(
            "🚨 Could not find a location to insert BarcodePackage instantiation."
          );
        }
      }
    } else {
      console.log("ℹ️ BarcodePackage instantiation already present.");
    }
    return config;
  });
};

module.exports = function withBarcodeModule(config) {
  console.log("📲 withBarcodeModule plugin is running!");
  return withPlugins(config, [modifyMainApplication]);
};
