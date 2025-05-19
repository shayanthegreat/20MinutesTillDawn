package com.tillDawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tillDawn.Controller.GameController;
import com.tillDawn.Controller.RegisterMenuController;
import com.tillDawn.Model.App;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.View.GameView;
import com.tillDawn.View.RegisterMenuView;

public class Main extends Game {
    private static Main instance; // Only assigned inside create()
    private SpriteBatch batch;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void create() {
        instance = this;
        batch = new SpriteBatch();
        //setScreen(new RegisterMenuView(new RegisterMenuController(), GameAssetManager.getInstance().getSkin()));
        setScreen(new GameView(new GameController(), GameAssetManager.getInstance().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
