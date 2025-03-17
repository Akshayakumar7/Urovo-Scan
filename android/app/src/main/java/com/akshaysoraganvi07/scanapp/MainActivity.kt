package com.akshaysoraganvi07.scanapp

import android.os.Build
import android.os.Bundle
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import expo.modules.ReactActivityDelegateWrapper

class MainActivity : ReactActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    // Ensure the theme is set before onCreate to support splash screen properly
    setTheme(R.style.AppTheme)
    super.onCreate(null)
  }

  // Returns the main component name registered from JavaScript
  override fun getMainComponentName(): String = "main"

  // Creates and wraps the ReactActivityDelegate with support for New Architecture
  override fun createReactActivityDelegate(): ReactActivityDelegate {
    return ReactActivityDelegateWrapper(
      this,
      BuildConfig.IS_NEW_ARCHITECTURE_ENABLED,
      object : DefaultReactActivityDelegate(
        this,
        mainComponentName,
        fabricEnabled
      ) {}
    )
  }

  // Handles back button behavior, aligning with Android S+ behavior
  override fun invokeDefaultOnBackPressed() {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
      if (!moveTaskToBack(false)) {
        super.invokeDefaultOnBackPressed()
      }
    } else {
      super.invokeDefaultOnBackPressed()
    }
  }
}
