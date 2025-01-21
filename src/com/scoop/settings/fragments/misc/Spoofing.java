/*
 * Copyright (C) 2019-2024 The Evolution X Project
 * SPDX-License-Identifier: Apache-2.0
 */
package com.scoop.settings.fragments.misc;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.scoop.settings.preferences.SystemPropertySwitchPreference;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.search.SearchIndexable;

import java.util.List;

@SearchIndexable
public class Spoofing extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "Spoofing";
    private static final String KEY_SYSTEM_WIDE_CATEGORY = "spoofing_system_wide_category";
    private static final String KEY_PIXEL_PROPS = "persist.sys.pphooks.enable";

    private PreferenceCategory mSystemWideCategory;
    private SystemPropertySwitchPreference mPixelPropsSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.spoofing);

        final PreferenceScreen prefScreen = getPreferenceScreen();

        mSystemWideCategory = findPreference(KEY_SYSTEM_WIDE_CATEGORY);
        if (mSystemWideCategory != null) {
            mPixelPropsSwitch = findPreference(KEY_PIXEL_PROPS);
        }

        boolean enablePixelProps = getContext().getResources().getBoolean(
                com.android.internal.R.bool.config_enablePixelProps);
        if (mPixelPropsSwitch != null && !enablePixelProps) {
            mSystemWideCategory.removePreference(mPixelPropsSwitch);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.SCOOP;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.spoofing);
}
