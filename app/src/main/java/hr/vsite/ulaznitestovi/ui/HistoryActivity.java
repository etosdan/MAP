package hr.vsite.ulaznitestovi.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.adapter.HistoryAdapter;
import hr.vsite.ulaznitestovi.local_data.TestPref;
import hr.vsite.ulaznitestovi.models.HistoryModel;

public class HistoryActivity extends AppCompatActivity {
    TestPref testPref;
    private RecyclerView rvHistory;
    private ArrayList<HistoryModel> historyModelList;
    private TextView tvTotalPoints, tvTotalQuestions;
    private int overallPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        findViewById(R.id.imageViewHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Collections.sort(loadData(), new AttemptCreatedTimeComparator());

        if (historyModelList.size() == 0) {
            findViewById(R.id.placeHolder).setVisibility(View.VISIBLE);
        } else {
            HistoryAdapter adapter = new HistoryAdapter(historyModelList, this);
            rvHistory.setAdapter(adapter);
            tvTotalQuestions.setText(String.valueOf(historyModelList.size()));
            for (HistoryModel userWithAttempts : historyModelList) {
                overallPoints += userWithAttempts.getEarned();
            }
            tvTotalPoints.setText(String.valueOf(overallPoints));

        }


    }

    public void initView() {
        testPref = TestPref.getInstance();
        rvHistory = findViewById(R.id.rvHistory);
        tvTotalQuestions = findViewById(R.id.tvTotalQuestionsHistory);
        tvTotalPoints = findViewById(R.id.tvOverAllPointsHistory);

    }

    private ArrayList<HistoryModel> loadData() {

        historyModelList = new ArrayList<HistoryModel>();
        Gson gson = new Gson();
        String gsonString = testPref.getHistoryQuiz();
        Type type = new TypeToken<ArrayList<HistoryModel>>() {
        }.getType();
        if (!gsonString.isEmpty()) {
            historyModelList = gson.fromJson(gsonString, type);

        }
        return historyModelList;
    }

    public class AttemptCreatedTimeComparator implements Comparator<HistoryModel> {

        @Override
        public int compare(HistoryModel attempt, HistoryModel t1) {
            return String.valueOf(t1.getCreatedTime()).compareTo(String.valueOf(attempt.getCreatedTime()));
        }
    }
}