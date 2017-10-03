package clientrest.com.clientrest.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import clientrest.com.clientrest.Service.MQTTService;

public class Broadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent it = new Intent(context, MQTTService.class);
        context.startService(it);
        Log.i("Script", "BroadcastReceiver1");
    }
}
