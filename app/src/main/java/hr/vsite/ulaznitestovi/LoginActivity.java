package hr.vsite.ulaznitestovi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        // Initialize UI components
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the user is an admin
                if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                    // Admin login
                    // Start admin activity or perform admin-related tasks
                    Toast.makeText(LoginActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                } else {
                    // Regular user login
                    // Validate user credentials and perform login logic
                    // TODO: Implement user login logic
                    Toast.makeText(LoginActivity.this, "User login successful", Toast.LENGTH_SHORT).show();
                    // TODO: Start user activity
                }
            }
        });

        // Set click listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Extract university from email
                String[] emailParts = email.split("@");
                String university = emailParts[1];

                // Register the user and store the university information
                // TODO: Implement user registration logic
                Toast.makeText(LoginActivity.this, "Registration successful for university: " + university, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
