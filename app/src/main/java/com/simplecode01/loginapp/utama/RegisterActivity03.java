package com.simplecode01.loginapp.utama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.simplecode01.loginapp.MainActivity;
import com.simplecode01.loginapp.R;

public class RegisterActivity03 extends AppCompatActivity {

    private EditText edtemail, edtpassword, edtphone;
    private Button btnregister ;
    private FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register03);

        edtemail =findViewById(R.id.edt_email_register);
        edtphone =findViewById(R.id.edt_phone_number);
        edtpassword = findViewById(R.id.edt_password_register);
        btnregister = findViewById(R.id.btn_sign_up);
        auth = FirebaseAuth.getInstance();

        initView();
        registerUser();
    }


    private void registerUser() {
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menampung imputan user
                String emailUser = edtemail.getText().toString().trim();
                String passwordUser = edtpassword.getText().toString().trim();
                String phonenumber = edtphone.getText().toString().trim();

                //validasi email dan password
                // jika email kosong
                if (emailUser.isEmpty()){
                    edtemail.setError("Email tidak boleh kosong");
                }
                // jika email not valid
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()){
                    edtemail.setError("Email tidak valid");
                }
                // jika password kosong
                else if (passwordUser.isEmpty()){
                    edtpassword.setError("Password tidak boleh kosong");
                }
                //jika password kurang dari 6 karakter
                else if (passwordUser.length() < 6){
                    edtpassword.setError("Password minimal terdiri dari 6 karakter");
                }
                else if (phonenumber.isEmpty()){
                    edtphone.setError("Masukan Nomot Tlpn");
                }
                else  if(phonenumber.length()<12){
                    edtphone.setError("Masukan No Yang Benar");
                }
                else {
                    //create user dengan firebase auth
                    auth.createUserWithEmailAndPassword(emailUser,passwordUser)
                            .addOnCompleteListener(RegisterActivity03.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //jika gagal register do something
                                    if (!task.isSuccessful()){
                                        Toast.makeText(RegisterActivity03.this,
                                                "Register gagal karena "+ task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }else {
                                        //jika sukses akan menuju ke login activity
                                        startActivity(new Intent(RegisterActivity03.this,LoginActivity.class));
                                    }
                                }
                            });
                }
            }
        });
    }

    private void initView() {
        edtemail = findViewById(R.id.edt_email_register);
        edtpassword = findViewById(R.id.edt_password_register);
        btnregister = findViewById(R.id.btn_sign_up);
        auth = FirebaseAuth.getInstance();
    }
}