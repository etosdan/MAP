package hr.vsite.ulaznitestovi.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.local_data.TestPref;

public class HomeActivity extends AppCompatActivity {
    TextView userNameHome;
    CardView cvStartQuiz, cvRule, cvHistory, cvLogout, cvAbout;
    TestPref testPref;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        String userId = getIntent().getStringExtra("userId");

        userNameHome.setText("Hello " + userId);
        cvHistory.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
        });
        cvAbout.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        });
        cvStartQuiz.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, TestOptionActivity.class));

        });
        cvLogout.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.activty_exit, findViewById(R.id.container), false);
            dialog.setContentView(view);
            view.findViewById(R.id.button_exit_no).setOnClickListener(view1 -> {
                dialog.cancel();
                dialog.dismiss();
            });
            view.findViewById(R.id.button_exit_yes).setOnClickListener(view2 -> {
                finish();
            });
            dialog.show();

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        });
        cvRule.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, RulesActivity.class));

        });
    }

    public void initView() {
        cvStartQuiz = findViewById(R.id.cvStartQuiz);
        cvRule = findViewById(R.id.cvRule);
        cvAbout = findViewById(R.id.cvEditPassword);
        cvHistory = findViewById(R.id.cvHistory);
        cvLogout = findViewById(R.id.cvLogout);
        userNameHome = (TextView) findViewById(R.id.tvUsernameHome);
        testPref = TestPref.getInstance();

    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finish();
        super.onBackPressed();
    }
}