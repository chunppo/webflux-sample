package com.spring.study02.router;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubUsersNames {
    private String title = "최종";

    @JsonProperty("login")
    private String login;

    @JsonProperty("id")
    private int id;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("following_url")
    private String followingUrl;

    @JsonProperty("join_user")
    private String joinUser;

}
