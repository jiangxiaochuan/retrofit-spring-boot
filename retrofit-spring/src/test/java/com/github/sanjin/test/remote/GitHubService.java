package com.github.sanjin.test.remote;

import com.github.sanjin.annotation.RetrofitConfig;
import com.github.sanjin.test.remote.model.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

@RetrofitConfig("https://api.github.com")
public interface GitHubService {
  @GET("users/{user}/repos")
  List<Repo> listReposWithRetrofitSpring(@Path("user") String user);

  @GET("users/{user}/repos")
  Call<List<Repo>> listReposWithRetrofit2(@Path("user") String user);
}