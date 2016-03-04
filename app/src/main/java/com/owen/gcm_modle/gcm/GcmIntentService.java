package com.owen.gcm_modle.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.owen.gcm_modle.MainActivity;
import com.owen.gcm_modle.R;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
		Log.d("GcmIntentService", "onHandleIntent");
        Bundle extras = intent.getExtras();
        if (!extras.isEmpty()) {
        		sendNotification(extras.toString());

        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String info) {
		final Intent intent = new Intent(this, MainActivity.class);
		intent.setAction(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//傳遞 notification 參數
		intent.putExtra(getString(R.string.notification_info), info);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    	final Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setTicker(getString(R.string.app_name));
        mBuilder.setContentTitle(getString(R.string.app_name));
		mBuilder.setContentInfo("");
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setSound(soundUri);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }
}