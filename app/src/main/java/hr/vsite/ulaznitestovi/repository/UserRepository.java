package hr.vsite.ulaznitestovi.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import hr.vsite.ulaznitestovi.models.User;

public class UserRepository {

    private final FirebaseFirestore firestore;

    public UserRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void getUsersByUniversity(String university, UserRepositoryCallback callback) {
        // Query the Firestore collection for users with the specified university
        firestore.collection("users")
                .whereEqualTo("university", university)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<User> userList = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        userList.add(user);
                    }
                    callback.onUsersRetrieved(userList);
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                });
    }

    public interface UserRepositoryCallback {
        void onUsersRetrieved(List<User> users);

        void onFailure(String errorMessage);
    }
}
