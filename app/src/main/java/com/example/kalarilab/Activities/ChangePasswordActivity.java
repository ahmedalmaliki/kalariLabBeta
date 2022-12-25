package com.example.kalarilab.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.kalarilab.Models.AuthModel;
import com.example.kalarilab.R;
import com.example.kalarilab.SessionManagement;
import com.example.kalarilab.ViewModels.AuthViewModel;
import com.google.android.exoplayer2.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.r0adkll.slidr.Slidr;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangePasswordActivity extends AppCompatActivity  {
    private EditText currentPasswordEntry, newPasswordEntry;
    private Button resetPassword;
    private androidx.appcompat.widget.Toolbar toolbar ;
    private AuthViewModel authViewModel;
    private AuthModel authModel1 = new AuthModel();
    private final static String TAG = "ChangePasswordActivityDebug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Slidr.attach(this);
        initHooks();
        observeData();
        bindings();
    }

    private void observeData() {
        authViewModel.getmAuthModel().observe(this, new Observer<AuthModel>() {
            @Override
            public void onChanged(AuthModel authModel) {
                authModel1 = authModel;
            }
        });
    }

    private void bindings() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(CurrentPasswordEntryIsValid()){
                        pushNewPassword();

                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, "Incorrect Password!", Toast.LENGTH_LONG).show();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                clearEntryFeilds();
            }
        });

    }

    private void clearEntryFeilds() {
        currentPasswordEntry.setText("");
        newPasswordEntry.setText("");
    }

    private void pushNewPassword() throws NoSuchAlgorithmException {
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().
                getCurrentUser().getUid()).child("password").setValue(hashPassword(newPasswordEntry.getText().toString()));
        Toast.makeText(this, "Password is changed!", Toast.LENGTH_SHORT).show();
    }

    private boolean CurrentPasswordEntryIsValid() throws NoSuchAlgorithmException {
        if (authModel1.getPassword().equals(hashPassword(currentPasswordEntry.getText().toString()))){
            return true;
        }
        else{


            return false;
        }
    }





    private void initHooks() {
        authViewModel = new AuthViewModel();
        authViewModel.setActivity(this);
        try {
            authViewModel.init();

        }catch (Exception e){
            android.util.Log.d(TAG, e.getMessage());
        }
        currentPasswordEntry = findViewById(R.id.editTextCurrentPassword);
        newPasswordEntry = findViewById(R.id.editTextNewPassword);
        resetPassword = findViewById(R.id.resetPasswordButton);
        toolbar = findViewById(R.id.topAppBar);

    }



    private void showAlertDialog( String message) {
        if (!this.isFinishing()) {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Connection Error!")
                    .setMessage(message)

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.ok, null)

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for(byte b : mdArray) {
            int v = b & 0xff;
            if(v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }



        return sb.toString();

    }
}