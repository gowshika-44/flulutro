package com.example.placementactivityapp.tpo;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.admin.SignUp;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AddTpo extends Fragment {

    EditText e1, e2, e3, e4;

    String name="", id="", email="", password="", adkey="";
    Button b1;

    public AddTpo(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
         return inflater.inflate(R.layout.activity_add_tpo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        e1 = (EditText) view.findViewById(R.id.editText8);
        e2 = (EditText) view.findViewById(R.id.editText9);
        e3 = (EditText) view.findViewById(R.id.editText10);
        e4 = (EditText) view.findViewById(R.id.editText11);

        b1 = (Button) view.findViewById(R.id.button2);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        adkey = sharedPreferences.getString("email", "NO");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = e1.getText().toString();
                id = e2.getText().toString();
                email = e3.getText().toString();
                password = e4.getText().toString();

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .addtpo(name, id, email, password, adkey);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String s = response.body().string();
                            Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
            }
        });

    }
}

