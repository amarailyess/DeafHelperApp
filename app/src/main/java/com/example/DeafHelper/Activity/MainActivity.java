package com.example.DeafHelper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.DeafHelper.Domain.Item;
import com.example.DeafHelper.Helper.DataManagment;
import com.example.DeafHelper.R;

public class MainActivity extends AppCompatActivity {

    DataManagment dataManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.serviceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()){
                    dataManagement = (DataManagment) new DataManagment(MainActivity.this, "service").execute(Item.servicesPath);
                }else{
                    Toast.makeText(MainActivity.this, "لا يوجذ اتصال بالانترنت",Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.termBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                    dataManagement = (DataManagment) new DataManagment(MainActivity.this, "term").execute(Item.termsPath);
                }else{
                    Toast.makeText(MainActivity.this, "لا يوجذ اتصال بالانترنت",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}