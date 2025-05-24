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
        App.getInstance().setAutoAim(false);
        App.getInstance().setCurrentPlayer(new Player(characterType, weapon));
        App.getInstance().getCurrentPlayer().setUser(App.getInstance().getCurrentUser());
        int numTrees = 200;
        float minDistance = 150f; // Minimum allowed distance between trees

        ArrayList<Tree> trees = App.getInstance().getTrees();
        Texture treeTexture = GameAssetManager.getInstance().getTreeTexture();

        int attempts = 0;
        int maxAttempts = numTrees * 10; // prevent infinite loop
        while (trees.size() < numTrees && attempts < maxAttempts) {
            float t = MathUtils.random(-8000, 8000);
            float w = MathUtils.random(-6000, 6000);

            boolean tooClose = false;
            for (Tree other : trees) {
                float dx = other.getWorldX() - t;
                float dy = other.getWorldY() - w;
                if (dx * dx + dy * dy < minDistance * minDistance) {
                    tooClose = true;
                    break;
                }
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
}
