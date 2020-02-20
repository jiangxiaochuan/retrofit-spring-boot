# Retrofit Spring Boot Starter
easy use retrofit in Spring Boot or Spring

# Usage
1. 在**Spring Boot**中引入依赖
```xml
<dependency>
    <groupId>com.github.jiangxch</groupId>
    <artifactId>retrofit-spring-boot-starter</artifactId>
    <version>1.0.0-RELEASE</version>
</dependency>

```

2. 定义API接口
```java
// api接口
@RetrofitConfig("https://api.github.com")
public interface GitHubService {
  @GET("users/{user}/repos")
  List<Repo> listRepos(@Path("user") String user);
}

// 实体类
@Data
public class Repo {
    private int id;
    private String node_id;
    private String name;
    private String full_name;
    @SerializedName("private")
    private boolean privateX;
    private OwnerBean owner;

    @Data
    public static class OwnerBean {
        private String login;
        private int id;
        private String node_id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private boolean site_admin;

        }
}

```

3. 使用
```java
@SpringBootApplication
public class AutoconfigureTest  implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(
                AutoconfigureTest.class,
                args
        );
    }

    @Autowired(required = false)
    private GitHubService gitHubService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("spring boot test");
        List<Repo> res = gitHubService.listRepos("jiangxch");
        System.out.println(res);
    }
}
```
