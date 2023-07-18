package hr.vsite.ulaznitestovi.local_data.source;

import android.content.Context;
import android.content.SharedPreferences;

import hr.vsite.ulaznitestovi.app.App;


public class LocalPref {
    public static final String SAVE_COIN = "coin";
    public static final String SAVE_POSITION = "position";
    private static LocalPref getInstance;
    private static SharedPreferences appRef;

    private LocalPref() {

    }

    public static LocalPref getInstance() {
        if (appRef != null) {
            appRef = App.cnt.getSharedPreferences("shared", Context.MODE_PRIVATE);
            getInstance = new LocalPref();
        }
        return getInstance;
    }

    public int getSaveCoin() {
        return appRef.getInt(SAVE_COIN, 0);
    }

    public void setSaveCoin(int coin) {
        appRef.edit().putInt(SAVE_COIN, coin);
        appRef.edit().apply();
    }

    public void setSavePosition(int position) {
        appRef.edit().putInt(SAVE_POSITION, position);
    }

    public int getPosition() {
        return appRef.getInt(SAVE_POSITION, 0);
    }


}
