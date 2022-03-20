package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.BASE_IMG_URL;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Config.GlideApp;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.getFavModel;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListResultModel;
import com.example.fragments.Model.List.getList;
import com.example.fragments.Recyclers.AddMovieListsRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {

    boolean heartPressed = false;
    FavFilmRequest favFilmRequest;
    Film film;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getArguments();
        film = (Film) bundle.getSerializable("Film");

        TextView txtDetailTitle = view.findViewById(R.id.txtDetailTitle);
        TextView txtDetailDesc = view.findViewById(R.id.txtDetailDesc);
        ImageView imgDetail = view.findViewById(R.id.imgDetail);
        ImageButton btnFav = view.findViewById(R.id.btnFav);
        ImageButton btnAddtoList = view.findViewById(R.id.btnAddtoList);

        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<getFavModel> call = apiCall.getFavourite(API_KEY, SESSION_ID);

        call.enqueue(new Callback<getFavModel>(){
            @Override
            public void onResponse(Call<getFavModel> call, Response<getFavModel> response) {
                if(response.code()!=200){
                    Log.i("testApi", "checkConnection");
                    return;
                }else {
                    ArrayList<Film> arraySearch = new ArrayList<>();
                    arraySearch = response.body().getResults();
                    for(Film favFilm: arraySearch) {
                        if(film.getId() == favFilm.getId()) {
                            btnFav.setImageResource(R.drawable.ic_fav_on);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<getFavModel> call, Throwable t) {

            }
        });

        txtDetailTitle.setText(film.getOriginal_title());
        txtDetailDesc.setText(film.getOverview());

        GlideApp.with(getContext())
                .load(BASE_IMG_URL + film.getPoster_path())
                .centerCrop()
                .into(imgDetail);

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = (!heartPressed) ? R.drawable.ic_fav_on : R.drawable.ic_fav_off;
                heartPressed = current != R.drawable.ic_fav_off;
                btnFav.setImageResource(current);
                if (heartPressed) {
                    favFilmRequest = new FavFilmRequest("movie", film.getId(), true);
                } else {
                    favFilmRequest = new FavFilmRequest("movie", film.getId(), false);
                }

                ApiCall apiCall2 = retrofit.create(ApiCall.class);
                Call<FavFilmResponse> call2 = apiCall2.markFavourite(API_KEY, SESSION_ID, favFilmRequest);

                call2.enqueue(new Callback<FavFilmResponse>(){
                    @Override
                    public void onResponse(Call<FavFilmResponse> call, Response<FavFilmResponse> response) {
                        if(response.code()!=200){
                            Log.i("testApi", "checkConnection");
                            return;
                        }else {
                        }
                    }

                    @Override
                    public void onFailure(Call<FavFilmResponse> call, Throwable t) {

                    }
                });
            }
        });

        btnAddtoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        return view;

    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_movie_to_list, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

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
                    RecyclerView recyclerView = alertCustomdialog.findViewById(R.id.recyclerList);
                    AddMovieListsRecyclerViewAdapter adapter = new AddMovieListsRecyclerViewAdapter(arrayList, getContext(), dialog, film);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
            @Override
            public void onFailure(Call<getList> call, Throwable t) {

            }
        });
    }

}