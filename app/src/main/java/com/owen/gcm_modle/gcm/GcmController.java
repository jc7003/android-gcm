package com.owen.gcm_modle.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.owen.gcm_modle.MainActivity;
import com.owen.gcm_modle.R;

/**
 * Created by owenchen on 2016/3/4.
 */
public class GcmController {

    private final String TAG = "GcmController";
    private Context mContext;
    private GoogleCloudMessaging mGcm;
    private final String TOKEN_LEY = "Token_Key";

    public GcmController(Context context){
        mContext = context;
    }

    public void init(){

        if (checkPlayServices()) {
            mGcm = GoogleCloudMessaging.getInstance(mContext);
            String regid = getGcmToken();

            //防呆
            if (regid.length() == 0) {
                registerInBackground();
            } else {
                printToken(regid);
            }
        }
    }

    private void printToken(String token){
        Log.d(TAG, "token:" + token);
    }

    //註冊推播
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String regid = "";
                try {
                    regid = mGcm.register(mContext.getString(R.string.google_gcm_sender_id));
                    storeGcmToken(regid);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return regid;
            }

            @Override
            protected void onPostExecute(String token){
                printToken(token);
            }

        }.execute(null, null, null);
    }

    private boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);

        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                Log.i(TAG, "error code:" + resultCode);
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    private void storeGcmToken(String regId) {
        SharedPreferences prefs = mContext.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_LEY, regId);
        editor.commit();
    }

    private String getGcmToken() {
        final SharedPreferences prefs = mContext.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(TOKEN_LEY, "");
        if (registrationId.isEmpty()) {
            return "";
        }

        return registrationId;
    }

}
