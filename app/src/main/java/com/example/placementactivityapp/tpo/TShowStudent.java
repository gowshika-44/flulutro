package com.example.placementactivityapp.tpo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.placementactivityapp.POSO.Interest;
import com.example.placementactivityapp.POSO.Student;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener4;
import com.example.placementactivityapp.listener.OnItemClickListener5;
import com.example.placementactivityapp.student.InterestCompanyAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class TShowStudent extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    List<Student> students = new ArrayList<Student>();
    EditText editText;

    TShowAdapter tShowAdapter;

    public TShowStudent(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         return inflater.inflate(R.layout.activity_tshow_student,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView)view.findViewById(R.id.rcv1);

        editText = view.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref1", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "NO");
        final String adkey = sharedPreferences.getString("adkey", "NO");

        Call<List<Student>> call = RetrofitClient
                .getInstance()
                .getApi()
                .showStudent(adkey);

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                students = response.body();
                tShowAdapter = new TShowAdapter(students, getActivity(), new OnItemClickListener4() {
                    @Override
                    public void onItemClick(Student student, String item) {

                        if (item.equalsIgnoreCase("company")){
                            Call<List<Interest>> call = RetrofitClient
                                    .getInstance()
                                    .getApi()
                                    .getinterest(student.getEmail());
                            call.enqueue(new Callback<List<Interest>>() {
                                @Override
                                public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
                                    List<Interest> interests = response.body();
                                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                                    View  promptview = layoutInflater.inflate(R.layout.tshow_recyclerviewcompany,null);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                    builder.setView(promptview);


                                    RecyclerView recyclerView1;
                                    RecyclerView.LayoutManager layoutManager;
                                    InterestCompanyAdapter interestCompanyAdapter;

                                    recyclerView1 = promptview.findViewById(R.id.rcv1);

                                    layoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView1.setHasFixedSize(true);
                                    recyclerView1.setLayoutManager(layoutManager);

                                    interestCompanyAdapter = new InterestCompanyAdapter(interests, getActivity(), new OnItemClickListener5() {
                                        @Override
                                        public void onItemClick5(Interest interest) {

                                        }
                                    });
                                    recyclerView1.setAdapter(interestCompanyAdapter);

                                    builder.setTitle("Choices");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                }

                                @Override
                                public void onFailure(Call<List<Interest>> call, Throwable t) {
                                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }
                });
                recyclerView.setAdapter(tShowAdapter);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filter(String text) {
        ArrayList<Student> filteredList = new ArrayList<Student>();

        for (Student item : students) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        tShowAdapter.filterList(filteredList);
    }
}
