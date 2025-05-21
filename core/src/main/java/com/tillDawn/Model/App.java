package com.tillDawn.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class App {
    private static App app;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private Player currentPlayer = new Player();
    private ArrayList<Tree> trees = new ArrayList<>();
    private final int TREE_COUNT = 500;
    private boolean autoReload = false;

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

}
