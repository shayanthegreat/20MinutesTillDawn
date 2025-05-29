package com.tillDawn.Model;

import com.tillDawn.Controller.GameController;
import com.tillDawn.View.GameView;

public class User {
    private String name;
    private String password;
    private String question;
    private String answer;
    private String avatarPath;
    private int xp;
    private int level;
    private GameDetails gameDetails;
    private int score;
    private int aliveTime;
    private int killCount;
    public User() {}
    public User(String name, String password, String question, String answer) {
        this.name = name;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.avatarPath = "avatar/Avatar1.png";
        this.xp = 0;
        this.level = 1;
        this.gameDetails = null;
        this.score = 0;
        this.aliveTime = 0;
        this.killCount = 0;
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

    public int getXp() {
        return xp;
    }

    public void increaseXp(int xp) {
        this.xp += xp;
        if(this.xp >= level * 20){
            this.xp -= level * 20;
            GameController.getInstance().pauseGame();
            level++;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GameDetails getGameDetails() {
        return gameDetails;
    }
    public void setGameDetails(GameDetails gameDetails) {
        this.gameDetails = gameDetails;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAliveTime() {
        return aliveTime;
    }

    public void setAliveTime(int aliveTime) {
        this.aliveTime = aliveTime;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }
}
