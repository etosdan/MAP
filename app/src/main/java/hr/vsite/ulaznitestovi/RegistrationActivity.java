package hr.vsite.ulaznitestovi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import androidx.annotation.NonNull;


import hr.vsite.ulaznitestovi.db.FirestoreDatabase;
import hr.vsite.ulaznitestovi.models.Role;
import hr.vsite.ulaznitestovi.models.User;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText emailEditText;
    private Spinner universitySpinner;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private TextView passwordMismatchTextView;
    private Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        universitySpinner = findViewById(R.id.universitySpinner);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        roleSpinner = findViewById(R.id.roleSpinner);
        passwordMismatchTextView = findViewById(R.id.passwordMismatchTextView);
        Button registerButton = findViewById(R.id.registerButton);

        ArrayAdapter<Role> roleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Role.values());
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String university = universitySpinner.getSelectedItem().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                Role role = Role.valueOf(roleSpinner.getSelectedItem().toString());
                // Validate all fields
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(email)
                        || TextUtils.isEmpty(university) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(RegistrationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate email format
                if (!isValidEmail(email)) {
                    Toast.makeText(RegistrationActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    passwordMismatchTextView.setVisibility(View.VISIBLE);
                    passwordMismatchTextView.setText("Passwords do not match");
                    return;
                } else {
                    passwordMismatchTextView.setVisibility(View.GONE);
                }

                // Hash the password
                String hashedPassword = PasswordHasher.hashPassword(password);

                // Create a user object
                User user = new User(name, surname, email, university, hashedPassword, role);

                // Save the user data to the database
                FirestoreDatabase.getInstance().collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Get the generated user ID
                                String userId = documentReference.getId();
                                user.setUserId(userId);

                                // Update the user data with the generated user ID
                                FirestoreDatabase.getInstance().collection("users").document(userId)
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                                // Navigate back to the login activity
                                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegistrationActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegistrationActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        }
    private boolean isValidEmail(String email) {
        // Simple email format validation using regular expression
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
        return email.matches(emailRegex);
    }
}
