package com.akshaysoraganvi07.scanapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.modules.core.DeviceEventManagerModule

class BarcodeReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BarcodeReceiver"
        private const val ACTION_DECODE_EVENT = "com.ubx.datawedge.SCANNER_DECODE_EVENT"
        private const val ACTION_KEYCODE_SCAN = "ACTION_KEYCODE_SCAN_PRESSED"
        private const val ACTION_DECODE_DATA = "android.intent.ACTION_DECODE_DATA"
        private const val ACTION_UROVO = "urovo.rcv.message"

        private const val EXTRA_BARCODE = "scanned_barcode"
        private const val EXTRA_SCAN_DATA = "EXTRA_SCAN_DATA"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return

        val barcodeValue = when (intent.action) {
            ACTION_DECODE_EVENT -> intent.getStringExtra(EXTRA_BARCODE)
            ACTION_DECODE_DATA -> intent.getStringExtra(EXTRA_SCAN_DATA)
            ACTION_UROVO -> intent.getStringExtra(EXTRA_SCAN_DATA)
            ACTION_KEYCODE_SCAN -> {
                Log.d(TAG, "KEYCODE_SCAN_PRESSED received")
                null
            }
            else -> {
                Log.w(TAG, "Unknown intent action: ${intent.action}")
                null
            }
        }

        barcodeValue?.let {
            Log.d(TAG, "Barcode value: $it")
            sendBarcodeToReactNative(context, it)
        } ?: Log.e(TAG, "No barcode data found in intent.")
    }

    private fun sendBarcodeToReactNative(context: Context?, barcodeValue: String) {
        val reactContext = context?.applicationContext as? ReactApplicationContext
        if (reactContext != null) {
            reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit("barcodeScanned", barcodeValue)
        } else {
            Log.e(TAG, "Context is not ReactApplicationContext")
        }
    }
}
