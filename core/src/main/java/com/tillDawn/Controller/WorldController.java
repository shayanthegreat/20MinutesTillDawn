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
    private CameraController cameraController = CameraController.getCameraController();
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
        cameraController.update();
        Main.getInstance().getBatch().setProjectionMatrix(cameraController.getCamera().combined);

        // Draw static background at world position (0, 0)
        float camX = cameraController.getCamera().position.x;
        float camY = cameraController.getCamera().position.y;
        float viewportWidth = cameraController.getCamera().viewportWidth;
        float viewportHeight = cameraController.getCamera().viewportHeight;

// Tile the background around the camera's view
        int tilesX = (int) Math.ceil(viewportWidth / backgroundWidth) + 2;
        int tilesY = (int) Math.ceil(viewportHeight / backgroundHeight) + 2;

        float startX = camX - (viewportWidth / 2) - backgroundWidth;
        float startY = camY - (viewportHeight / 2) - backgroundHeight;

        for (int x = 0; x < tilesX; x++) {
            for (int y = 0; y < tilesY; y++) {
                Main.getInstance().getBatch().draw(
                    backgroundRegion,
                    startX + x * backgroundWidth,
                    startY + y * backgroundHeight
                );
            }
        }


        // Draw trees at world positions
        ArrayList<Tree> trees = App.getInstance().getTrees();
        for (Tree tree : trees) {
            tree.getSprite().setPosition(tree.getWorldX(), tree.getWorldY());
            tree.getSprite().draw(Main.getInstance().getBatch());
        }

        // Draw player last
        playerController.update();
    }


}
