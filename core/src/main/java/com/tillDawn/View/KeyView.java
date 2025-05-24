package com.tillDawn.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDawn.Controller.SettingController;
import com.tillDawn.Main;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.KeyBindings;

public class KeyView implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private boolean waitingForKey = false;
    private String actionToRebind = null;
    private Label statusLabel;
    private TextButton backButton;
    private Table table;

    private boolean isKeyUsed(int keycode, String excludingAction) {
        if (!excludingAction.equals("MOVE_LEFT") && KeyBindings.MOVE_LEFT == keycode) return true;
        if (!excludingAction.equals("MOVE_UP") && KeyBindings.MOVE_UP == keycode) return true;
        if (!excludingAction.equals("MOVE_DOWN") && KeyBindings.MOVE_DOWN == keycode) return true;
        if (!excludingAction.equals("MOVE_RIGHT") && KeyBindings.MOVE_RIGHT == keycode) return true;
        if (!excludingAction.equals("ACTION_RELOAD") && KeyBindings.ACTION_RELOAD == keycode) return true;
        if (!excludingAction.equals("ACTION_AIM") && KeyBindings.ACTION_AIM == keycode) return true;
        if (!excludingAction.equals("ACTION_CLICK") && KeyBindings.ACTION_CLICK == keycode) return true;
        return false;
    }

    @Override
    public void show() {
        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        skin = GameAssetManager.getInstance().getSkin();
        stage = new Stage(new ScreenViewport());

        stage.addActor(backgroundImage);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.top().padTop(10);
        stage.addActor(table);

        statusLabel = new Label("", skin);
        backButton = new TextButton("Back", skin);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getInstance().setScreen(new SettingView(new SettingController(), skin));
            }
        });

        buildBindingsTable(table);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (waitingForKey && actionToRebind != null) {
                    if (isKeyUsed(keycode, actionToRebind)) {
                        statusLabel.setText("Key " + Input.Keys.toString(keycode) + " already assigned to another action!");
                        return true;
                    }
                    KeyBindings.setBinding(actionToRebind, keycode);
                    buildBindingsTable(table);
                    waitingForKey = false;
                    statusLabel.setText("Rebound " + actionToRebind + " to " + Input.Keys.toString(keycode));
                    return true;
                }
                return false;
            }
        }));
    }

    private Table createRebindEntry(String action, int keyCode) {
        Table entry = new Table();
        Label label = new Label(action + " (" + Input.Keys.toString(keyCode) + ")", skin);
        TextButton button = new TextButton("Rebind", skin);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                waitingForKey = true;
                actionToRebind = action;
                statusLabel.setText("Press a key to rebind " + action + "...");
            }
        });

        entry.add(label).padRight(10);
        entry.add(button);
        return entry;
    }

    private void buildBindingsTable(Table table) {
        table.clearChildren();

        table.add(new Label("Rebind Controls (click a button):", skin)).colspan(4).padBottom(20).row();

        String[] actions = {
            "MOVE_LEFT", "MOVE_UP", "MOVE_DOWN", "MOVE_RIGHT",
            "ACTION_RELOAD", "ACTION_AIM", "ACTION_CLICK"
        };
        int[] keys = {
            KeyBindings.MOVE_LEFT, KeyBindings.MOVE_UP, KeyBindings.MOVE_DOWN, KeyBindings.MOVE_RIGHT,
            KeyBindings.ACTION_RELOAD, KeyBindings.ACTION_AIM, KeyBindings.ACTION_CLICK
        };

        for (int i = 0; i < actions.length; i += 2) {
            Table leftEntry = createRebindEntry(actions[i], keys[i]);
            table.add(leftEntry).pad(10);

            if (i + 1 < actions.length) {
                Table rightEntry = createRebindEntry(actions[i + 1], keys[i + 1]);
                table.add(rightEntry).pad(10);
            } else {
                table.add().pad(10); // empty cell for odd count
            }
            table.row();
        }

        table.row().padTop(10);
        table.add(statusLabel).colspan(4).left();
        table.row();
        table.add(backButton).colspan(4).center();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
