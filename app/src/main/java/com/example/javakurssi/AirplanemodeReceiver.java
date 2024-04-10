package com.example.javakurssi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AirplanemodeReceiver extends BroadcastReceiver {

    //private Context context;
    private CharSequence text;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        boolean state = intent.getBooleanExtra("state", false);

        Log.e("APM", String.valueOf(state));

        context = context.getApplicationContext();

        if (state == true){
            String apm = context.getResources().getString(R.string.apmon);
            text = apm;}
        else{
            String apm = context.getResources().getString(R.string.apmoff);
            text = apm;
        }
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}