package com.example.ud.notificaciondr;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MFBMS: ";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From "+ remoteMessage.getFrom());
        if(remoteMessage.getData().size() > 0 ){
            Log.d(TAG,"Message data payload" + remoteMessage.getData());
            Log.d(TAG,"Title: "+ remoteMessage.getData().get("Title"));
            Log.d(TAG,"Message: " + remoteMessage.getData().get("Message"));
        }
        if (remoteMessage.getNotification() != null){
            Log.d(TAG,"Message Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG,"Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification());
        }
    }

    private void sendNotification(RemoteMessage.Notification notification){
        String ns = Context.NOTIFICATION_SERVICE;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence NombreCanal ="Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel("UDNOTIFICATION",NombreCanal, NotificationManager
                    .IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(ns);
            notificationManager.createNotificationChannel(notificationChannel);
           }

            Context contexto = getApplicationContext();

            Intent notIntent = new Intent(contexto,MainActivity.class);
            PendingIntent contIntent = PendingIntent.getActivity(contexto,0,notIntent,0);

            int icono = android.R.drawable.stat_sys_warning;
            CharSequence textoEstado = "!Atencion¡";
            CharSequence titulo = notification.getTitle();
            CharSequence descripcion = notification.getBody();
            long hora = System.currentTimeMillis();

            @SuppressLint("UseCompatLoadingForDrawables") NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(contexto, "UDNOTIFICATION")
                .setSmallIcon(icono)
                .setLargeIcon((((BitmapDrawable)getResources()
                .getDrawable(R.drawable.info)).getBitmap()))
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setContentInfo(textoEstado)
                .setWhen(hora)
                .setContentIntent(contIntent)
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_default))
                .setVibrate(new long[] {1000,500,1000,500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                NotificationManagerCompat notManager = NotificationManagerCompat.from(contexto);
                notManager.notify(1,mBuilder.build());

                    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("MI TOKEN",s);


    }
}
