package com.example.gogoooma.graduationproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends BaseActivity implements
        View.OnClickListener {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("users");

    private EditText memail;
    private EditText mpassword;
    private EditText mname;
    private EditText mphoneNumber;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        memail = findViewById(R.id.editText_signIn_email);
        mpassword = findViewById(R.id.editText_signIn_password);
        mname = findViewById(R.id.editText_signIn_name);
        mphoneNumber = findViewById(R.id.editText_signIn_phoneNumber);

        // Buttons
        findViewById(R.id.button_signIn).setOnClickListener(this);
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

    }

    private void createAccount(final String email, final String password, final String name, final String phoneNumber) {
        if (!validateForm()) {
            return;
        }
        // BaseActivity의 로딩중 다이얼로그
        showProgressDialog();
        // 이메일 생성
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            databaseReference.child(phoneNumber).child("name").setValue(name);
                            databaseReference.child(phoneNumber).child("email").setValue(email);
                            databaseReference.child(phoneNumber).child("pw").setValue(password);
                            updateUI(user);

                            // 로컬에 정보 저장
                            SharedPreferences auto;
                            auto = getSharedPreferences("savefile", Activity.MODE_PRIVATE);
                            if(auto.getString("phone", null) != null){
                                auto.edit().clear().commit();
                            }

                            SharedPreferences.Editor autoLogin = auto.edit();
                            autoLogin.putString("phone", phoneNumber);
                            autoLogin.putString("name", name);
                            autoLogin.putString("email", email);
                            autoLogin.putString("pwd", password);
                            autoLogin.commit();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "Sign in failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        hideProgressDialog();
                    }
                });
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_signIn) {
            createAccount(memail.getText().toString(),
                    mpassword.getText().toString(),
                    mname.getText().toString(),
                    mphoneNumber.getText().toString());
        }

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = memail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            memail.setError("Required.");
            valid = false;
        } else {
            memail.setError(null);
        }

        String password = mpassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mpassword.setError("Required.");
            valid = false;
        } else {
            mpassword.setError(null);
        }
        String name = mname.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mname.setError("Required.");
            valid = false;
        } else {
            mname.setError(null);
        }

        String phoneNumber = mphoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mphoneNumber.setError("Required.");
            valid = false;
        } else {
            mphoneNumber.setError(null);
        }

        return valid;
    }
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            EditText field_email = (EditText) findViewById(R.id.editText_signIn_email);
            EditText field_password = (EditText) findViewById(R.id.editText_signIn_password);
            EditText field_name = (EditText) findViewById(R.id.editText_signIn_name);
            EditText field_phoneNumber = (EditText) findViewById(R.id.editText_signIn_phoneNumber);
            field_email.setText("");
            field_password.setText("");
            field_name.setText("");
            field_phoneNumber.setText("");
        }
    }


}
