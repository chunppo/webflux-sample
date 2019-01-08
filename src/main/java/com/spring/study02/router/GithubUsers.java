package com.spring.study02.router;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubUsers {
    @JsonProperty("login")
    private String login;

    @JsonProperty("id")
    private int id;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("avatar_url")
    private String avatarUrl;
}
