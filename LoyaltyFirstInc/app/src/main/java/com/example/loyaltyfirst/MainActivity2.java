package com.example.loyaltyfirst;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstaceState) {

        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String cid = intent.getStringExtra("cid");
        TextView cusnameview = findViewById(R.id.textView4);
        TextView pointsview = findViewById(R.id.textView5);
        ImageView image_View = findViewById(R.id.imageView);
        Button alltxn = findViewById(R.id.button);
        Button txndetail = findViewById(R.id.button3);
        Button reddetail = findViewById(R.id.button4);
        Button atf = findViewById(R.id.button5);
        Button exit = findViewById(R.id.button6);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Info.jsp?cid="+cid;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] x = s.trim().split("#");
                String name = x[0];
                String points = x[1];

                cusnameview.setText(name);
                pointsview.setText(points);
            }
        },null);
        queue.add(request);

        String url2 ="http://10.0.2.2:8080/loyaltyfirst/images/"+cid+".jpeg";

        ImageRequest request2 = new ImageRequest(url2, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                image_View.setImageBitmap(bitmap);
            }
        },0,0,null,null);
        queue.add(request2);

        alltxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity2.this,MainActivity3.class);
                intent2.putExtra("cid",cid);
                startActivity(intent2);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent5);
            }
        });

        txndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity2.this,MainActivity4.class);
                intent1.putExtra("cid",cid);
                startActivity(intent1);
            }
        });

        reddetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity2.this,MainActivity5.class);
                intent3.putExtra("cid",cid);
                startActivity(intent3);
            }
        });

        atf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity2.this, MainActivity6.class);
                intent4.putExtra("cid",cid);
                startActivity(intent4);
            }
        });




    }
}
