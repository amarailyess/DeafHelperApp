package com.example.DeafHelper.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.DeafHelper.Adapter.ImagesAdapter;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class LoadImage extends AsyncTask<String, Void, ArrayList> {
    JSONArray json;
    Context context;
    ArrayList<Bitmap> images_bitmap;
    ArrayList<String> list;
    RecyclerView recyclerView;
    RecyclerView.Adapter imagesAdapter;
    ProgressDialog dialog;
    String basePath = "https://giveawaygiftcard.com/deafhelper/deafhelper/web/";
    public LoadImage(Context con, RecyclerView recyclerView){
        context = con;
        images_bitmap = new ArrayList<Bitmap>();
        this.recyclerView = recyclerView;
        list = new ArrayList<String>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("جاري تحميل المصطلح ...");
        dialog.show();
    }

    @Override
    protected ArrayList doInBackground(String... urls) {

        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try
        {
            URL url = new URL(urls[0]);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(((URLConnection) urlConn).getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            json = new  JSONArray(stringBuffer.toString());
            for(int i=0;i<json.length();i++){
//                images_urls[i] = json.getJSONObject(i).getString("label");
                String imageURL= basePath+json.getJSONObject(i).getString("label");
                Bitmap bimage = null;
                try {
                    InputStream in= new java.net.URL(imageURL).openStream();
                    bimage= BitmapFactory.decodeStream(in);
                    images_bitmap.add(bimage);
                } catch (Exception e) {
                    Log.e("Error Message", e.getMessage());
                    e.printStackTrace();
                }
            }
            return images_bitmap;
        }
        catch(Exception ex)
        {
            Log.e("App", "yourDataTask", ex);
            return null;
        }
        finally
        {
            if(bufferedReader != null)
            {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void onPostExecute(ArrayList result) {
        dialog.dismiss();
        imagesAdapter = new ImagesAdapter(result);
        recyclerView.setAdapter(imagesAdapter);

    }
}
