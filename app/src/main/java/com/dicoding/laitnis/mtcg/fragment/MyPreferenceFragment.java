package com.dicoding.laitnis.mtcg.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.dicoding.laitnis.mtcg.R;
import com.dicoding.laitnis.mtcg.receiver.DailyReminderReceiver;
import com.dicoding.laitnis.mtcg.receiver.ReleaseTodayReceiver;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private String DAILY, RELEASE, LANGUAGE;
    private SwitchPreference dailyPref, releasePref;
    private Preference langaugePref;

    private DailyReminderReceiver dailyReminderReceiver = new DailyReminderReceiver();
    private ReleaseTodayReceiver releaseTodayReceiver = new ReleaseTodayReceiver();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummaries();
    }


    private void init() {
        DAILY = getResources().getString(R.string.key_daily);
        RELEASE = getResources().getString(R.string.key_release);
        LANGUAGE = getResources().getString(R.string.key_language);

        langaugePref = findPreference(LANGUAGE);
        dailyPref = findPreference(DAILY);
        releasePref = findPreference(RELEASE);

        langaugePref.setOnPreferenceClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(DAILY)) {
            boolean dailyChecked = sharedPreferences.getBoolean(DAILY, false);
            dailyPref.setChecked(dailyChecked);

            if (dailyChecked) {
                dailyReminderReceiver.setDailyReminder(getActivity());
                Toast.makeText(getActivity(), getResources().getString(R.string.daily_checked_toast), Toast.LENGTH_SHORT).show();
            } else {
                dailyReminderReceiver.cancelAlarm(getActivity());
                Toast.makeText(getActivity(), getResources().getString(R.string.daily_unchecked_toast), Toast.LENGTH_SHORT).show();

            }

        }

        if (key.equals(RELEASE)) {
            boolean releaseChecked = sharedPreferences.getBoolean(RELEASE, false);
            releasePref.setChecked(releaseChecked);

            if (releaseChecked) {
                releaseTodayReceiver.setReleaseReminder(getActivity());
            } else {
                releaseTodayReceiver.cancelAlarm(getActivity());
            }
        }


    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        String key = preference.getKey();

        if (key.equals(LANGUAGE)) {
            Intent sIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(sIntent);
        }

        return false;
    }

    private void setSummaries() {
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        boolean dailyChecked = sharedPreferences.getBoolean(DAILY, false);

        if (dailyChecked) {
            dailyReminderReceiver.setDailyReminder(getActivity());
        } else {
            dailyReminderReceiver.cancelAlarm(getActivity());
        }

        boolean releaseChecked = sharedPreferences.getBoolean(RELEASE, false);

        if (releaseChecked) {
            releaseTodayReceiver.setReleaseReminder(getActivity());
        } else {
            releaseTodayReceiver.cancelAlarm(getActivity());
        }
    }

}
