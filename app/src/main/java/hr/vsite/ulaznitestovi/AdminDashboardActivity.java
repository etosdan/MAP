package hr.vsite.ulaznitestovi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import hr.vsite.ulaznitestovi.result.Result;
import hr.vsite.ulaznitestovi.result.ResultAdapter;
import hr.vsite.ulaznitestovi.tests.CreateTestActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button createGroupButton;
    private Button createTestButton;
    private ListView resultsListView;
    private ResultAdapter resultAdapter;
    private List<Result> resultList;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        createTestButton = findViewById(R.id.createTestButton);
        resultsListView = findViewById(R.id.resultsListView);

        firestore = FirebaseFirestore.getInstance();

        // Initialize the list of results
        resultList = new ArrayList<>();

        // Create the adapter for the results list
        resultAdapter = new ResultAdapter(this, resultList);

        // Set the adapter on the ListView
        resultsListView.setAdapter(resultAdapter);

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for creating a group
                Toast.makeText(AdminDashboardActivity.this, "Group creation clicked", Toast.LENGTH_SHORT).show();
            }
        });

        createTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for creating a test
                Toast.makeText(AdminDashboardActivity.this, "Test creation clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminDashboardActivity.this, CreateTestActivity.class);
                startActivity(intent);
            }
        });

        // Load the test names from the database
        loadTestNames();

        // Load the user scores from the database
        loadUserScores();
    }

    private void loadTestNames() {
        firestore.collection("tests")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    resultList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String testTitle = document.getString("testTitle");
                        resultList.add(new Result(testTitle, ""));
                    }
                    resultAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdminDashboardActivity.this, "Failed to load test names", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadUserScores() {
        firestore.collection("users")
                .whereEqualTo("groupID", "your_group_id")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("name");
                        double score = document.getDouble("score");
                        resultList.add(new Result(name, String.valueOf(score)));
                    }
                    resultAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdminDashboardActivity.this, "Failed to load user scores", Toast.LENGTH_SHORT).show();
                });
    }
}
