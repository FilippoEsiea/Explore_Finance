package com.example.meetfinance;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.meetfinance.data.Constants;
import com.example.meetfinance.data.SymbolAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static SymbolAPI symbolAPIInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson() {
        if (gsonInstance == null) {
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferencesInstance == null) {
            sharedPreferencesInstance = context.getSharedPreferences("Esiea_3A", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }

    public static SymbolAPI getSymbolAPI() {
        if (symbolAPIInstance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            symbolAPIInstance = retrofit.create(SymbolAPI.class);
        }
        return symbolAPIInstance;
    }
}
