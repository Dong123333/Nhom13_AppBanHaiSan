package com.example.nhom13_appbanhaisan;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
=======
import android.os.Bundle;
>>>>>>> origin/codeGiaoDien/Dong123333

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
<<<<<<< HEAD
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
=======
>>>>>>> origin/codeGiaoDien/Dong123333
    }
}