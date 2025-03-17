package expo.modules.mymodule

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import java.net.URL
import android.util.Log // Import the Log class

class MyModule : Module() {
    override fun definition() = ModuleDefinition {
        Name("MyModule")

        Constants(
            "PI" to Math.PI
        )

        Events("onChange")

        Function("hello") {
            Log.d("MyModule", "Function hello called") // Log added here
            "Hello world! 👋"
        }

        AsyncFunction("setValueAsync") { value: String ->
            Log.d("MyModule", "AsyncFunction setValueAsync called with value: $value") // Log added here
            sendEvent("onChange", mapOf(
                "value" to value
            ))
        }

        View(MyModuleView::class) {
            Prop("url") { view: MyModuleView, url: URL ->
                Log.d("MyModule", "Prop url set with URL: $url") // Log added here
                view.webView.loadUrl(url.toString())
            }
            Events("onLoad")
        }

        Log.d("MyModule", "MyModule definition initialized") // Log added here for module initialization
    }
}