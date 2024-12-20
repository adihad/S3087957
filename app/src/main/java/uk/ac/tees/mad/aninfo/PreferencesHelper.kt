package uk.ac.tees.mad.aninfo

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun isBiometricAuthEnabled(): Boolean {
        return sharedPreferences.getBoolean("biometric_auth_enabled", false)
    }

    fun setBiometricAuthEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("biometric_auth_enabled", enabled).apply()
    }
}