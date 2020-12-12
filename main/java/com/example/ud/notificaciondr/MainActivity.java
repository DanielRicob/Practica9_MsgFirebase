package com.example.ud.notificaciondr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public  void  GetToken(View v){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Log.w("TOKEN: ","Fetching FCM registration token Failed", task.getException());
                    return;
                }

                String token = task.getResult();
                String msg = token;
                Log.d("MI TOKEN MA: ",msg);
                Toast.makeText(MainActivity.this,msg, Toast.LENGTH_LONG).show();


            }
        });



    }

}