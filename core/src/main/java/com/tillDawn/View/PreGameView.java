package com.tillDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDawn.Controller.GameController;
import com.tillDawn.Controller.MainController;
import com.tillDawn.Controller.PreGameController;
import com.tillDawn.Controller.SettingController;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.MusicManager;

public class PreGameView implements Screen {
    private final Skin skin;
    private final Stage stage;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private final PreGameController controller;
    private Table table = new Table();
    public PreGameView(PreGameController controller, Skin skin) {
        this.skin = skin;
        this.controller = controller;
        this.stage = new Stage(new ScreenViewport());
    }
    @Override
    public void show() {

        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        table.setFillParent(true);
        table.center();

        // Weapon textures (replace with your actual file paths)
        Texture[] weaponTextures = {
            new Texture(Gdx.files.internal("gun/revolver.png")),
            new Texture(Gdx.files.internal("gun/shotgun.png")),
            new Texture(Gdx.files.internal("gun/smg.png"))
        };
        final Image weaponImage = new Image(weaponTextures[0]);

        final int[] weaponIndex = {0};

        TextButton weaponLeft = new TextButton("<", skin);
        TextButton weaponRight = new TextButton(">", skin);

        weaponLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                weaponIndex[0]--;
                if (weaponIndex[0] < 0) {
                    weaponIndex[0] = weaponTextures.length - 1;
                }
                weaponImage.setDrawable(new Image(weaponTextures[weaponIndex[0]]).getDrawable());
            }
        });

        weaponRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                weaponIndex[0]++;
                if (weaponIndex[0] >= weaponTextures.length) {
                    weaponIndex[0] = 0;
                }
                weaponImage.setDrawable(new Image(weaponTextures[weaponIndex[0]]).getDrawable());
            }
        });

        Table weaponSwitchTable = new Table();
        weaponSwitchTable.add(weaponLeft).padRight(10);
        weaponSwitchTable.add(weaponImage).size(100, 100).pad(10);
        weaponSwitchTable.add(weaponRight).padLeft(10);

        table.row();
        table.add(weaponSwitchTable).pad(20);


        Texture[] characterTextures = {
            new Texture(Gdx.files.internal("Heros/Dasher/idle/Idle_0.png")),
            new Texture(Gdx.files.internal("Heros/Diamond/idle/Idle_0.png")),
            new Texture(Gdx.files.internal("Heros/Lilith/idle/Idle_0.png")),
            new Texture(Gdx.files.internal("Heros/Scarlet/idle/Idle_0.png")),
            new Texture(Gdx.files.internal("Heros/Shana/idle/Idle_0.png"))
        };
        final Image characterImage = new Image(characterTextures[0]);

        final int[] currentIndex = {0};

        TextButton leftArrow = new TextButton("<", skin);
        TextButton rightArrow = new TextButton(">", skin);

        leftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentIndex[0]--;
                if (currentIndex[0] < 0) {
                    currentIndex[0] = characterTextures.length - 1;
                }
                characterImage.setDrawable(new Image(characterTextures[currentIndex[0]]).getDrawable());
            }
        });

        rightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentIndex[0]++;
                if (currentIndex[0] >= characterTextures.length) {
                    currentIndex[0] = 0;
                }
                characterImage.setDrawable(new Image(characterTextures[currentIndex[0]]).getDrawable());
            }
        });

        Table characterSwitchTable = new Table();
        characterSwitchTable.add(leftArrow).padRight(10);
        characterSwitchTable.add(characterImage).size(64, 80).pad(10);
        characterSwitchTable.add(rightArrow).padLeft(10);

        table.row();
        table.add(characterSwitchTable).pad(20);

        // Game time options
        final int[] gameTimes = {2, 5, 10, 20};
        final int[] selectedGameTimeIndex = {0};
        final Label gameTimeLabel = new Label(gameTimes[selectedGameTimeIndex[0]] + "", skin);
        gameTimeLabel.setFontScale(1.8f);
        gameTimeLabel.setWidth(100);
        TextButton timeLeft = new TextButton("<", skin);
        TextButton timeRight = new TextButton(">", skin);

        timeLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedGameTimeIndex[0]--;
                if (selectedGameTimeIndex[0] < 0) {
                    selectedGameTimeIndex[0] = gameTimes.length - 1;
                }
                gameTimeLabel.setText(gameTimes[selectedGameTimeIndex[0]]);
                gameTimeLabel.setFontScale(1.8f);
            }
        });

        timeRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedGameTimeIndex[0]++;
                if (selectedGameTimeIndex[0] >= gameTimes.length) {
                    selectedGameTimeIndex[0] = 0;
                }
                gameTimeLabel.setText(gameTimes[selectedGameTimeIndex[0]]);
                gameTimeLabel.setFontScale(1.8f);
            }
        });

        Table timeSelector = new Table();
        timeSelector.add(timeLeft).padRight(30);
        timeSelector.add(gameTimeLabel).pad(20);
        timeSelector.add(timeRight).padLeft(30);

        table.row();
        table.add(timeSelector).padTop(20);

        stage.addActor(table);

        TextButton startButton = new TextButton("Start Game", skin);
        TextButton backButton = new TextButton("Back", skin);

        table.row().padTop(50);

        addButton(startButton);

        addButton(backButton);

        Gdx.input.setInputProcessor(stage);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startGame(weaponIndex[0], currentIndex[0], selectedGameTimeIndex[0]);
                Main.getInstance().setScreen(new GameView(new GameController(), GameAssetManager.getInstance().getSkin()));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
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
