package hr.vsite.ulaznitestovi.local_data.source;

import android.content.Context;
import android.content.SharedPreferences;

public class Base {
    public static final String SAVE_COIN = "coin";
    public static final String SAVE_HEART = "level";
    private static Base base;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    private Base(Context context) {
        String SHARED_PREFERENCES_NAME = "shared";
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public static Base getInstance() {
        return base;
    }

    public static void init(Context context) {
        if (base == null) {
            base = new Base(context);
        }

    }

    public int getSaveCoin() {
        return sharedPreferences.getInt(SAVE_COIN, 0);
    }

    public void setSaveCoin(int coin) {
        editor.putInt(SAVE_COIN, coin);
        editor.apply();
    }

    public int getSaveHeart() {
        return sharedPreferences.getInt(SAVE_HEART, 2);
    }

    public void setSaveHeart(int level) {
        editor.putInt(SAVE_HEART, level);
        editor.apply();
    }


}
