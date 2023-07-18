package hr.vsite.ulaznitestovi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.helpers.Constants;
import hr.vsite.ulaznitestovi.helpers.Utils;
import hr.vsite.ulaznitestovi.local_data.TestPref;
import hr.vsite.ulaznitestovi.models.HistoryModel;
import nl.dionsegijn.konfetti.xml.KonfettiView;


public class FinalResultActivity extends AppCompatActivity {
    private final TestPref testPref = TestPref.getInstance();
    HistoryModel historyModel;
    KonfettiView konfettiView;
    private TextView tvSubject, tvCorrect, tvIncorrect, tvDate, tvWellDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);
        initView();
        onBackPressed();
        Intent intent = getIntent();
        int correctAnswer = intent.getIntExtra(Constants.CORRECT, 0);
        int incorrectAnswer = intent.getIntExtra(Constants.INCORRECT, 0);
        String subject = intent.getStringExtra(Constants.SUBJECT);
        int earnedPoints = (correctAnswer * Constants.CORRECT_POINT) - (incorrectAnswer * Constants.INCORRECT_POINT);
        TextView textView = findViewById(R.id.result_);

        historyModel = new HistoryModel(Calendar.getInstance().getTimeInMillis(), subject, correctAnswer, incorrectAnswer, earnedPoints);
        if (historyModel.getEarned() < 0) {
            historyModel.setEarned(0);
        }
        if (historyModel.getOverallPoints() < 0) {
            historyModel.setOverallPoints(0);

        }
        historyModel.setOverallPoints(testPref.getPoint() + historyModel.getEarned());
        testPref.savePoint(testPref.getPoint() + historyModel.getEarned());
        if (!intent.getStringExtra(Constants.TYPE).equals("history")) {
            saveSharedLocalData();
            textView.setText("Final Result");

        } else {
            textView.setText("History Result");
        }
        displayData(historyModel);
        finishAgain();
    }

    public void finishAgain() {
        findViewById(R.id.btnFinishQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalResultActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void saveSharedLocalData() {
        Type type = new TypeToken<ArrayList<HistoryModel>>() {
        }.getType();
        ArrayList<HistoryModel> historyModelArrayList;
        Gson gson = new Gson();
        if (testPref.getHistoryQuiz().isEmpty()) {
            historyModelArrayList = new ArrayList<>();
        } else {
            historyModelArrayList = new ArrayList<>(gson.fromJson(testPref.getHistoryQuiz(), type));
        }
        historyModelArrayList.add(historyModel);
        String toJson = gson.toJson(historyModelArrayList, type);
        testPref.historyQuiz(toJson);
    }

    public void initView() {
//        konfettiView = findViewById(R.id.viewKonfetti);
        tvSubject = findViewById(R.id.textView16);
        tvCorrect = findViewById(R.id.textView19);
        tvIncorrect = findViewById(R.id.textView27);
        tvDate = findViewById(R.id.textView30);
        tvWellDone = findViewById(R.id.tvWellDone);
    }

    private void displayData(HistoryModel attempt) {
//        final int containerMiddleX = konfettiView.getWidth() / 2;
//        final int containerMiddleY = konfettiView.getHeight() / 2;
//        final ConfettiSource confettiSource = new ConfettiSource(containerMiddleX, containerMiddleY);


        tvSubject.setText(attempt.getSubject());
        tvCorrect.setText(String.valueOf(attempt.getCorrect()));
        tvIncorrect.setText(String.valueOf(attempt.getIncorrect()));
        if (attempt.getCorrect() > 5) {


            tvWellDone.setText("Ajoyib," + testPref.getName());
        } else {
            tvWellDone.setText("Yomon," + testPref.getName());

        }
        tvDate.setText(Utils.formatDate(attempt.getCreatedTime()));

    }

    public void onBackPressed() {
        findViewById(R.id.imageViewFinalResultQuiz).setOnClickListener(view -> {
            Intent intent = new Intent(FinalResultActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}