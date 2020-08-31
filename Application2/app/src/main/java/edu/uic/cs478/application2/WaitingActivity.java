package edu.uic.cs478.application2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

public class WaitingActivity extends Activity {

    private static final String AttractionsIntent = "edu.uic.cs478.sp2020.AttractionsIntent";
    private static final String RestaurantsIntent = "edu.uic.cs478.sp2020.RestaurantsIntent";
    private static final String CHITTOWNPERMISSION = "edu.uic.cs478.sp2020.chitowndanger" ;


    BroadcastReceiver myAttractionsReceiver = new AttractionsReceiver();
    BroadcastReceiver myRestaurantsReceiver = new RestaurantsReceiver();
    IntentFilter AttractionsIntentFilter = new IntentFilter(AttractionsIntent);
    IntentFilter RestaurantsIntentFilter = new IntentFilter(RestaurantsIntent);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_activity);

        // Check for permission granted from App 1
        checkPermissionAndRegisterReceiver();
    }

    protected void onDestroy() {
        super.onDestroy() ;
        unregisterReceiver(myAttractionsReceiver);
        unregisterReceiver(myRestaurantsReceiver);

    }

    private void checkPermissionAndRegisterReceiver() {
        if (ContextCompat.checkSelfPermission(this, CHITTOWNPERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
            registerReceiver(myAttractionsReceiver, AttractionsIntentFilter, CHITTOWNPERMISSION, null);
            registerReceiver(myRestaurantsReceiver, RestaurantsIntentFilter,CHITTOWNPERMISSION, null);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{CHITTOWNPERMISSION}, 0) ;
        }

    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        if (results.length > 0) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                registerReceiver(myAttractionsReceiver, AttractionsIntentFilter, CHITTOWNPERMISSION, null);
                registerReceiver(myRestaurantsReceiver, RestaurantsIntentFilter,CHITTOWNPERMISSION, null);
            }
            else {
                Toast.makeText(this, "Bummer: No permission", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.option_menu,menu);
        return true;

    }
}