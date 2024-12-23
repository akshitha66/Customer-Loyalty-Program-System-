package com.example.loyaltyfirst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        TextView textView18 = findViewById(R.id.textView18);
        TextView textView20 = findViewById(R.id.textView20);
        TextView textView21 = findViewById(R.id.textView21);
        TextView textView22 = findViewById(R.id.textView22);
        TextView textView23 = findViewById(R.id.textView23);
        Spinner spinnerinred = findViewById(R.id.spinner2);
        Intent intent5 = getIntent();
        String cid = intent5.getStringExtra("cid");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url5 = "http://10.0.2.2:8080/loyaltyfirst/PrizeIds.jsp?cid="+cid;
        StringRequest request5 = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<String> MyArrayList2 = new ArrayList<String>();
                String[] prizes = s.trim().split("#");
                for(int i =0; i<prizes.length;i++)
                {
                    MyArrayList2.add(prizes[i]);
                }
                ArrayAdapter<String> newArrayAdapter2 = new ArrayAdapter<String>(MainActivity5.this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,MyArrayList2);
                newArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerinred.setAdapter(newArrayAdapter2);
            }
        },null);
        requestQueue.add(request5);

        spinnerinred.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pid = adapterView.getSelectedItem().toString();
                String url2 = "http://10.0.2.2:8080/loyaltyfirst/RedemptionDetails.jsp?prizeid="+pid+"&cid="+cid;
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String pdesc = "";
                        String pneeded = "";
                        String[] values = s.trim().split("#");

                        String[] firstset = values[0].split("\\*");
                        pdesc = firstset[0];
                        pneeded = firstset[1];

                        textView18.setText(pdesc);
                        textView20.setText(pneeded);
                        textView22.setText("------------------------------------------------------------------------------------------");

                        String capture = "";
                        for(int i = 0; i< values.length;i++){
                            String[] set = values[i].split("\\*");
                            capture = capture + set[2].substring(0,set[2].indexOf(" ")) + "," + set[3] + "#";
                        }
                        String data = capture.replace(",", "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t").replace("#", "\n\n");
                        textView23.setText(data);
                    }
                },null);
                requestQueue.add(stringRequest2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
