package ibmro.ishqa.settings

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import ibmro.ishqa.App
import ibmro.common.androidCommon.KEY_SHARED_PREF
import ibmro.common.androidCommon.SharedPref
import ibmro.ishqa.MainActivity
import ibmro.ishqa.R
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*
import java.util.concurrent.TimeUnit


class SettingsActivity :
//     BaseActivity()
        AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        super.setLanguage()
        setContentView(R.layout.activity_settings)

        setLayout()

    }

    private fun setLanguagePosition(): Int {
        var languagePositon = 0
        val sharedLangPosition = App.prefs.language

        when (sharedLangPosition) {
            "" -> {
                languagePositon = 0
                settings_language_tv.text = getString(R.string.english)
            }
            "de" -> {
                languagePositon = 1
                settings_language_tv.text = getString(R.string.german)
            }
        }
        return languagePositon
    }

    private fun setLayout() {
        settings_select_language.clicks()
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe { _ ->
                    setLanguageDialog()
                }

//        setLanguageDialog()
        setLanguagePosition()
    }

    private fun setLanguageDialog() {
        val languages = arrayOf(getString(R.string.english), getString(R.string.german))
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.select_language))
                .setSingleChoiceItems(languages, setLanguagePosition()) { dialog: DialogInterface?, which: Int ->
                    when (which) {
                        0 -> {
                            setLocale("en", this)
                            recreate()
                        }
                        1 -> {
                            setLocale("de", this)
                            recreate()
                        }
                    }
                    dialog!!.dismiss()
                }
                .setNeutralButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
                .create().show()
    }

    private fun setLocale(language: String, context: Context) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        val sharedPref = App.prefs
        sharedPref.language = language


        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}
