package com.tillDawn.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class App {
    private static App app;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private Player currentPlayer = null;
    private User currentUser = new User("guest", "", "", "");
    private ArrayList<Tree> trees = new ArrayList<>();
    private boolean autoReload = false;
    private boolean sfxSound = false;
    private boolean musicSound = false;
    private int gameTime;
    private boolean autoAim;
    ArrayList<Monster> monsters = new ArrayList<>();
    public App() {
        loadUsersFromJson("users.json");
    }

    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isSfxSound() {
        return sfxSound;
    }

    public void setSfxSound(boolean sfxSound) {
        this.sfxSound = sfxSound;
    }

    public boolean isMusicSound() {
        return musicSound;
    }

    public void setMusicSound(boolean musicSound) {
        this.musicSound = musicSound;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public boolean isAutoAim() {
        return autoAim;
    }

    public void setAutoAim(boolean autoAim) {
        this.autoAim = autoAim;
    }

    public void loadUsersFromJson(String path) {
        this.users = UserSaver.loadUsers(path);
    }

    public void saveUsersToJson(String path) {
        UserSaver.saveUsers(this.users, path);
    }

    public void AppCloser(){
        UserSaver.saveUsers(users, "users.json");
    }

//    public void updateOrAddCurrentUser(String path) {
//        boolean updated = false;
//        for (int i = 0; i < users.size(); i++) {
//            if (users.get(i).getName().equals(currentUser.getName())) {
//                users.set(i, currentUser);
//                updated = true;
//                break;
//            }
//        }
//
//        if (!updated && !currentUser.getName().equals("guest")) {
//            users.add(currentUser);
//        }
//
//        saveUsersToJson(path);
//    }
}
