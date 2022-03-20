package com.example.fragments.Config;

import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.getFavModel;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.AddMovieResponse;
import com.example.fragments.Model.List.AddMovieToList;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListResponse;
import com.example.fragments.Model.List.ListResultModel;
import com.example.fragments.Model.List.getList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall {

    @GET("search/movie?")
    Call<searchFilmModel> getData(@Query("api_key") String api_key, @Query("query") String query);

    @GET("account/FatimaSahar/favorite/movies?")
    Call<getFavModel> getFavourite(@Query("api_key") String api_key, @Query("session_id") String session_id);

    @POST("account/FatimaSahar/favorite?")
    Call<FavFilmResponse> markFavourite(@Query("api_key") String api_key, @Query("session_id") String session_id, @Body FavFilmRequest favFilmRequest);

    @POST("list?")
    Call<ListResponse> postList(@Query("api_key") String api_key, @Query("session_id") String session_id, @Body ListModel list);

    @GET("account/FatimaSahar/lists?")
    Call<getList> getList(@Query("api_key") String api_key, @Query("session_id") String session_id);

    @POST("list/{list_id}/add_item?")
    Call<AddMovieResponse> addMovie(@Path("list_id") String id, @Query("api_key") String api_key, @Query("session_id") String session_id, @Body int media_id);

    @GET("list/{list_id}?")
    Call<getFavModel> getDetails(@Path("list_id") String id, @Query("api_key") String api_key);
}
