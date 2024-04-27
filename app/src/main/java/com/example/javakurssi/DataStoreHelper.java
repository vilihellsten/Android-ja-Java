package com.example.javakurssi;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava2.RxDataStore;

import java.util.Map;

import io.reactivex.Single;

public class DataStoreHelper {
    Activity activity;
    RxDataStore<Preferences> dataStoreRX;

    MutablePreferences mutablePreferences;

    public DataStoreHelper(Activity activity, RxDataStore<Preferences> dataStoreRX) {
        this.activity = activity;
        this.dataStoreRX = dataStoreRX;
    }

    Preferences pref_error = new Preferences() {
        @Nullable
        @Override
        public <T> T get(@NonNull Key<T> key) {
            return null;
        }

        @Override
        public <T> boolean contains(@NonNull Key<T> key) {
            return false;
        }

        @NonNull
        @Override
        public Map<Key<?>, Object> asMap() {
            return null;
        }
    };

    public boolean putIntValue(String Key, int value) {
        Preferences.Key<Integer> PREF_KEY = PreferencesKeys.intKey(Key);
        boolean returnvalue;
        Single<Preferences> updateResult = dataStoreRX.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_KEY, value);
            return Single.just(mutablePreferences);
        }).onErrorReturnItem(pref_error);
        returnvalue = updateResult.blockingGet() != pref_error;
        return returnvalue;
    }

    public int getIntValue(String key, int def) {
        Preferences.Key<Integer> PREF_KEY = PreferencesKeys.intKey(key);
        Single<Integer> value = dataStoreRX.data().firstOrError().map(prefs -> prefs.get(PREF_KEY)).onErrorReturnItem(def);
        return value.blockingGet();
    }

    public boolean putStringValue(String Key, String value) {
        boolean returnvalue;
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<Preferences> updateResult = dataStoreRX.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_KEY, value);
            return Single.just(mutablePreferences);
        }).onErrorReturnItem(pref_error);
        returnvalue = updateResult.blockingGet() != pref_error;
        return returnvalue;
    }

    public String getStringValue(String Key) {
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<String> value = dataStoreRX.data().firstOrError().map(prefs -> prefs.get(PREF_KEY)).onErrorReturnItem("null");
        return value.blockingGet();
    }}
