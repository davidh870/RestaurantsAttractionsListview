package edu.uic.cs478.application2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AttractionsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent AttractionsIntent = new Intent(context.getApplicationContext(), AttractionsActivity.class);
        //AttractionsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(AttractionsIntent);
    }
}
