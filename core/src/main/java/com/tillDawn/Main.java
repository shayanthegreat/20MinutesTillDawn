package com.tillDawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tillDawn.Controller.*;
import com.tillDawn.Model.App;
import com.tillDawn.Model.FileChooser;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.MusicManager;
import com.tillDawn.View.*;

public class Main extends Game {
    private static Main instance; // Only assigned inside create()
    private SpriteBatch batch;
    private FileChooser fileChooser;
    public static Main getInstance() {
        return instance;
    }

    public Pixmap scalePixmap(Pixmap original, int targetWidth, int targetHeight) {
        Pixmap scaled = new Pixmap(targetWidth, targetHeight, original.getFormat());
        scaled.drawPixmap(
            original,
            0, 0, original.getWidth(), original.getHeight(), // source rectangle (full original)
            0, 0, targetWidth, targetHeight                    // destination rectangle (scaled)
        );
        return scaled;
    }
    @Override
    public void create() {
        instance = this;
        batch = new SpriteBatch();
        Pixmap original = new Pixmap(Gdx.files.internal("crossHair.png"));
        int newWidth = original.getWidth() / 32;
        int newHeight = original.getHeight() / 32;

        Pixmap scaled = scalePixmap(original, newWidth, newHeight);

        Cursor cursor = Gdx.graphics.newCursor(scaled, newWidth / 2, newHeight / 2);
        Gdx.graphics.setCursor(cursor);
        original.dispose();
        scaled.dispose();


        //setScreen(new RegisterMenuView(new RegisterMenuController(), GameAssetManager.getInstance().getSkin()));
        //setScreen(new GameView(new GameController(), GameAssetManager.getInstance().getSkin()));
        //setScreen(new MainView(new MainController(), GameAssetManager.getInstance().getSkin()));
        //setScreen(SettingView.getInstance());
        //setScreen(new PreGameView(new PreGameController(), GameAssetManager.getInstance().getSkin()));
        setScreen(new KeyView());
    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void setFileChooser(FileChooser chooser) {
        this.fileChooser = chooser;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }
    public SpriteBatch getBatch() {
        return batch;
    }
}
