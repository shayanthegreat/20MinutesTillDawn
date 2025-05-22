package com.tillDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tillDawn.Controller.MainController;
import com.tillDawn.Controller.SettingController;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.MusicManager;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.table;

public class SettingView implements Screen {
    private static SettingView instance;
//    public static SettingView getInstance(){
//        if (instance == null){
//            instance = new SettingView(new SettingController(), GameAssetManager.getInstance().getSkin());
//
//        }
//        return instance;
//    }
    private final Skin skin;
    private final Stage stage;
    private final Slider musicVolumeSlider;
    private final CheckBox musicOnCheckBox;
    private final CheckBox sfxOnCheckBox;
    private final CheckBox reloadOnCheckBox;
    private final CheckBox blackAndWhiteCheckBox;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private final SettingController controller;
    private final SelectBox<String> musicTrackSelectBox;
    private final TextButton backButton;
    private Table table = new Table();
    public SettingView(SettingController controller, Skin skin) {
        this.skin = skin;
        this.controller = controller;
        this.stage = new Stage(new ScreenViewport());
        musicVolumeSlider = new Slider(0f, 100f, 1f, false, skin);
        musicOnCheckBox = new CheckBox("Music On", skin);
        sfxOnCheckBox = new CheckBox("Sfx On", skin);
        reloadOnCheckBox = new CheckBox("Reload On", skin);
        blackAndWhiteCheckBox = new CheckBox("Black and White", skin);
        musicTrackSelectBox = new SelectBox<>(skin);
        musicTrackSelectBox.setItems("music1.mp3", "music2.mp3", "music3.mp3", "music4.mp3", "music5.mp3"); // Change these to match your assets
        this.backButton = new TextButton("Main Menu", skin);
        // Music On
        musicVolumeSlider.setValue(MusicManager.getMusicManager().getVolume() * 100f);
        musicOnCheckBox.setChecked(MusicManager.getMusicManager().getCurrentMusic().isPlaying());
        sfxOnCheckBox.setChecked(App.getInstance().isSfxSound());
        reloadOnCheckBox.setChecked(App.getInstance().isAutoReload());
        blackAndWhiteCheckBox.setChecked(false);
    }


    @Override
    public void show() {
        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        table.setFillParent(true);
        table.center();
        table.add(new Label("Music Track", skin)).left();
        table.add(musicTrackSelectBox).width(200);
        table.row().pad(30, 0, 30, 0);
        table.add(new Label("Music Volume", skin)).left();
        musicVolumeSlider.setValue(MusicManager.getMusicManager().getVolume() * 100f);
        table.add(musicVolumeSlider).width(400);
        table.row().pad(30, 0, 30, 0);


        table.add(musicOnCheckBox).colspan(2);
        table.row();

        // SFX On
        table.add(sfxOnCheckBox).colspan(2);
        table.row();

        // Player Controller
        table.add(new Label("Reload", skin)).left();
        // Reload On
        table.add(reloadOnCheckBox).colspan(2);
        table.row();

        // Black and White Mode
        table.add(blackAndWhiteCheckBox).colspan(2);
        table.row();

        addButton(backButton);
        // === Listeners ===
        stage.addActor(table);

        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = musicVolumeSlider.getValue();
                volume /= 100f;
                MusicManager.getMusicManager().setVolume(volume);
            }
        });

        musicOnCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean on = musicOnCheckBox.isChecked();
                if(!on){
                    MusicManager.getMusicManager().pause();
                }
                else{
                    MusicManager.getMusicManager().resume();
                }
            }
        });

        sfxOnCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean on = sfxOnCheckBox.isChecked();
                App.getInstance().setSfxSound(on);
            }
        });

        musicTrackSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedTrack = musicTrackSelectBox.getSelected();
                System.out.println("Selected track: " + selectedTrack);
                controller.setMusicTrack(selectedTrack); // Assumes your controller handles music switching
            }
        });

        reloadOnCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean reloadEnabled = reloadOnCheckBox.isChecked();
                App.getInstance().setAutoReload(reloadEnabled);
            }
        });

        blackAndWhiteCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean bw = blackAndWhiteCheckBox.isChecked();
                System.out.println("Black & White mode: " + (bw ? "ENABLED" : "DISABLED"));
                // TODO: controller.setBlackAndWhiteMode(bw);
            }
        });
        Gdx.input.setInputProcessor(stage);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new MainView(new MainController(), GameAssetManager.getInstance().getSkin()));
            }
        });
    }

    private void applyHoverEffect(final TextButton button) {
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.addAction(Actions.parallel(
                    Actions.scaleTo(1.05f, 1.05f, 0.1f),
                    Actions.color(new Color(1f, 1f, 0.7f, 1f), 0.1f)
                ));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.addAction(Actions.parallel(
                    Actions.scaleTo(1f, 1f, 0.1f),
                    Actions.color(Color.WHITE, 0.1f)
                ));
            }
        });
    }

    private void addButton(TextButton button) {
        button.setScale(1f);
        applyHoverEffect(button);
        table.row();
        table.add(button).width(500).pad(10);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
