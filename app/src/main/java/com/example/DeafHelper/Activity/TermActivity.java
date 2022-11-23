package com.example.DeafHelper.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DeafHelper.Domain.Item;
import com.example.DeafHelper.Helper.DataManagment;
import com.example.DeafHelper.Helper.LoadImage;
import com.example.DeafHelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TermActivity extends AppCompatActivity {

    TextView termTitle;
    TextView description;
    RecyclerView images;
    Intent intent;
    String id,label, desc, images_path;
    RecyclerView imagesRecyclerView;
    DataManagment dataManagment;
    ArrayList<String> list;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        initData();
        bottomNavigation();

    }

    private void initData() {
        intent = getIntent();
        id = intent.getStringExtra("id");
        label = intent.getStringExtra("label");
        desc = intent.getStringExtra("description");
        list = intent.getStringArrayListExtra("list");
        termTitle = findViewById(R.id.termTitle);
        description = findViewById(R.id.description);
        termTitle.setText(label);
        description.setText(desc);
        imagesRecyclerView = findViewById(R.id.imagesRecyclerView);
        images_path = "https://giveawaygiftcard.com/deafhelper/deafhelper/web/api/termimages?id="+id;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        imagesRecyclerView.setLayoutManager(linearLayoutManager);
//        adapter = new ImagesAdapter(list);
//        imagesRecyclerView.setAdapter(adapter);
        new LoadImage(this,imagesRecyclerView).execute(images_path);
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout servicesBtn = findViewById(R.id.servicesBtn);
        LinearLayout termsBtn = findViewById(R.id.termsBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TermActivity.this, MainActivity.class));
            }
        });
        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()){
                    DataManagment dataManagement = (DataManagment) new DataManagment(TermActivity.this, "service").execute(Item.servicesPath);
                }else{
                    Toast.makeText(TermActivity.this, "لا يوجذ اتصال بالانترنت",Toast.LENGTH_SHORT).show();
                }
            }
        });

        termsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                    DataManagment dataManagement = (DataManagment) new DataManagment(TermActivity.this, "term").execute(Item.termsPath);
                }else{
                    Toast.makeText(TermActivity.this, "لا يوجذ اتصال بالانترنت",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(TermActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}