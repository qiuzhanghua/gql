package com.example.gql;

import java.io.Serializable;

public class CreatePostCommand implements Serializable {
    public CreatePostCommand(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public CreatePostCommand() {
    }

    String title;
    String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}