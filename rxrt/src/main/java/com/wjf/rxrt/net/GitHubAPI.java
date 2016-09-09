package com.wjf.rxrt.net;



import com.wjf.rxrt.bean.Contributor;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/9/8.
 */
public interface GitHubAPI {
    @GET("repos/{onwer}/{repo}/contributors")
    Call<ResponseBody> simpleGetDate(@Path("onwer") String onwer, @Path("repo") String path);

    @GET("repos/{onwer}/{repo}/contributors")
    Call<List<Contributor>> GsonGetDate(@Path("onwer") String onwer, @Path("repo") String path);
}
