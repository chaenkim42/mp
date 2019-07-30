package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AskAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btn_login;
    Button btn_signup;
    Button btn_signout;
    Button btn_verify;
    EditText email_field, password_field;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askaccount);

        btn_signup = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login);
        btn_signout = findViewById(R.id.btn_signout);
        btn_verify = findViewById(R.id.btn_verify);
        email_field = findViewById(R.id.field_email);
        password_field = findViewById(R.id.field_password);

        mAuth = FirebaseAuth.getInstance();

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                    //일단은 메인으로 넘어가도록
                    startActivity(new Intent(AskAccountActivity.this,MainActivity.class));
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

    @Override

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_verify).setEnabled(!user.isEmailVerified());

        } else {

            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
            findViewById(R.id.signedInButtons).setVisibility(View.GONE);

        }

    }

    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.btn_signup) {
            createAccount(email_field.getText().toString(), password_field.getText().toString());
        } else if (i == R.id.btn_login) {
            signIn(email_field.getText().toString(), password_field.getText().toString());
        } else if (i == R.id.btn_signout) {
            signOut();
        } else if (i == R.id.btn_verify) {
            sendEmailVerification();
        }
    }

    private void createAccount(String email, String password) {
        Log.d("AskAccountActivity", "createAccount:" + email);

        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AskAccountActivity", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AskAccountActivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AskAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d("AskAccountActivity", "signIn:" + email);

        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AskAccountActivity", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithEmail:failure", task.getException());
                            Toast.makeText(AskAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        findViewById(R.id.btn_verify).setEnabled(false);
        final FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Re-enable button
                        findViewById(R.id.btn_verify).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(AskAccountActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("AskAccountActivity", "sendEmailVerification", task.getException());
                            Toast.makeText(AskAccountActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = email_field.getText().toString();

        if (TextUtils.isEmpty(email)) {
            email_field.setError("Required.");
            valid = false;
        } else {
            email_field.setError(null);
        }

        String password = password_field.getText().toString();
        if (TextUtils.isEmpty(password)) {
            password_field.setError("Required.");
            valid = false;
        } else {
            password_field.setError(null);
        }
        return valid;
    }
}
