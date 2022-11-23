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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DeafHelper.Adapter.Adapter;
import com.example.DeafHelper.Domain.Item;
import com.example.DeafHelper.Domain.Term;
import com.example.DeafHelper.Domain.Service;
import com.example.DeafHelper.Helper.DataManagment;
import com.example.DeafHelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity  {

    String type;
    ArrayList<Service> services;
    ArrayList<Term> terms;
    TextView itemType, emptyText;
    private RecyclerView recyclerViewList;
    private Adapter adapter;
    ScrollView scrollView;
    Intent i;
    Bundle b;
    DataManagment dataManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        itemType= findViewById(R.id.itemType);
        emptyText = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView6);
        recyclerViewList=findViewById(R.id.items);
        initList();
        bottomNavigation();
    }

    private void initList(){
        i= getIntent();
        type = i.getStringExtra("type");
        Bundle bundle = getIntent().getExtras();
        if(type.equals("service")){
            itemType.setText("خدمات إجتماعية");
            services = (ArrayList<Service>) bundle.getSerializable("services");
            adapter = new Adapter(services,type,this);
        }else{
            itemType.setText("مصطلحات");
            terms = (ArrayList<Term>) bundle.getSerializable("terms");
            adapter = new Adapter(terms,type, this);
        }
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerViewList.setAdapter(adapter);
        recyclerViewList.setLayoutManager(linearLayoutManager);

    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout servicesBtn = findViewById(R.id.servicesBtn);
        LinearLayout termsBtn = findViewById(R.id.termsBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ItemListActivity.this, MainActivity.class));
            }
        });
        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()){
                    dataManagement = (DataManagment) new DataManagment(ItemListActivity.this, "service").execute(Item.servicesPath);
                }else{
                    Toast.makeText(ItemListActivity.this, "لا يوجذ اتصال بالانترنت",Toast.LENGTH_SHORT).show();

                }
            }
        });

        termsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                    dataManagement = (DataManagment) new DataManagment(ItemListActivity.this, "term").execute(Item.termsPath);
                }else{
                    Toast.makeText(ItemListActivity.this, "لا يوجذ اتصال بالانترنت",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(ItemListActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}