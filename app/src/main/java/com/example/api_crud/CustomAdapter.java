package com.example.api_crud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<com.example.api_crud.CustomAdapter.ViewHolder> {
    private ArrayList<HocSinh> list;
    private LayoutInflater inflater;
    private String url ="https://60b5afdefe923b0017c8467c.mockapi.io/crud";
    private Context context;


    public CustomAdapter(ArrayList<HocSinh> list, Context context) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public com.example.api_crud.CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_1, parent, false);

        context = parent.getContext();


        return new com.example.api_crud.CustomAdapter.ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.api_crud.CustomAdapter.ViewHolder holder, int position) {
        HocSinh student = list.get(position);
        holder.tvName.setText(student.getName());

        holder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteApi(url, list.get(position), context);
                list.remove(position);
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;
        public final Button btndel;
        final com.example.api_crud.CustomAdapter customAdapter;

        public ViewHolder(@NonNull View itemView, com.example.api_crud.CustomAdapter customAdapter) {
            super(itemView);
            this.customAdapter = customAdapter;

            tvName = itemView.findViewById(R.id.tvName);
            btndel = itemView.findViewById(R.id.btnDel);
        }
    }

    private void deleteApi(String url, HocSinh student, Context context){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url + '/' + student.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void changeList(ArrayList<HocSinh> listc){
        list = listc;
        notifyDataSetChanged();
    }


}
