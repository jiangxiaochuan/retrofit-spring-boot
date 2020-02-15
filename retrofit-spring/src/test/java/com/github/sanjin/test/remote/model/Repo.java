package com.github.sanjin.test.remote.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author: sanjin
 * @date: 2020/2/13 上午11:07
 */
@Data
public class Repo {
    /**
     * id : 17881631
     * node_id : MDEwOlJlcG9zaXRvcnkxNzg4MTYzMQ==
     * name : octocat.github.io
     * full_name : octocat/octocat.github.io
     * private : false
     * owner : {"login":"octocat","id":583231,"node_id":"MDQ6VXNlcjU4MzIzMQ==","avatar_url":"https://avatars3.githubusercontent.com/u/583231?v=4","gravatar_id":"","url":"https://api.github.com/users/octocat","html_url":"https://github.com/octocat","followers_url":"https://api.github.com/users/octocat/followers","following_url":"https://api.github.com/users/octocat/following{/other_user}","gists_url":"https://api.github.com/users/octocat/gists{/gist_id}","starred_url":"https://api.github.com/users/octocat/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/octocat/subscriptions","organizations_url":"https://api.github.com/users/octocat/orgs","repos_url":"https://api.github.com/users/octocat/repos","events_url":"https://api.github.com/users/octocat/events{/privacy}","received_events_url":"https://api.github.com/users/octocat/received_events","type":"User","site_admin":false}
     */

    private int id;
    private String node_id;
    private String name;
    private String full_name;
    @SerializedName("private")
    private boolean privateX;
    private OwnerBean owner;

    @Data
    public static class OwnerBean {
        /**
         * login : octocat
         * id : 583231
         * node_id : MDQ6VXNlcjU4MzIzMQ==
         * avatar_url : https://avatars3.githubusercontent.com/u/583231?v=4
         * gravatar_id :
         * url : https://api.github.com/users/octocat
         * html_url : https://github.com/octocat
         * followers_url : https://api.github.com/users/octocat/followers
         * following_url : https://api.github.com/users/octocat/following{/other_user}
         * gists_url : https://api.github.com/users/octocat/gists{/gist_id}
         * starred_url : https://api.github.com/users/octocat/starred{/owner}{/repo}
         * subscriptions_url : https://api.github.com/users/octocat/subscriptions
         * organizations_url : https://api.github.com/users/octocat/orgs
         * repos_url : https://api.github.com/users/octocat/repos
         * events_url : https://api.github.com/users/octocat/events{/privacy}
         * received_events_url : https://api.github.com/users/octocat/received_events
         * type : User
         * site_admin : false
         */

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
