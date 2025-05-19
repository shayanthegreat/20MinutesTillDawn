package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.Tree;

import java.util.ArrayList;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;
    private float backgroundWidth = 1920;
    private float backgroundHeight = 1080;
    private ArrayList<Tree> trees = new ArrayList<>();
    private final int TREE_COUNT = 50; // adjust as needed
    public WorldController(PlayerController playerController) {
        this.playerController = playerController;
        for (int i = 0; i < TREE_COUNT; i++) {
            float x = MathUtils.random(0, 1920);
            float y = MathUtils.random(0, 1080);
            Sprite tree = new Sprite(GameAssetManager.getInstance().getTreeTexture());
            tree.setSize(30, 30);
            tree.setPosition(x, y);
            trees.add(new Tree(tree, x, y));
        }
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        backgroundRegion = new TextureRegion(backgroundTexture);
        backgroundRegion.setRegion(0, 0, (int) backgroundWidth, (int) backgroundHeight);
    }

    public void update() {
        float playerX = App.getInstance().getCurrentPlayer().getPosX();
        float playerY = App.getInstance().getCurrentPlayer().getPosY();

        // Offset background based on player position
        backgroundRegion.setRegion((int) playerX, (int) playerY, (int) backgroundWidth, (int) backgroundHeight);

        Main.getInstance().getBatch().draw(backgroundRegion, 0, 0, backgroundWidth, backgroundHeight);

//        for (Tree tree : trees) {
//            float screenX = tree.getWorldX() - playerX + backgroundWidth / 2f;
//            float screenY = tree.getWorldY() - playerY + backgroundHeight / 2f;
//
//            tree.getSprite().setPosition(screenX, screenY);
//            tree.getSprite().draw(Main.getInstance().getBatch());
//        }
        playerController.update(); // Draw player after background
    }
}
