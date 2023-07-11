package hr.vsite.ulaznitestovi.repository;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import hr.vsite.ulaznitestovi.adapter.CreateGroupCallback;
import hr.vsite.ulaznitestovi.adapter.DeleteGroupCallback;
import hr.vsite.ulaznitestovi.adapter.GetGroupById;
import hr.vsite.ulaznitestovi.adapter.GroupFetch;
import hr.vsite.ulaznitestovi.adapter.UpdateGroupCallback;
import hr.vsite.ulaznitestovi.models.Group;

public class GroupRepository {
    private static final String COLLECTION_NAME = "groups";
    private final FirebaseFirestore firestore;

    public GroupRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void saveGroup(Group group, CreateGroupCallback callback) {
        DocumentReference groupRef = firestore.collection(COLLECTION_NAME).document();

        group.setGroupId(groupRef.getId());

        groupRef.set(group)
                .addOnSuccessListener(aVoid -> {
                    callback.onCreateGroup(group.getGroupId());
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                });
    }

    public void updateGroup(Group group, UpdateGroupCallback callback) {
        DocumentReference groupRef = firestore.collection(COLLECTION_NAME).document(group.getGroupId());

        groupRef.set(group)
                .addOnSuccessListener(aVoid -> {
                    callback.onGroupUpdated(group);
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                });
    }

    public void deleteGroup(Group group, DeleteGroupCallback callback) {
        DocumentReference groupRef = firestore.collection(COLLECTION_NAME).document(group.getGroupId());

        groupRef.delete()
                .addOnSuccessListener(aVoid -> {
                    callback.onGroupDeleted(group);
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                });
    }

    public void getGroupById(String groupId, GetGroupById callback) {
        if (groupId != null) {
            DocumentReference groupRef = firestore.collection(COLLECTION_NAME).document(groupId);

            groupRef.get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Group group = documentSnapshot.toObject(Group.class);
                            callback.onGroupRetrieved(group);
                        } else {
                            callback.onFailure("Group not found");
                        }
                    })
                    .addOnFailureListener(e -> {
                        callback.onFailure(e.getMessage());
                    });
        } else {
            callback.onFailure("Invalid group ID");
        }
    }

    public void getGroupsByAuthorId(String authorId, GroupFetch callback) {
        FirebaseFirestore.getInstance().collection("groups")
                .whereEqualTo("authorId", authorId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Group> groupList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Group group = document.toObject(Group.class);
                        groupList.add(group);
                    }
                    callback.onGroupsFetched(groupList);
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                });
    }

}
