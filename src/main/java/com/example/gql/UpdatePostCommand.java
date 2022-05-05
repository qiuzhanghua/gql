package com.example.gql;

public class UpdatePostCommand {
    String title;
    String content;

    public UpdatePostCommand(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public UpdatePostCommand() {
    }

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
