package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tillDawn.Main;
import com.tillDawn.Model.*;

import java.util.ArrayList;

public class WorldController {
    private static WorldController instance;
    private PlayerController playerController;
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;
    private float backgroundWidth = 1920;
    private float backgroundHeight = 1080;
    private CameraController cameraController = CameraController.getCameraController();
    private Fence fence;
    private float elapsedTime = 0f;
    private final float fenceStartTime;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private ArrayList<Egg> eggs = new ArrayList<>();


    private WorldController(PlayerController playerController) {
        this.playerController = playerController;
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        backgroundRegion = new TextureRegion(backgroundTexture);
        backgroundRegion.setRegion(0, 0, (int) backgroundWidth, (int) backgroundHeight);
        this.fenceStartTime = App.getInstance().getGameTime() / 2f; // Half game time
        float fenceWidth = 1920;
        float fenceHeight = 1080;
        Player player = App.getInstance().getCurrentPlayer();
        fence = new Fence(fenceWidth, fenceHeight, player.getPosX(), player.getPosY());
    }

    // Public method to provide access to the instance
    public static WorldController getInstance(PlayerController playerController) {
        if (instance == null) {
            instance = new WorldController(playerController);
        }
        return instance;
    }

    public static WorldController getInstance() {
        return instance;
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
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

        playerController.update();

        for (Egg egg : eggs) {
            if (App.getInstance().getCurrentPlayer().getRect().collidesWith(egg.getRect())) {
                egg.setCollected(true);
                System.out.println("Egg collected!");
            }
        }
        elapsedTime += delta;

        if (elapsedTime >= App.getInstance().getGameTime() * 30 && !fence.isActive() && !fence.isWasActive()) {
            fence = new Fence(1920, 1080, App.getInstance().getCurrentPlayer().getPosX(), App.getInstance().getCurrentPlayer().getPosY());
            fence.activate();
        }
        fence.update(delta);
        fence.checkCollision(App.getInstance().getCurrentPlayer(), delta);
    }


    public Fence getFence() {
        return fence;
    }

    public CameraController getCameraController() {
        return cameraController;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
    public void addEgg(Egg egg) {
        eggs.add(egg);
    }

    public ArrayList<Egg> getEggs() {
        return eggs;
    }

}
