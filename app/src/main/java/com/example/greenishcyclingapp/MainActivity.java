package com.example.greenishcyclingapp;

import android.app.Dialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REEQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isServicesOK()) {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No Google Play", Toast.LENGTH_SHORT).show();
                }
            }
        });
}
    public boolean isServicesOK() {

        Log.d(TAG, "isServicesOK: checking google services version");

        int avaliable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (avaliable == ConnectionResult.SUCCESS){
            //It is ok. User can make map requests
            Log.d(TAG, "isServicesOK: Gooogle Play services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(avaliable)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, avaliable, ERROR_DIALOG_REEQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
