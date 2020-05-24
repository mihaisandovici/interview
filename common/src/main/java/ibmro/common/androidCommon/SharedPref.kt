package ibmro.common.androidCommon

import android.content.Context

import android.content.SharedPreferences

class SharedPref(
        val context: Context
) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences(KEY_SHARED_PREF, Context.MODE_PRIVATE)

    var completedOnboarding: Boolean
        get() = sharedPref.getBoolean(KEY_COMPLETED_ONBOARDING, false)
        set(value) {
            val editor = sharedPref.edit()
            editor.putBoolean(KEY_COMPLETED_ONBOARDING, value)
            editor.apply()
        }

    var language: String
        get() = sharedPref.getString(KEY_LANGUAGE, "")
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(KEY_LANGUAGE, value)
            editor.apply()
        }

    var completedProfileStep: Boolean
        get() = sharedPref.getBoolean(KEY_COMPLETED_PROFILE_STEP, false)
        set(value) {
            val editor = sharedPref.edit()
            editor.putBoolean(KEY_COMPLETED_PROFILE_STEP, value)
            editor.apply()
        }

    var profile: String
        get() = sharedPref.getString(KEY_PROFILE, "")
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(KEY_PROFILE, value)
            editor.apply()
        }

    var role: String
        get() = sharedPref.getString(KEY_ROLE, "ANONYM_USER")
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(KEY_ROLE, value)
            editor.apply()
        }
}


const val KEY_COMPLETED_ONBOARDING = "KEY_COMPLETED_ONBOARDING"
const val KEY_LANGUAGE = "KEY_LANGUAGE"
const val KEY_ROLE = "KEY_ROLE"

const val KEY_SHARED_PREF = "ro.ibm.ISHQA.sharedPref"

