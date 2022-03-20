package com.example.fragments.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DefaultConstants {

    public static final String API_KEY = "afd0559e1934a3726f469c9a51f97eed";
    public static final String SESSION_ID = "cdf094ebf0e39af04dfcde7b9671429b1804cfd6";
    public static final String ACCOUNT_ID = "FatimaSahar";

    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500/";

    public static final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.themoviedb.org/3/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

}
