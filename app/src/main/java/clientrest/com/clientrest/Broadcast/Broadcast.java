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
        Log.e("log_tag", "Action :: "+intent.getAction());
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent it = new Intent(context, MQTTService.class);
            context.startService(it);
        }
    }
}
