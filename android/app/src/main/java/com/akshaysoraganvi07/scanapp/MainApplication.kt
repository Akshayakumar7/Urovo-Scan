package com.akshaysoraganvi07.scanapp

import android.app.Application
import android.content.Context
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.soloader.SoLoader

class MainApplication : Application(), ReactApplication {

    private val mReactNativeHost = object : ReactNativeHost(this) {
        override fun getUseDeveloperSupport() = BuildConfig.DEBUG

        override fun getPackages(): List<ReactPackage> {
            return PackageList(this).packages
        }
    }

    override fun getReactNativeHost() = mReactNativeHost

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        // Initialize Flipper only in Debug mode
        if (BuildConfig.DEBUG) {
            try {
                val className = "com.akshaysoraganvi07.scanapp.ReactNativeFlipper"
                val clazz = Class.forName(className)
                clazz.getMethod("initializeFlipper", Context::class.java, ReactInstanceManager::class.java)
                    .invoke(null, this, mReactNativeHost.reactInstanceManager)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
