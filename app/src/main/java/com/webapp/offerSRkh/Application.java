package com.webapp.offerSRkh;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class Application extends android.app.Application {
    @Override
    public void onCreate()
    {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    public class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler
    {
        @Override
        public void notificationOpened(OSNotificationOpenResult result)
        {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;


                      if(data != null && data.has("id")){
                          Intent intent = new Intent(Application.this,NotificationdataActivity.class);
                          intent.putExtra("id",data.optString("id"));
                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          startActivity(intent);
            }
        }
    }
}
