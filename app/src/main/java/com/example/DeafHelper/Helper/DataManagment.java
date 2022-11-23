package com.example.DeafHelper.Helper;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.DeafHelper.Activity.ItemListActivity;
import com.example.DeafHelper.Domain.Term;
import com.example.DeafHelper.Domain.Service;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DataManagment extends AsyncTask<String, Void, JSONArray>
{
    String  type,msg_;
    ArrayList<Term> terms;
    ArrayList<Service> services;
    Context context;
    Intent intent;
    public DataManagment(Context context,String type){
        this.type = type;
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        if(type.equals("service")){
            services = new ArrayList<Service>();
            msg_ = "جاري تحميل الخدمات";
        }else{
            terms = new ArrayList<Term>();
            msg_ = "جاري تحميل المصطلحات";

        }
        Toast.makeText(context, msg_,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected JSONArray doInBackground(String... params)
    {
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try
        {
            URL url = new URL(params[0]);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(((URLConnection) urlConn).getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            return new JSONArray(stringBuffer.toString());
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
    @Override
    protected void onPostExecute(JSONArray response) {
        if (response != null) {
            try {
                if (type.equals("service")) {
                    for (int i = 0; i < response.length(); i++) {
                        Service service = new Service(response.getJSONObject(i).getString("id"), response.getJSONObject(i).getString("label"),
                                response.getJSONObject(i).getString("description"), response.getJSONObject(i).getString("image"),
                                response.getJSONObject(i).getString("video"));
                        services.add(service);
                    }
                } else {
                    for (int i = 0; i < response.length(); i++) {
                        Term term = new Term(response.getJSONObject(i).getString("id"), response.getJSONObject(i).getString("label"),
                                response.getJSONObject(i).getString("description")
                        );
                        terms.add(term);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            intent = new Intent(context, ItemListActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtra("type", type);
            if (type.equals("service")) {
                bundle.putSerializable("services", (ArrayList<Service>) services);
            } else {
                bundle.putSerializable("terms", (ArrayList<Term>) terms);
            }
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            if (type.equals("service")) {
                msg_ = "لا يوجد خدمات متوفرة حاليا";
            } else {
                msg_ = "لا يوجد مصطلحات متوفرة حاليا";
            }
            Toast.makeText(context, msg_, Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<Service> getServices() {
        return services;
    }
    public ArrayList<Term> getTerms(){
        return terms;
}

}
