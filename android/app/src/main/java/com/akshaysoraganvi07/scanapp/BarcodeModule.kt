package com.akshaysoraganvi07.scanapp

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.modules.core.DeviceEventManagerModule

@ReactModule(name = "BarcodeModule")
class BarcodeModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    init {
        Log.d("BarcodeModule", "BarcodeModule initialized")
    }

    override fun getName(): String {
        Log.d("BarcodeModule", "getName() called")
        return "BarcodeModule"
    }

    fun sendBarcode(barcodeValue: String) {
        Log.d("BarcodeModule", "sendBarcode() called with: $barcodeValue")
        reactApplicationContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit("barcodeScanned", barcodeValue)
        Log.d("BarcodeModule", "barcodeScanned event emitted")
    }
}
