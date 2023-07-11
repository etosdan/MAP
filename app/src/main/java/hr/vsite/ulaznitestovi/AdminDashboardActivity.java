package hr.vsite.ulaznitestovi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import hr.vsite.ulaznitestovi.adapter.GroupAdapter;
import hr.vsite.ulaznitestovi.adapter.TestAdapter;
import hr.vsite.ulaznitestovi.models.Group;
import hr.vsite.ulaznitestovi.models.Test;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button createTestButton;
    private Button createGroupButton;
    private ListView testsListView;
    private ListView groupsListView;
    private TestAdapter testAdapter;
    private GroupAdapter groupAdapter;
    private List<Test> testList;
    private List<Group> groupList;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        createTestButton = findViewById(R.id.createTestButton);
        createGroupButton = findViewById(R.id.createGroupButton);
        testsListView = findViewById(R.id.testsListView);
        groupsListView = findViewById(R.id.groupsListView);

        firestore = FirebaseFirestore.getInstance();

        // Initialize the lists of tests and groups
        testList = new ArrayList<>();
        groupList = new ArrayList<>();

        // Create the adapters for the tests and groups lists
        testAdapter = new TestAdapter(this, testList);
        groupAdapter = new GroupAdapter(this, groupList);

        // Set the adapters on the list views
        testsListView.setAdapter(testAdapter);
        groupsListView.setAdapter(groupAdapter);

        createTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for creating a test
                Toast.makeText(AdminDashboardActivity.this, "Create Test clicked", Toast.LENGTH_SHORT).show();
                // Navigate to the test edit form
                String userId = getIntent().getStringExtra("userId");
                Intent intent = new Intent(AdminDashboardActivity.this, CreateTestActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for creating a group
                Toast.makeText(AdminDashboardActivity.this, "Create Group clicked", Toast.LENGTH_SHORT).show();
                // Navigate to the group edit form
                String userId = getIntent().getStringExtra("userId");
                Intent intent = new Intent(AdminDashboardActivity.this, GroupCreateActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        // Set click listeners for the tests and groups
        testsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected test
                Test selectedTest = testList.get(position);
                // Navigate to the test edit form with the selected test ID
                Intent intent = new Intent(AdminDashboardActivity.this, TestEditActivity.class);
                intent.putExtra("testId", selectedTest.getTestId());
                startActivity(intent);
            }
        });

        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected group
                Group selectedGroup = groupList.get(position);
                // Navigate to the group edit form with the selected group ID
                Intent intent = new Intent(AdminDashboardActivity.this, GroupEditActivity.class);
                intent.putExtra("groupId", selectedGroup.getGroupId());
                startActivity(intent);
            }
        });
        String userId = getIntent().getStringExtra("userId");
        // Load the tests and groups from the database
        loadTests(userId);
        loadGroups(userId);
    }

    private void loadTests(String userId) {
        // Clear the existing testList
        testList.clear();

        // Query the tests collection where the authorId is equal to the userId
        FirebaseFirestore.getInstance().collection("tests")
                .whereEqualTo("authorId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Iterate through the query results
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Get the test data from the document
                        String testName = document.getString("testName");

                        // Create a new Test object
                        Test test = new Test(testName);

                        // Add the test to the testList
                        testList.add(test);
                    }

                    // Notify the testAdapter of the data set change
                    testAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdminDashboardActivity.this, "Failed to load tests", Toast.LENGTH_SHORT).show();
                });
    }


    // ...

    private void loadGroups(String userId) {
        // Clear the existing groupList
        groupList.clear();

        // Query the groups collection where the authorId is equal to the userId
        FirebaseFirestore.getInstance().collection("groups")
                .whereEqualTo("authorId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Clear the existing groupList
                    groupList.clear();

                    // Iterate through the query results
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Get the group details from the document
                        String groupId = document.getId();
                        String groupName = document.getString("groupName");
                        String university = document.getString("university");
                        String authorId = document.getString("authorId");
                        List<String> testIds = (List<String>) document.get("testIds");
                        List<String> userIds = (List<String>) document.get("userIds");

                        // Create a new Group object with the retrieved details
                        Group group = new Group(groupName, university, authorId, userIds);
                        group.setGroupId(groupId);
                        group.setTestIds(testIds);

                        // Add the group to the groupList
                        groupList.add(group);
                    }

                    // Notify the groupAdapter of the data set change
                    groupAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle the failure to fetch the group documents
                    // Display an error message or perform any necessary error handling
                });
    }
}

