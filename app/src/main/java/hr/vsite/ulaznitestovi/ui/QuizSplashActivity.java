package hr.vsite.ulaznitestovi.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.activity.LoginActivity;
import hr.vsite.ulaznitestovi.local_data.TestPref;


@SuppressLint("CustomSplashScreen")
public class QuizSplashActivity extends AppCompatActivity {
    TestPref testPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        testPref = TestPref.getInstance();
        View content = findViewById(android.R.id.content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            content.getViewTreeObserver().addOnDrawListener(() -> {
            });
        }

        splashAndroid11();
    }

    public void splashAndroid11() {
        new Handler().postDelayed(() -> {
            System.out.println(testPref.getName() + " 123123123123123333333333333333");
            if (testPref.getName().isEmpty()) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
            finish();
        }, 3000);
    }
}