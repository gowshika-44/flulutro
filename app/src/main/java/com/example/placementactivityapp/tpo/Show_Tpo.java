package com.example.placementactivityapp.tpo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.placementactivityapp.POSO.Tpo;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Show_Tpo extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyTpoAdapter myTpoAdapter;

    String email="";

    public Show_Tpo(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
       return inflater.inflate(R.layout.activity_show__tpo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView)view.findViewById(R.id.rcv1);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "NO");

        Call<List<Tpo>> call = RetrofitClient
                .getInstance()
                .getApi()
                .showTpo(email);

        call.enqueue(new Callback<List<Tpo>>() {
            @Override
            public void onResponse(Call<List<Tpo>> call, Response<List<Tpo>> response) {
                List<Tpo> tpos = response.body();
                myTpoAdapter = new MyTpoAdapter(tpos, getActivity().getApplicationContext());
                recyclerView.setAdapter(myTpoAdapter);
            }

            @Override
            public void onFailure(Call<List<Tpo>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    }
