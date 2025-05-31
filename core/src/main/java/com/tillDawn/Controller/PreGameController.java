package com.tillDawn.Controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.tillDawn.Model.*;

import java.util.ArrayList;

public class PreGameController extends Controller{
    public void startGame(int x, int y, int z){
        CharacterType characterType = null;
        Weapon weapon = null;
        switch (x){
            case 0:{
                weapon = new Weapon("Revolver");
                break;
            }
            case 1:{
                weapon = new Weapon("Shotgun");
                break;
            }
            case 2:{
                weapon = new Weapon("Smg");
                break;
            }
            default:{

            }
        }
        switch (y){
            case 0:{
                characterType = CharacterType.Dasher;
                break;
            }
            case 1:{
                characterType = CharacterType.Diamond;
                break;
            }
            case 2:{
                characterType = CharacterType.Lilith;
                break;
            }
            case 3:{
                characterType = CharacterType.Scarlet;
                break;
            }
            case 4:{
                characterType = CharacterType.Shana;
                break;
            }
            default:{

            }
        }
        switch (z){
            case 0:{
                App.getInstance().setGameTime(2);
                break;
            }
            case 1:{
                App.getInstance().setGameTime(5);
                break;
            }
            case 2:{
                App.getInstance().setGameTime(10);
                break;
            }
            case 3:{
                App.getInstance().setGameTime(20);
                break;
            }
            default:{

            }
        }

        App.getInstance().loadUsersFromJson("Users.json");
        User newuser = null;
        for (User user : App.getInstance().getUsers()) {
            if(user.getName().equals(App.getInstance().getCurrentUser().getName())){
                newuser = user;
                break;
            }
        }
        if(newuser != null){
            App.getInstance().setCurrentUser(newuser);
        }
        if (App.getInstance().getCurrentUser().getGameDetails() == null) {
            App.getInstance().setAutoAim(false);
            App.getInstance().setCurrentPlayer(new Player(characterType, weapon));
            App.getInstance().getCurrentGame().elapsedTime = 0;
            int numTrees = 200;
            float minDistance = 150f; // Minimum allowed distance between trees5

            ArrayList<Tree> trees = App.getInstance().getTrees();
            Texture treeTexture = GameAssetManager.getInstance().getTreeTexture();

            int attempts = 0;
            int maxAttempts = numTrees * 10; // prevent infinite loop
            while (trees.size() < numTrees && attempts < maxAttempts) {
                float playerX = App.getInstance().getCurrentPlayer().getPosX();
                float playerY = App.getInstance().getCurrentPlayer().getPosY();
                float playerSafeRadius = 300f; // adjust depending on game visuals

                while (trees.size() < numTrees && attempts < maxAttempts) {
                    float t = MathUtils.random(-8000, 8000);
                    float w = MathUtils.random(-6000, 6000);

                    boolean tooClose = false;

                    // Check distance from existing trees
                    for (Tree other : trees) {
                        float dx = other.getWorldX() - t;
                        float dy = other.getWorldY() - w;
                        if (dx * dx + dy * dy < minDistance * minDistance) {
                            tooClose = true;
                            break;
                        }
                    }

                    // Check distance from the player
                    float dxPlayer = playerX - t;
                    float dyPlayer = playerY - w;
                    if (dxPlayer * dxPlayer + dyPlayer * dyPlayer < playerSafeRadius * playerSafeRadius) {
                        tooClose = true;
                    }

                    if (!tooClose) {
                        Sprite sprite = new Sprite(treeTexture);
                        sprite.setSize(140, 182);
                        sprite.setPosition(t, w);
                        trees.add(new Tree(sprite, t, w));
                    }

                    attempts++;
                }
            }
            App.getInstance().getCurrentUser().setGameDetails(App.getInstance().getCurrentGame());
        }
        else{
            App.getInstance().setCurrentGame(App.getInstance().getCurrentUser().getGameDetails());
            App.getInstance().getCurrentGame().player.loadTextures();
            App.getInstance().getCurrentGame().player.getWeapon().loadTextureAndSprite();
            App.getInstance().getCurrentGame().pause = false;
            for (Monster monster : App.getInstance().getCurrentGame().monsters) {
                monster.loadAssets(); // method to load textures, animations, etc.
            }
            for (Tree tree : App.getInstance().getCurrentGame().trees) {
                tree.initialize(); // restores runtime resources
            }
            for (Bullet bullet : App.getInstance().getCurrentGame().bullets) {
                bullet.initialize();
            }
            for (Bullet bullet : App.getInstance().getCurrentGame().enemyBullets) {
                bullet.initialize();
            }
            for (Egg egg : App.getInstance().getCurrentGame().eggs) {
                egg.initialize();
            }

        }


    }
}
