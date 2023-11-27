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

import com.example.nhom13_appbanhaisan.Model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText name,quyCach,gia,xuatXu,monNgon,soLuong;
    ImageView back,image;
    Uri anhsanpham;
    private Button luu,upload;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateproduct);
        getView();
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueFirebase();
                finish();
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
                finish();
            }
        });
    }
    public void getView(){
        image = findViewById(R.id.imageUpdate);
        name = findViewById(R.id.nameUpdate);
        quyCach = findViewById(R.id.quyCachUpdate);
        gia = findViewById(R.id.giaUpdate);
        xuatXu = findViewById(R.id.xuatXuUpdate);
        monNgon = findViewById(R.id.monNgonUpdate);
        soLuong = findViewById(R.id.soLuongUpdate);
        luu = findViewById(R.id.luuUpdate);
        back = findViewById(R.id.back);
        upload = findViewById(R.id.loadImage);
    }
    public void setValueFirebase(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String imageName = "image_" + timestamp + ".jpg";
        StorageReference imageRef = storageRef.child("images/" + imageName);
        imageRef.putFile(anhsanpham)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                String imageUrl = downloadUri.toString();
                                String ten = name.getText().toString();
                                String quycach = quyCach.getText().toString();
                                int giasp = Integer.parseInt(gia.getText().toString());
                                String xuatxu = xuatXu.getText().toString();
                                String monngon = monNgon.getText().toString();
                                int soluong = Integer.parseInt(soLuong.getText().toString());
                                Product product = new Product(imageUrl,ten,giasp,0,quycach,"Ngon,bổ,rẻ",monngon,"Còn hàng",xuatxu,soluong);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference productsRef = database.getReference("products");
                                productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        long count = dataSnapshot.getChildrenCount() + 1;
                                        product.setId((int) count);
                                        productsRef.child(String.valueOf(count)).setValue(product).addOnSuccessListener(aVoid -> {
                                            Log.d("Firebase", "Sản phẩm mới đã được thêm vào Firebase. ID: " + count);
                                        }).addOnFailureListener(e -> {
                                            Log.e("Firebase", "Lỗi khi thêm sản phẩm mới vào Firebase: " + e.getMessage());
                                        });
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Xử lý lỗi nếu có
                                        Log.e("Firebase", "Lỗi khi đọc dữ liệu từ Firebase: " + databaseError.getMessage());
                                    }
                                });
                            }
                        });
                    }
                });

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

}
