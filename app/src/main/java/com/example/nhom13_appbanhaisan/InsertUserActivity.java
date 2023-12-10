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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.Model.Users;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.List;

public class InsertUserActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText name,gender,date,phone;
    ImageView back,image;
    private TextView upload, luu;
    Uri anhsanpham;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertuser);
        getView();
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueFirebase();
                Toast.makeText(InsertUserActivity.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
            }
        });
        getValue();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PersonalInfoManagementActivity.class);
                startActivity(intent);
            }
        });
    }
    public void getView(){
        image = findViewById(R.id.imageInsertUser);
        name = findViewById(R.id.hoVaTen);
        gender = findViewById(R.id.gioiTinh);
        date = findViewById(R.id.ngaySinh);
        phone = findViewById(R.id.soDienThoai);
        luu = findViewById(R.id.btnLuu);
        back = findViewById(R.id.backInsertUser);
        upload = findViewById(R.id.insertImage);
    }
    public void setValueFirebase(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            String id = user.getUid();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String imageName = "image_" + timestamp + ".jpg";
            StorageReference imageRef = storageRef.child("images/" + imageName);
            if (anhsanpham != null){
                imageRef.putFile(anhsanpham)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadUri) {
                                        String imageUrl = downloadUri.toString();
                                        String ten = name.getText().toString().trim();
                                        String gioitinh = gender.getText().toString().trim();
                                        String ngaysinh = date.getText().toString().trim();
                                        String sodienthoai = phone.getText().toString().trim();
                                        Users users = new Users(imageUrl,ten,gioitinh,ngaysinh,sodienthoai);
                                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(id).child("account");
                                        userRef.setValue(users);

                                    }
                                });
                            }
                        });
            }
            else {
                DatabaseReference imageUrlRef = FirebaseDatabase.getInstance().getReference("users").child(id).child("account");
                imageUrlRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String oldImageUrl = dataSnapshot.child("image").getValue(String.class);
                            String ten = name.getText().toString().trim();
                            String gioitinh = gender.getText().toString().trim();
                            String ngaysinh = date.getText().toString().trim();
                            String sodienthoai = phone.getText().toString().trim();
                            Users users = new Users(oldImageUrl,ten,gioitinh,ngaysinh,sodienthoai);
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(id).child("account");
                            userRef.setValue(users);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

        }
        else {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }
    }
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            anhsanpham = data.getData();
            image.setImageURI(selectedImageUri);
        }
    }
    public void getValue(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            String id = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(id).child("account");
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String ten = snapshot.child("fullName").getValue(String.class);
                        String anh = snapshot.child("image").getValue(String.class);
                        String gioitinh = snapshot.child("gender").getValue(String.class);
                        String ngaysinh = snapshot.child("date").getValue(String.class);
                        String sdt = snapshot.child("phone").getValue(String.class);
                        name.setText(ten);
                        gender.setText(gioitinh);
                        date.setText(ngaysinh);
                        phone.setText(sdt);
                        if(anh != null && !anh.isEmpty()){
                            Glide.with(InsertUserActivity.this)
                                    .load(anh)
                                    .apply(new RequestOptions()
                                            .override(800, 800)
                                            .fitCenter())
                                    .into(image);
                        }else {
                            image.setImageResource(R.drawable.user);
                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
