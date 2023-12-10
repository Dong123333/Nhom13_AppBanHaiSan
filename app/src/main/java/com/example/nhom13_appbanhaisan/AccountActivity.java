package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AccountActivity extends AppCompatActivity {

    private EditText matKhau,gmail;
    RelativeLayout hoSo;
    ImageView back;
    private Button luu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        getView();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            gmail.setText(user.getEmail());
        }
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueFirebase();
                Toast.makeText(AccountActivity.this,"Thay đổi thành công",Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PersonalInfoManagementActivity.class);
                startActivity(intent);
            }
        });
        hoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),InsertUserActivity.class);
                startActivity(intent);
            }
        });
    }
    public void getView(){
        matKhau = findViewById(R.id.password);
        gmail = findViewById(R.id.email);
        luu = findViewById(R.id.btnLuuAccount);
        back = findViewById(R.id.backAccount);
        hoSo = findViewById(R.id.hoSoCuaToi);
    }
    public void setValueFirebase(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            String password = matKhau.getText().toString().trim();
            String email = gmail.getText().toString().trim();
            user.updateEmail(email);
            user.updatePassword(password);
        }
        else {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }
    }

}
