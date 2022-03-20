package com.example.fragments.Recyclers;


import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.BASE_IMG_URL;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Config.GlideApp;
import com.example.fragments.DetailFragment;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.List.AddMovieResponse;
import com.example.fragments.Model.List.AddMovieToList;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListResponse;
import com.example.fragments.Model.List.ListResultModel;
import com.example.fragments.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMovieListsRecyclerViewAdapter extends RecyclerView.Adapter<AddMovieListsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ListResultModel> arrayList;
    private Context context;
    private AlertDialog dialog;
    private Film film;

    public AddMovieListsRecyclerViewAdapter(ArrayList<ListResultModel> arrN, Context c, AlertDialog dialog, Film film){
        this.arrayList = arrN;
        this.context = c;
        this.dialog = dialog;
        this.film = film;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int i) {

        holder.listTitle.setText(arrayList.get(i).getName());
        holder.itemCount.setText(String.valueOf(arrayList.get(i).getItem_count()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("select", arrayList.get(i).getName());
                ApiCall apiCall = retrofit.create(ApiCall.class);
                Call<AddMovieResponse> call = apiCall.addMovie(arrayList.get(i).getId(),API_KEY, SESSION_ID, film.getId());

                call.enqueue(new Callback<AddMovieResponse>(){
                    @Override
                    public void onResponse(Call<AddMovieResponse> call, Response<AddMovieResponse> response) {
                        if(response.code()!=200){
                            Log.i("list", "checkConnection");
                            return;
                        }else {
                        }
                    }

                    @Override
                    public void onFailure(Call<AddMovieResponse> call, Throwable t) {
                    }
                });
                dialog.dismiss();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listTitle;
        TextView itemCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listTitle = itemView.findViewById(R.id.listTitle);
            itemCount= itemView.findViewById(R.id.itemCount);
        }
    }
}

