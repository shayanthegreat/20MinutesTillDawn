package com.tillDawn.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class App {
    private static App app;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private Player currentPlayer = new Player();
    private User currentUser = new User("shayan", "sabzi", "asdad", "asdasdasd");
    private ArrayList<Tree> trees = new ArrayList<>();
    private final int TREE_COUNT = 500;
    private boolean autoReload = false;
    private boolean sfxSound = false;
    private boolean musicSound = false;
    private CharacterType currentCharacter = CharacterType.Scarlet;
    ArrayList<Monster> monsters = new ArrayList<>();
    public App() {
        for (int i = 0; i < TREE_COUNT; i++) {
            float x = MathUtils.random(-6000, 6000);
            float y = MathUtils.random(-3000, 3000);
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

    public CharacterType getCurrentCharacter() {
        return currentCharacter;
    }

    public void setCurrentCharacter(CharacterType currentCharacter) {
        this.currentCharacter = currentCharacter;
    }
}
