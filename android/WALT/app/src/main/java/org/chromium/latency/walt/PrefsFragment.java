package org.chromium.latency.walt;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;


public class PrefsFragment extends PreferenceFragmentCompat {


    public PrefsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String s) {
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        //
        EditTextPreference pref_timesToBlink = (EditTextPreference) getPreferenceScreen().findPreference("pref_screen_reps");

        pref_timesToBlink.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Boolean rtnval = true;
                if (newValue == "") {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Invalid Input");
                    builder.setMessage("Must be a valid number");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.show();
                    rtnval = false;
                }
                return rtnval;
            }
        });
    }

}
