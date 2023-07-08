package hr.vsite.ulaznitestovi.db;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreDatabase {
    private static FirebaseFirestore instance;

    private FirestoreDatabase() {
        // Private constructor to prevent instantiation
    }

    public static FirebaseFirestore initialize(FirebaseFirestore firestore) {
        instance = firestore;
        return firestore;
    }

    public static FirebaseFirestore getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Firestore instance has not been initialized. Call initialize() first.");
        }
        return instance;
    }
}
