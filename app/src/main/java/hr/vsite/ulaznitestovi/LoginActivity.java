package hr.vsite.ulaznitestovi;

import static hr.vsite.ulaznitestovi.PasswordHasher.verifyPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import hr.vsite.ulaznitestovi.db.FirestoreDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    // Dummy admin credentials
    private final String ADMIN_EMAIL = "admin@example.com";
    private final String ADMIN_PASSWORD = "admin123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db
        = FirestoreDatabase.initialize(FirebaseFirestore.getInstance());

        // Initialize UI components
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Retrieve the user's hashed password and ID from the Firestore database
                db.collection("users")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                String hashedPassword = document.getString("password");
                                String userId = document.getId();

                                // Verify the provided password
                                if (verifyPassword(password, hashedPassword)) {
                                    // Password is correct, perform login logic
                                    String role = document.getString("role");

                                    if (role.equals(Role.ADMIN)) {
                                        // Admin login
                                        Toast.makeText(LoginActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                                        startActivity(intent);
                                    } else if (role.equals(Role.USER)) {
                                        // User login
                                        String name = document.getString("name");
                                        // ... Get other user data

                                        Toast.makeText(LoginActivity.this, "User login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);
                                        intent.putExtra("userId", userId);
                                        startActivity(intent);
                                    }
                                } else {
                                    // Password is incorrect
                                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // User does not exist
                                Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Register the user and store the university information
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "Registration successful for university: " , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
