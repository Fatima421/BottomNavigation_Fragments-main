package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.getFavModel;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListResponse;
import com.example.fragments.Model.List.ListResultModel;
import com.example.fragments.Model.List.getList;
import com.example.fragments.Recyclers.ListMovieRecyclerViewAdapter;
import com.example.fragments.Recyclers.SearchMovieRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {
    // Properties
    ListModel list;
    RecyclerView recyclerView;

    public ListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        FloatingActionButton btnAdd = view.findViewById(R.id.btnAddList);
        recyclerView = view.findViewById(R.id.recyclerLists);

        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<getList> call = apiCall.getList(API_KEY, SESSION_ID);

        call.enqueue(new Callback<getList>(){
            @Override
            public void onResponse(Call<getList> call, Response<getList> response) {
                if(response.code()!=200){
                    Log.i("testApi", "checkConnection");
                    return;
                }else {
                    ArrayList<ListResultModel> arrayList = new ArrayList<>();
                    arrayList = response.body().getResults();
                    callRecycler(arrayList);
                }
            }
            @Override
            public void onFailure(Call<getList> call, Throwable t) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_add_list, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        Button btnSaveList = alertCustomdialog.findViewById(R.id.btnSaveList);
        EditText listName = alertCustomdialog.findViewById(R.id.txtList);
        EditText description = alertCustomdialog.findViewById(R.id.txtDescription);

        btnSaveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!listName.getText().toString().isEmpty() && !description.toString().isEmpty()) {
                    list = new ListModel(listName.getText().toString(), description.getText().toString(), "en");
                    ApiCall apiCall = retrofit.create(ApiCall.class);
                    Call<ListResponse> call = apiCall.postList(API_KEY, SESSION_ID, list);

                    call.enqueue(new Callback<ListResponse>(){
                        @Override
                        public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                            if(response.code()!=200){
                                Log.i("list", "checkConnection");
                                return;
                            }else {
                            }
                        }

                        @Override
                        public void onFailure(Call<ListResponse> call, Throwable t) {

                        }
                    });
                }
                dialog.dismiss();
            }
        });
    }

    public void callRecycler(ArrayList<ListResultModel> arrayList){
        ListMovieRecyclerViewAdapter adapter = new ListMovieRecyclerViewAdapter(arrayList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}