package com.tillDawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tillDawn.Controller.GameController;
import com.tillDawn.Controller.MainController;
import com.tillDawn.Controller.RegisterMenuController;
import com.tillDawn.Controller.SettingController;
import com.tillDawn.Model.App;
import com.tillDawn.Model.FileChooser;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.MusicManager;
import com.tillDawn.View.GameView;
import com.tillDawn.View.MainView;
import com.tillDawn.View.RegisterMenuView;
import com.tillDawn.View.SettingView;

public class Main extends Game {
    private static Main instance; // Only assigned inside create()
    private SpriteBatch batch;
    private FileChooser fileChooser;
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void create() {
        instance = this;
        batch = new SpriteBatch();
        //setScreen(new RegisterMenuView(new RegisterMenuController(), GameAssetManager.getInstance().getSkin()));
        setScreen(new GameView(new GameController(), GameAssetManager.getInstance().getSkin()));
//        setScreen(new MainView(new MainController(), GameAssetManager.getInstance().getSkin()));
        //setScreen(SettingView.getInstance());
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
