package edu.wgu.dmass13.c196.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.view.assessment.AssessmentEditActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class C196Receiver extends BroadcastReceiver {

    public static final String C196RECEIVER_INTENT = "edu.wgu.dmass13.c196.C196Receiver";
    static int notificationID;
    String channel_id = "C196";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String message = (bundle == null ? null : (String) bundle.getSerializable(C196Receiver.C196RECEIVER_INTENT));


        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_owl)
                .setContentTitle(message)
                .setContentText("C196 WGU").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);


    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}


