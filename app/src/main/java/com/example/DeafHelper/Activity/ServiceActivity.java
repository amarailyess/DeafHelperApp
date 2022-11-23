package com.example.DeafHelper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.DeafHelper.Domain.Item;
import com.example.DeafHelper.Helper.DataManagment;
import com.example.DeafHelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ServiceActivity extends AppCompatActivity {

    TextView serviceTitle;
    VideoView videoView;
    Intent intent;
    String videoPath, label;
    MediaController mc;
    ProgressDialog dialog;
    DataManagment dataManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        intent = getIntent();
        videoPath = intent.getStringExtra("video");
        label = intent.getStringExtra("label");
        serviceTitle = findViewById(R.id.termTitle);
        videoView = findViewById(R.id.videoView);
        serviceTitle.setText(label);
        if (mc == null) {
            mc = new MediaController(this);
            mc.setAnchorView(this.videoView);
        }
        videoView.setMediaController(mc);
        dialog = new ProgressDialog(this);
        dialog.setMessage("جاري تحميل الفيديو...");
        dialog.show();
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        videoView.start();
        bottomNavigation();

    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout servicesBtn = findViewById(R.id.servicesBtn);
        LinearLayout termsBtn = findViewById(R.id.termsBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServiceActivity.this, MainActivity.class));
            }
        });
        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    dataManagement = (DataManagment) new DataManagment(ServiceActivity.this, "service").execute(Item.servicesPath);
                } else {
                    Toast.makeText(ServiceActivity.this, "لا يوجذ اتصال بالانترنت", Toast.LENGTH_SHORT).show();
                }
            }
        });

        termsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    dataManagement = (DataManagment) new DataManagment(ServiceActivity.this, "term").execute(Item.termsPath);
                } else {
                    Toast.makeText(ServiceActivity.this, "لا يوجذ اتصال بالانترنت", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(ServiceActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
