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
    private final int TREE_COUNT = 900;
    private boolean autoReload = false;
    private boolean sfxSound = false;
    private boolean musicSound = false;
    private int gameTime;
    private boolean autoAim;
    ArrayList<Monster> monsters = new ArrayList<>();
    public App() {
        for (int i = 0; i < TREE_COUNT; i++) {
            float x = MathUtils.random(-8000, 8000);
            float y = MathUtils.random(-6000, 6000);
            Sprite tree = new Sprite(GameAssetManager.getInstance().getTreeTexture());
            tree.setSize(43, 43);
            tree.setPosition(x, y);
            trees.add(new Tree(tree, x, y));
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
}
