package com.tochiku85.liketochiku.utils.parse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
import com.tochiku85.liketochiku.main.MainActivity;

public class PushReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushOpen(Context context, Intent intent) {

        Log.d("UserCustomReceiver", "onPushOpen");

        Bundle bundle = intent.getExtras();
        Intent i = new Intent(context,MainActivity.class);
        i.putExtras(bundle);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
