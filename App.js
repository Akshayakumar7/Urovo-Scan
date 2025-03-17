// import React, { useEffect, useState } from "react";
// import { View, Text, NativeEventEmitter, NativeModules } from "react-native";
// import debounce from "lodash/debounce";

// const { BarcodeModule } = NativeModules;

// export default function App() {
//   console.log("NativeModules", NativeModules);
//   const [barcodeValue, setBarcodeValue] = useState(null);

//   useEffect(() => {
//     if (BarcodeModule) {
//       const eventEmitter = new NativeEventEmitter(BarcodeModule);

//       const handleBarcode = debounce((value) => {
//         setBarcodeValue(value);
//         console.log("Scanned Barcode:", value);
//       }, 300);

//       const subscription = eventEmitter.addListener(
//         "barcodeScanned",
//         handleBarcode
//       );

//       return () => {
//         subscription.remove();
//         handleBarcode.cancel();
//       };
//     } else {
//       console.error("BarcodeModule is not available.", NativeModules);
//     }
//   }, []);

//   return (
//     <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
//       <Text>Barcode Value: {barcodeValue || "No barcode scanned"}</Text>
//     </View>
//   );
// }

import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  NativeEventEmitter,
  NativeModules,
  StyleSheet,
} from "react-native";
import debounce from "lodash/debounce";

const { BarcodeModule } = NativeModules;

export default function App() {
  const [barcodeValue, setBarcodeValue] = useState(null);

  useEffect(() => {
    if (BarcodeModule) {
      console.log("NativeModules loaded:", NativeModules);

      const eventEmitter = new NativeEventEmitter(BarcodeModule);

      const handleBarcode = debounce((value) => {
        if (value && typeof value === "string") {
          setBarcodeValue(value.trim());
          console.log("Scanned Barcode:", value);
        }
      }, 300);

      const subscription = eventEmitter.addListener(
        "barcodeScanned",
        handleBarcode
      );

      return () => {
        if (subscription) subscription.remove();
        handleBarcode.cancel();
      };
    } else {
      console.error(
        "❗ BarcodeModule is missing! Ensure native code is linked properly."
      );
    }
  }, []);

  console.log(NativeModules);

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Barcode Scanner</Text>
      <Text style={styles.barcodeValue}>
        {barcodeValue ? `Barcode: ${barcodeValue}` : "No barcode scanned"}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f4f4f4",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
  },
  barcodeValue: {
    fontSize: 18,
    color: "green",
  },
});
