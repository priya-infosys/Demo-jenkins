package com.example.priya_jain04.check;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
    static DummyNotification mStatus = DummyNotification.Failure;
    private static int NOTIFICATION_ID = 1723911; // Random value
    static Notification notification;



    enum DummyNotification {
        SUCCESS("Data Saved"), Failure(
                "No data saved");

        String message = null;

        DummyNotification(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);
        getNotificationInstance(DummyNotification.Failure, this);

        //updateNotification(DummyNotification.Failure,this);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);

            if(!cn.getName().equals(null)){
                //updateNotification(DummyNotification.SUCCESS,this);
               // notificationManager.notify(0, getNotificationInstance(notification, context));
               // getNotificationInstance(DummyNotification.SUCCESS, this);

            }
        }


        notificationManager.notify(0, notification);
        startForeground(1337, notification);

    }


    public static void updateNotification(DummyNotification notification, Context context) {
        mStatus = notification;
        ((Service) context).startForeground(NOTIFICATION_ID,
                getNotificationInstance(notification, context));
    }
    public static Notification getNotificationInstance(
            DummyNotification notificationType, Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Generate Notification
        Notification.Builder builder = new Notification.Builder(context);
         notification = builder.setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentTitle(context.getString(com.example.priya_jain04.check.R.string.app_name))
                .setContentText(notificationType.getMessage())
                .build();

        return notification;
    }

}
