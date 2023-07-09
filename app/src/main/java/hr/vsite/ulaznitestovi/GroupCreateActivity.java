package hr.vsite.ulaznitestovi;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hr.vsite.ulaznitestovi.adapter.CheckBoxListAdapter;
import hr.vsite.ulaznitestovi.adapter.CreateGroupCallback;
import hr.vsite.ulaznitestovi.models.Group;
import hr.vsite.ulaznitestovi.models.User;
import hr.vsite.ulaznitestovi.repository.GroupRepository;
import hr.vsite.ulaznitestovi.repository.UserRepository;

public class GroupCreateActivity extends AppCompatActivity {

    private EditText groupNameEditText;
    private Spinner universitySpinner;
    private Button createUserGroupButton;
    private CheckBoxListAdapter checkBoxListAdapter;
    private List<User> userList;
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);

        groupNameEditText = findViewById(R.id.groupNameEditText);
        universitySpinner = findViewById(R.id.universitySpinner);
        createUserGroupButton = findViewById(R.id.createUserGroupButton);

        // Populate the university spinner with the available options
        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this, R.array.university_array, android.R.layout.simple_spinner_item);
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universitySpinner.setAdapter(universityAdapter);

        // Initialize the repositories
        userRepository = new UserRepository();
        groupRepository = new GroupRepository();

        // Set up the checkbox list adapter for selecting users
        userList = new ArrayList<>();
        checkBoxListAdapter = new CheckBoxListAdapter(this, userList);
        RecyclerView userRecyclerView = findViewById(R.id.userRecyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(checkBoxListAdapter);

        // Retrieve users by university and update the list
        String selectedUniversity = getSelectedUniversity();
        getUsersByUniversity(selectedUniversity);

        createUserGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
            }
        });
    }

    private String getSelectedUniversity() {
        return universitySpinner.getSelectedItem().toString();
    }

    private void getUsersByUniversity(String university) {
        userRepository.getUsersByUniversity(university, new UserRepository.UserRepositoryCallback() {
            @Override
            public void onUsersRetrieved(List<User> users) {
                userList.clear();
                userList.addAll(users);
                checkBoxListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(GroupCreateActivity.this, "Failed to retrieve users: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createGroup() {
        String groupName = groupNameEditText.getText().toString();
        String university = getSelectedUniversity();
        List<String> selectedUserIds = checkBoxListAdapter.getSelectedUserIds();

        String authorId = getIntent().getStringExtra("userId");

        // Create a new Group object with the provided data
        Group group = new Group(groupName, university, authorId, selectedUserIds);

        // Save the group to the database
        groupRepository.saveGroup(group, new CreateGroupCallback() {
            @Override
            public void onCreateGroup(String groupId) {
                // Group saved successfully with the generated group ID
                group.setGroupId(groupId); // Set the generated group ID to the Group object
                String message = "Group created: " + group.getGroupName() + "\nGroup ID: " + groupId;
                Toast.makeText(GroupCreateActivity.this, message, Toast.LENGTH_SHORT).show();

                // Finish the activity and return to the previous screen
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Failed to save the group
                Toast.makeText(GroupCreateActivity.this, "Failed to create group: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
