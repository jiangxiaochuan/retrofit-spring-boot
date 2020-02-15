package retrofitautoconfiguretest;

import retrofitautoconfiguretest.remote.GitHubService;
import retrofitautoconfiguretest.remote.model.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import retrofit.spring.boot.autoconfigure.RetrofitScan;

import java.util.List;

/**
 * @author: sanjin
 * @date: 2020/2/15 下午7:42
 */
@SpringBootApplication
@RetrofitScan
public class AutoconfigureTest implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AutoconfigureTest.class,  args);
    }

    @Autowired(required = false)
    private GitHubService gitHubService;
    @Override
    public void run(String... args) throws Exception {
        List<Repo> res = gitHubService.listReposWithRetrofitSpring("jiangxiaochuan");
        System.out.println(res);
    }
}
