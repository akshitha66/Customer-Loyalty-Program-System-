package com.example.loyaltyfirst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class MainActivity6 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        Spinner spinnerinadd = findViewById(R.id.spinner3);
        Intent intent = getIntent();
        String cid = intent.getStringExtra("cid");
        TextView textView26 = findViewById(R.id.textView26);
        TextView textView28 = findViewById(R.id.textView28);
        TextView textView30 = findViewById(R.id.textView30);
        Button button7 = findViewById(R.id.button7);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<String> MyArrayList = new ArrayList<String>();
                String[] resultin6 = s.trim().split("#");
                for(int i =0; i<resultin6.length;i++)
                {
                    String set = resultin6[i];
                    String[] result2 = set.split("\\*");
                    MyArrayList.add(result2[0]);
                }
                ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<String>(MainActivity6.this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,MyArrayList);
                newArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerinadd.setAdapter(newArrayAdapter);
            }
        },null);
        queue.add(request);

        spinnerinadd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tranref = adapterView.getSelectedItem().toString();
                String url6 = "http://10.0.2.2:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid="+cid+"&tref="+tranref;
                StringRequest request2 = new StringRequest(Request.Method.GET, url6, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String[] resp = s.trim().split("\\*");
                        textView26.setText(resp[2]);
                        textView28.setText(resp[0]);
                        textView30.setText(resp[1]);
                    }
                },null);
                queue.add(request2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pts = Integer.parseInt(textView26.getText().toString());
                String familyid = textView28.getText().toString();
                int percent = Integer.parseInt(textView30.getText().toString());
                float multiplier = (float)(percent/100.0);
                float addpts = multiplier*pts;
                String url7 = "http://10.0.2.2:8080/loyaltyfirst/FamilyIncrease.jsp?fid="+familyid+"&cid="+cid+"&npoints="+Math.round(addpts);
                StringRequest request3 = new StringRequest(Request.Method.GET, url7, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(MainActivity6.this,addpts+" Points added to the members of Family id "+familyid,Toast.LENGTH_LONG).show();
                    }
                },null);
                queue.add(request3);
            }
        });
    }

}
