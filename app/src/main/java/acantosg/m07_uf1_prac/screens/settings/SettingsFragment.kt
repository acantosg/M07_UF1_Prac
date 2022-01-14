package acantosg.m07_uf1_prac.screens.settings

import acantosg.m07_uf1_prac.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

var countertype = true
var nickname = ""

/*
*
* https://developer.android.com/guide/topics/ui/settings/use-saved-values
*
* */
class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        var sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences(requireContext().packageName + "_preferences", Context.MODE_PRIVATE)
        countertype = sharedPreferences?.getBoolean("countertype", true)!!
        nickname = sharedPreferences?.getString("nickname", "Nombre")!!
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        //solamente el tipo de cronómetro y el nickname se actualizan en mitad de partida
        if (key.equals("countertype")) {
            countertype = sharedPreferences?.getBoolean(key, true)!!
        } else if (key.equals("nickname")) {
            sharedPreferences?.getString(key, "Nombre")!!
        }
    }

}