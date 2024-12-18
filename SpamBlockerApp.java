// SpamBlockerApp.java

package com.example.spamblocker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String BLOCKLIST_PREF = "BlockList";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(BLOCKLIST_PREF, MODE_PRIVATE);

        // Check for necessary permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.ANSWER_PHONE_CALLS
            }, PERMISSION_REQUEST_CODE);
        }
    }

    // Save a phone number to block list
    public void blockNumber(String phoneNumber) {
        Set<String> blockList = sharedPreferences.getStringSet("blockList", new HashSet<>());
        blockList.add(phoneNumber);
        sharedPreferences.edit().putStringSet("blockList", blockList).apply();
        Toast.makeText(this, "Number blocked successfully!", Toast.LENGTH_SHORT).show();
    }

    // Remove a phone number from block list
    public void unblockNumber(String phoneNumber) {
        Set<String> blockList = sharedPreferences.getStringSet("blockList", new HashSet<>());
        if (blockList.contains(phoneNumber)) {
            blockList.remove(phoneNumber);
            sharedPreferences.edit().putStringSet("blockList", blockList).apply();
            Toast.makeText(this, "Number unblocked successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Number not found in blocklist!", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // BroadcastReceiver to monitor incoming calls
    public static class CallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                SharedPreferences preferences = context.getSharedPreferences(BLOCKLIST_PREF, Context.MODE_PRIVATE);
                Set<String> blockList = preferences.getStringSet("blockList", new HashSet<>());

                if (TelephonyManager.EXTRA_STATE_RINGING.equals(state) && blockList.contains(incomingNumber)) {
                    // Block the call
                    endCall(context);
                    Toast.makeText(context, "Blocked call from: " + incomingNumber, Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void endCall(Context context) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                tm.getClass().getMethod("endCall").invoke(tm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
