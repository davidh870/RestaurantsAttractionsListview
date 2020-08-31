package edu.uic.cs478.SenderApp1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    private Button AttractionsButton;
    private Button RestaurantButton;
    private int ButtonPressed = 0;

    private static final String ChiTownPermission = "edu.uic.cs478.sp2020.chitowndanger";

    private static final String AttractionsIntent = "edu.uic.cs478.sp2020.AttractionsIntent";
    private static final String RestaurantsIntent = "edu.uic.cs478.sp2020.RestaurantsIntent";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get button from layout
        AttractionsButton = (Button) findViewById(R.id.AttractionsButtonID);
        RestaurantButton = (Button) findViewById(R.id.RestaurantsButtonID);

        AttractionsButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ButtonPressed = 1;
                checkPermissionAndBroadcast(ButtonPressed);

               // Toast.makeText(this, "Allowing to view attractions in Chicago", Toast.LENGTH_SHORT).show();
            }
        });

        RestaurantButton.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ButtonPressed = 2;
                checkPermissionAndBroadcast(ButtonPressed);
            }
        });

        //Toast.makeText(this, "Bummer: No permission", Toast.LENGTH_SHORT).show();
    }

    private void checkPermissionAndBroadcast(int action){
        // Checks if permission was granted
        if(ContextCompat.checkSelfPermission(this, ChiTownPermission) == PackageManager.PERMISSION_GRANTED){
            // If user click on attractions button send appropriate broadcast and permission
            if(action == 1){
                Intent myIntent = new Intent(AttractionsIntent);
                sendBroadcast(myIntent, "edu.uic.cs478.sp2020.chitowndanger");

                Toast.makeText(this, "Allowing to view attractions in Chicago", Toast.LENGTH_SHORT).show();
            }
            // If user click on restaurants button send appropriate broadcast and permission
            else if(action == 2){
                Intent myIntent = new Intent(RestaurantsIntent);
                sendBroadcast(myIntent, "edu.uic.cs478.sp2020.chitowndanger");

                Toast.makeText(this, "Allowing to view restaurants in Chicago", Toast.LENGTH_SHORT).show();
            }
        }
        // Request Permission
        else{
            ActivityCompat.requestPermissions(this, new String[]{ChiTownPermission}, 0) ;
        }
    }


    public void onRequestPermissionsResult(int code, String[] permissions, int[] results){
        if (results.length > 0){
            // If permission granted send appropriate broadcast and display toast
            if(results[0] == PackageManager.PERMISSION_GRANTED){
                if(ButtonPressed == 1){
                    Intent myIntent = new Intent(AttractionsIntent);
                    sendBroadcast(myIntent, "edu.uic.cs478.sp2020.chitowndanger");

                    Toast.makeText(this, "Allowing to view attractions in Chicago", Toast.LENGTH_SHORT).show();
                }
                else if(ButtonPressed == 2){
                    Intent myIntent = new Intent(RestaurantsIntent);
                    sendBroadcast(myIntent, "edu.uic.cs478.sp2020.chitowndanger");

                    Toast.makeText(this, "Allowing to view restaurants in Chicago", Toast.LENGTH_SHORT).show();
                }
            }
            // Else display toast
            else{
                Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }



}