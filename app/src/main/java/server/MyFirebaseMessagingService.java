package server;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.iranplanner.tourism.iranplanner.ui.activity.mainActivity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

/**
 * Created by Hoda on 14/03/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "Hoda: " + remoteMessage.getFrom());
        //waiting for debugger service
        android.os.Debug.waitForDebugger();
        android.os.Debug.isDebuggerConnected();
        if (remoteMessage == null)
            return;



        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                Map<String, String> params = remoteMessage.getData();
                JSONObject object = new JSONObject(params);
                Log.e("JSON OBJECT", object.toString());
                handleDataMessage(object);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
//            3
            return;
        }else {
            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
                handleNotification(remoteMessage.getNotification());
            }
        }

    }

    private void handleNotification(RemoteMessage.Notification message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.showNotificationMessage(
                    message.getTitle(),
                    message.getBody(),
                    String.valueOf(System.currentTimeMillis()),
                    pushNotification
            );

            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    // todo data message need to change according to data type we gonna use
    private void handleDataMessage(JSONObject data) {
        android.os.Debug.waitForDebugger();
        android.os.Debug.waitForDebugger();
        android.os.Debug.isDebuggerConnected();
        try {
//            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
//            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String ntype = data.getString("ntype");
            String id = data.getString("id");
            String timestamp = String.valueOf(new Date(System.currentTimeMillis()));
//            JSONObject payload = data.getJSONObject("payload");


//            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//                // app is in foreground, broadcast the push message
//                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", message);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSound();
//            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bd = new Bundle();
                bd.putString("tests", "123");
                bd.putString("ntype",ntype);
                bd.putString("id",id);
                resultIntent.putExtras(bd);
                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }
//                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * Showing notification with text only
     */

    private void showNotificationMessage(Context context, String title, String message, String
            timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String
            message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}