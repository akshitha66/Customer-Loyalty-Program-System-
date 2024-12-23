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

public class MainActivity4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Spinner spinner = findViewById(R.id.spinner);
        Intent intent = getIntent();
        TextView textView12 = findViewById(R.id.textView12);
        TextView textView13 = findViewById(R.id.textView13);
        TextView textView15 = findViewById(R.id.textView15);

        ArrayList<String> MyArrayList = new ArrayList<String>();
        String cid = intent.getStringExtra("cid");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid=" + cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] result = s.trim().split("#");
                for (int i = 0; i < result.length; i++) {
                    String newValue = result[i];
                    String[] result2 = newValue.split("\\*");
                    MyArrayList.add(result2[0]);
                }
                ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, MyArrayList);
                newArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(newArrayAdapter);
            }
        }, null);
        queue.add(request);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tid = adapterView.getSelectedItem().toString();
                String url2 = "http://10.0.2.2:8080/loyaltyfirst/TransactionDetails.jsp?tref="+tid;
                StringRequest request1 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String[] result3 = s.trim().split("#");
                        String[] res3_values = result3[0].split("\\*");
                        String t_date = res3_values[0].substring(0,res3_values[0].indexOf(" "));
                        String t_points = res3_values[1];
                        textView12.setText(t_date);
                        textView13.setText(t_points);
                        String capture = "";
                        for(int i = 0; i< result3.length;i++){
                            String[] values = result3[i].split("\\*");
                            capture = capture + values[2] + "," + values[4] + "," + values[3] + "#";
                        }
                        String data = capture.replace(",", "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t").replace("#", "\n\n");
                        textView15.setText(data);

                    }
                },null);
                queue.add(request1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        }
    }

