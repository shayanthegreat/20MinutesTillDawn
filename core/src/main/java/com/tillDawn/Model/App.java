package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class App {
    private static App app;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser = new User("guest", "", "", "");
    private GameDetails currentGame = new GameDetails();
    public App() {
        boolean flag = false;
        loadUsersFromJson("users.json");
        for (User user : users) {
            if(user.getName().equals("guest")){
                flag = true;
            }
        }
        if(!flag){
            users.add(currentUser);
        }
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
        return currentGame.player;
    }

    public boolean isAutoReload() {
        return currentGame.autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        currentGame.autoReload = autoReload;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        currentGame.player = currentPlayer;
    }

    public boolean isSfxSound() {
        return currentGame.sfxSound;
    }

    public void setSfxSound(boolean sfxSound) {
        currentGame.sfxSound = sfxSound;
    }

    public boolean isMusicSound() {
        return currentGame.musicSound;
    }

    public void setMusicSound(boolean musicSound) {
        currentGame.musicSound = musicSound;
    }

    public int getGameTime() {
        return currentGame.gameTime;
    }

    public void setGameTime(int gameTime) {
        currentGame.gameTime = gameTime;
    }

    public boolean isAutoAim() {
        return currentGame.autoAim;
    }

    public void setAutoAim(boolean autoAim) {
        currentGame.autoAim = autoAim;
    }

    public void loadUsersFromJson(String path) {
        this.users = UserSaver.loadUsers(path);
    }

    public void saveUsersToJson(String path) {
        UserSaver.saveUsers(this.users, path);
    }

    public void AppCloser(){
        UserSaver.saveUsers(users, "users.json");
        Gdx.app.exit();
    }

    public ArrayList<Monster> getMonsters() {
        return currentGame.monsters;
    }

    public ArrayList<Tree> getTrees(){
        return currentGame.trees;
    }

    public ArrayList<Bullet> getBullets(){
        return currentGame.bullets;
    }

    public ArrayList<Bullet> getEnemyBullets(){
        return currentGame.enemyBullets;
    }

    public GameDetails getCurrentGame() {
        return currentGame;
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


    public void setCurrentGame(GameDetails currentGame) {
        this.currentGame = currentGame;
    }

    //public void se
    public void pause(){
        App.getInstance().getCurrentGame().pause = true;
    }

    public void resume(){
        App.getInstance().getCurrentGame().pause = false;
    }

    public boolean isPause(){
        return App.getInstance().getCurrentGame().pause;
    }
}
