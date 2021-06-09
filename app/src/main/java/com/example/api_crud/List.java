package com.example.api_crud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class List extends AppCompatActivity {
    private RecyclerView rcv;
    private com.example.api_crud.CustomAdapter customAdapter;
    private ArrayList<HocSinh> list = new ArrayList<>();;
    private String url ="https://60b5afdefe923b0017c8467c.mockapi.io/crud";
    private Button btnAdd;
    private EditText edName, edAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rcv = findViewById(R.id.rcv);
        customAdapter = new com.example.api_crud.CustomAdapter(list, getApplicationContext());
        rcv.setAdapter(customAdapter);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getArrayJson(url);
        customAdapter.changeList(list);

        edName = findViewById(R.id.edName_add);
        edAge = findViewById(R.id.edAge_add);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString();
                String agestr = edAge.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(), "Enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(agestr)){
                    Toast.makeText(getApplicationContext(), "Enter age", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(agestr);
                int id = list.size()+20;
                HocSinh student = new HocSinh(id, name, age);
                PostApi(url, student);

                Intent intent = new Intent(List.this, List.class);
                startActivity(intent);
            }
        });

    }

    private void getArrayJson(String url){
        JsonRequest jsonRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++){
                            try{
                                JSONObject object = (JSONObject) response.get(i);
                                HocSinh student = new HocSinh();
                                student.setId(Integer.parseInt(object.get("id").toString()));
                                student.setAge(Integer.parseInt(object.get("age").toString()));
                                student.setName(object.get("name").toString());

                                list.add(student);

                            }catch (JSONException e){
                                e.printStackTrace();;
                            }
                        }
                        customAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(List.this, "Error by getting Json", Toast.LENGTH_SHORT).show();


                    }
                }

        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonRequest);

    }

    private void PostApi(String url, HocSinh student) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(List.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(List.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String>
                        params = new HashMap<>();
                params.put("id", String.valueOf(student.getId()));
                params.put("name", student.getName());
                params.put("age", String.valueOf(student.getAge()));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}