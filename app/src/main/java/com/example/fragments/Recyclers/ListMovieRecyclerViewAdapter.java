package com.example.fragments.Recyclers;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.BASE_IMG_URL;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Config.GlideApp;
import com.example.fragments.DetailFragment;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.getFavModel;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListResultModel;
import com.example.fragments.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMovieRecyclerViewAdapter extends RecyclerView.Adapter<ListMovieRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ListResultModel> arrayList;
    private Context context;
    RecyclerView recyclerView;

    public ListMovieRecyclerViewAdapter(ArrayList<ListResultModel> arrN, Context c){
        this.arrayList = arrN;
        this.context = c;
    }

    @NonNull
    @Override
    public ListMovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        recyclerView = view.findViewById(R.id.recyclerSearch);
        ListMovieRecyclerViewAdapter.ViewHolder holder = new ListMovieRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListMovieRecyclerViewAdapter.ViewHolder holder, int i) {
        holder.listTitle.setText(arrayList.get(i).getName());
        holder.listDescription.setText(String.valueOf(arrayList.get(i).getItem_count()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                ApiCall apiCall = retrofit.create(ApiCall.class);
                Call<getFavModel> call = apiCall.getDetails(arrayList.get(i).getId(), API_KEY);

                call.enqueue(new Callback<getFavModel>(){
                    @Override
                    public void onResponse(Call<getFavModel> call, Response<getFavModel> response) {
                        if(response.code()!=200){
                            Log.i("testApi", "checkConnection");
                            return;
                        }else {
                            ArrayList<Film> arraySearch = new ArrayList<>();
                            arraySearch = response.body().getResults();
                            callRecycler(arraySearch);
                        }
                    }

                    @Override
                    public void onFailure(Call<getFavModel> call, Throwable t) {

                    }
                });

                 */
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listTitle;
        TextView listDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listTitle = itemView.findViewById(R.id.listTitle);
            listDescription = itemView.findViewById(R.id.itemCount);
        }
    }

    public void callRecycler(ArrayList<Film> arraySearch){
        SearchMovieRecyclerViewAdapter adapter = new SearchMovieRecyclerViewAdapter(arraySearch, context);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.setAdapter(adapter);
    }
}

