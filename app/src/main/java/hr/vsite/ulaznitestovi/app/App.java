package hr.vsite.ulaznitestovi.app;

import android.app.Application;
import android.content.Context;

import hr.vsite.ulaznitestovi.local_data.TestPref;
import hr.vsite.ulaznitestovi.local_data.source.Base;

public class App extends Application {
    public static Context cnt;

    @Override
    public void onCreate() {
        super.onCreate();
        cnt = this;
        Base.init(this);
        TestPref.init(this);
    }
}
