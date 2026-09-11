package org.telegram.messenger;

import android.content.Context;
import android.content.SharedPreferences;

public class AureliaSettings {
    private static final String PREFS_NAME = "aurelia_settings";
    private static final String KEY_ANTI_REVOKE = "anti_revoke";
    private static final String KEY_DOWNLOAD_BOOST = "download_boost";
    
    private static AureliaSettings instance;
    private SharedPreferences prefs;
    
    private AureliaSettings(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    public static synchronized AureliaSettings getInstance() {
        if (instance == null) {
            instance = new AureliaSettings(ApplicationLoader.applicationContext);
        }
        return instance;
    }
    
    public boolean isAntiRevokeEnabled() {
        return prefs.getBoolean(KEY_ANTI_REVOKE, true);
    }
    
    public void setAntiRevokeEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_ANTI_REVOKE, enabled).apply();
    }
    
    public boolean isDownloadBoostEnabled() {
        return prefs.getBoolean(KEY_DOWNLOAD_BOOST, true);
    }
    
    public void setDownloadBoostEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_DOWNLOAD_BOOST, enabled).apply();
    }
}
