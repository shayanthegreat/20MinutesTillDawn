package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.Tree;

import java.util.ArrayList;

public class WorldController {
    private static WorldController instance;
    private PlayerController playerController;
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;
    private float backgroundWidth = 1920;
    private float backgroundHeight = 1080;

     // adjust as needed
    // Private constructor to prevent instantiation
    private WorldController(PlayerController playerController) {
        this.playerController = playerController;
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        backgroundRegion = new TextureRegion(backgroundTexture);
        backgroundRegion.setRegion(0, 0, (int) backgroundWidth, (int) backgroundHeight);
    }

    // Public method to provide access to the instance
    public static WorldController getInstance(PlayerController playerController) {
        if (instance == null) {
            instance = new WorldController(playerController);
        }
        return instance;
    }

    public void update() {
        float playerX = App.getInstance().getCurrentPlayer().getPosX();
        float playerY = App.getInstance().getCurrentPlayer().getPosY();

        float screenCenterX = backgroundWidth / 2f;
        float screenCenterY = backgroundHeight / 2f;

        // Offset background based on player position
        backgroundRegion.setRegion((int) playerX, (int) playerY, (int) backgroundWidth, (int) backgroundHeight);

        Main.getInstance().getBatch().draw(backgroundRegion, 0, 0, backgroundWidth, backgroundHeight);
        ArrayList<Tree> trees = App.getInstance().getTrees();
        for (Tree tree : trees) {
            float screenX = tree.getWorldX() - playerX + screenCenterX;
            float screenY = tree.getWorldY() - playerY + screenCenterY;

            tree.getSprite().setPosition(screenX, screenY);
            tree.getSprite().draw(Main.getInstance().getBatch());
        }

        playerController.update(); // Draw player after background
    }

}
