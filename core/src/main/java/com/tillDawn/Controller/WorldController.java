package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tillDawn.Main;
import com.tillDawn.Model.App;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;
    private float backgroundWidth = 1920;
    private float backgroundHeight = 1080;

    public WorldController(PlayerController playerController) {
        this.playerController = playerController;

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        // Use a region larger than screen to support scrolling
        backgroundRegion = new TextureRegion(backgroundTexture);
        backgroundRegion.setRegion(0, 0, (int) backgroundWidth, (int) backgroundHeight);
    }

    public void update() {
        float playerX = App.getInstance().getCurrentPlayer().getPosX();
        float playerY = App.getInstance().getCurrentPlayer().getPosY();

        // Offset background based on player position
        backgroundRegion.setRegion((int) playerX, (int) playerY, (int) backgroundWidth, (int) backgroundHeight);

        Main.getInstance().getBatch().draw(backgroundRegion, 0, 0, backgroundWidth, backgroundHeight);

        playerController.update(); // Draw player after background
    }
}
