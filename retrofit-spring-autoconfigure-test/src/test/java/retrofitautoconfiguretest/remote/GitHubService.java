package retrofitautoconfiguretest.remote;

import com.github.sanjin.annotation.RetrofitConfig;
import retrofitautoconfiguretest.remote.model.Repo;
import org.springframework.stereotype.Component;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;


@RetrofitConfig("https://api.github.com")
public interface GitHubService {
  @GET("users/{user}/repos")
  List<Repo> listReposWithRetrofitSpring(@Path("user") String user);
}