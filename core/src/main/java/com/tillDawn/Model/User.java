package com.tillDawn.Model;

public class User {
    private String name;
    private String password;
    private String question;
    private String answer;
    private String avatarPath;
    public User(String name, String password, String question, String answer) {
        this.name = name;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.avatarPath = "avatar/Avatar1.png";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
