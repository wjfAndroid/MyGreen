package com.wjf.rxrt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wjf.rxrt.bean.Contributor;
import com.wjf.rxrt.net.GitHubAPI;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String baseurl = "https://api.github.com/";
    Retrofit retrofit;
    GitHubAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        simpleRetrofit();


    }

    public void simpleRetrofit() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseurl)
                .build();

        api = retrofit.create(GitHubAPI.class);

        Call<ResponseBody> call = api.simpleGetDate("square", "retrofit");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println("response = " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void gsonRetrofit() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(GitHubAPI.class);

        Call<List<Contributor>> call = api.GsonGetDate("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                System.out.println("response = " + response.body());
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });


    }
}
