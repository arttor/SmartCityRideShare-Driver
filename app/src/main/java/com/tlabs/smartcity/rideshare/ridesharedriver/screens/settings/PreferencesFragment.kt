package com.tlabscloud.r2b.dflow.screens.settings

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.tlabs.smartcity.rideshare.ridesharedriver.R

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val walletPreference = findPreference("wallet") as EditTextPreference
        val walletPrivateKeyPreference = findPreference("wallet_private_key") as EditTextPreference
        walletPreference.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()
        walletPrivateKeyPreference.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()
    }
}
