package hr.vsite.ulaznitestovi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hr.vsite.ulaznitestovi.adapter.CheckBoxListAdapter;
import hr.vsite.ulaznitestovi.adapter.DeleteGroupCallback;
import hr.vsite.ulaznitestovi.adapter.GetGroupById;
import hr.vsite.ulaznitestovi.adapter.UpdateGroupCallback;
import hr.vsite.ulaznitestovi.models.Group;
import hr.vsite.ulaznitestovi.models.User;
import hr.vsite.ulaznitestovi.repository.GroupRepository;
import hr.vsite.ulaznitestovi.repository.UserRepository;

public class GroupEditActivity extends AppCompatActivity {

    private EditText groupNameEditText;
    private Button saveGroupButton;
    private Button deleteGroupButton;
    private RecyclerView userRecyclerView;
    private CheckBoxListAdapter checkBoxListAdapter;
    private List<User> userList;
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_edit);

        groupNameEditText = findViewById(R.id.groupNameEditText);
        saveGroupButton = findViewById(R.id.saveGroupButton);
        deleteGroupButton = findViewById(R.id.deleteGroupButton);
        userRecyclerView = findViewById(R.id.userRecyclerView);

        groupRepository = new GroupRepository();
        userRepository = new UserRepository();

        // Retrieve the Group object from intent extras
        String groupId = getIntent().getStringExtra("groupId");

        groupRepository.getGroupById(groupId, new GetGroupById() {
            @Override
            public void onGroupRetrieved(Group retrievedGroup) {
                group = retrievedGroup;

                // Set the placeholder text for the group name EditText with the existing group name
                groupNameEditText.setHint(group.getGroupName());

                // Retrieve users with the same university as the group
                getUsersByUniversity(group.getUniversity());
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(GroupEditActivity.this, "Failed to retrieve group: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        userList = new ArrayList<>();
        checkBoxListAdapter = new CheckBoxListAdapter(this, userList);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(checkBoxListAdapter);

        deleteGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGroup();
                finish();
            }
        });

        saveGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGroup();
                finish();
            }
        });

        // Retrieve users with the same university as the group
        if (group != null) {
            getUsersByUniversity(group.getUniversity());
        }
    }

    private void getUsersByUniversity(String university) {
        userRepository.getUsersByUniversity(university, new UserRepository.UserRepositoryCallback() {
            @Override
            public void onUsersRetrieved(List<User> users) {
                userList.clear();
                userList.addAll(users);
                checkBoxListAdapter.notifyDataSetChanged();

                // Pre-select users who are already in the group
                if (group != null) {
                    for (User user : userList) {
                        if (group.getUserIds().contains(user.getUserId())) {
                            checkBoxListAdapter.setChecked(user, true);
                        }
                    }
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(GroupEditActivity.this, "Failed to retrieve users: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateGroup() {
        String newGroupName = groupNameEditText.getText().toString();

        if (group != null) {
            // Update the group name
            group.setGroupName(newGroupName);

            // Update the selected user IDs
            List<String> selectedUserIds = checkBoxListAdapter.getSelectedUserIds();
            group.setUserIds(selectedUserIds);

            // Save the updated group to the database
            groupRepository.updateGroup(group, new UpdateGroupCallback() {
                @Override
                public void onGroupUpdated(Group updatedGroup) {
                    // Group updated successfully
                    String message = "Group updated: " + updatedGroup.getGroupName();
                    Toast.makeText(GroupEditActivity.this, message, Toast.LENGTH_SHORT).show();

                    // Finish the activity and return to the previous screen
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    // Failed to update the group
                    Toast.makeText(GroupEditActivity.this, "Failed to update group: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void deleteGroup() {
        if (group != null) {
            groupRepository.deleteGroup(group, new DeleteGroupCallback() {
                @Override
                public void onGroupDeleted(Group deletedGroup) {
                    // Group deleted successfully
                    String message = "Group deleted: " + deletedGroup.getGroupName();
                    Toast.makeText(GroupEditActivity.this, message, Toast.LENGTH_SHORT).show();

                    // Finish the activity and return to the previous screen
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    // Failed to delete the group
                    Toast.makeText(GroupEditActivity.this, "Failed to delete group: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
