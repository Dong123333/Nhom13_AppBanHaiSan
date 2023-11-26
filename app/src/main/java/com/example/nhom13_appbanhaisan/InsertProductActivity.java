package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InsertProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText name,quyCach,gia,xuatXu,monNgon,soLuong;
    ImageView back,image;
    Uri anhsanpham;
    private Button luu,upload;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertproduct);
        getView();
        Intent intent = getIntent();
        int productId = intent.getIntExtra("ID",0);
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertValueFirebase(productId);
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
        image = findViewById(R.id.imageInsert);
        name = findViewById(R.id.nameInsert);
        quyCach = findViewById(R.id.quyCachInsert);
        gia = findViewById(R.id.giaInsert);
        xuatXu = findViewById(R.id.xuatXuInsert);
        monNgon = findViewById(R.id.monNgonInsert);
        soLuong = findViewById(R.id.soLuongInsert);
        luu = findViewById(R.id.luuInsert);
        back = findViewById(R.id.back);
        upload = findViewById(R.id.loadImageInsert);
    }
    public void insertValueFirebase(int id){
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
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference productsRef = database.getReference("products").child(String.valueOf(id));
                                productsRef.child("anh").setValue(imageUrl);
                                productsRef.child("ten_san_pham").setValue(ten);
                                productsRef.child("quy_cach").setValue(quycach);
                                productsRef.child("gia").setValue(giasp);
                                productsRef.child("xuat_xu").setValue(xuatxu);
                                productsRef.child("mon_ngon").setValue(monngon);
                                productsRef.child("so_luong_ton_kho").setValue(soluong);
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
