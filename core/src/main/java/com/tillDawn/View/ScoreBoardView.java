package com.tillDawn.View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDawn.Controller.MainController;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.User;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreBoardView implements Screen {
    private Stage stage;
    private Table table;
    private TextButton backButton;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Skin skin;
    private static final Color GOLD = new Color(1f, 0.84f, 0f, 1f);     // Gold
    private static final Color SILVER = new Color(0.75f, 0.75f, 0.75f, 1f); // Silver
    private static final Color BRONZE = new Color(0.8f, 0.5f, 0.2f, 1f);    // Bronze

    private enum SortMode {
        SCORE, USERNAME, KILL, ALIVE
    }
    private SortMode sortMode = SortMode.SCORE;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = GameAssetManager.getInstance().getSkin();

        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        table = new Table();
        table.setFillParent(true);
        table.center();

        Label titleLabel = new Label("Scoreboard", skin);
        titleLabel.setFontScale(2f);
        titleLabel.setColor(Color.WHITE);

        table.add(titleLabel).colspan(6).padBottom(30);
        table.row();

        // Sort buttons
        addSortButtons();
        table.row().padBottom(15);

        renderUserRows();

        backButton = new TextButton("Back", skin);
        applyHoverEffect(backButton);
        table.row();
        table.add(backButton).colspan(6).width(300).padTop(30);

        stage.addActor(table);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new MainView(new MainController(), skin));
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    private void addSortButtons() {
        TextButton scoreBtn = new TextButton("Sort by Score", skin);
        TextButton userBtn = new TextButton("Sort by Username", skin);
        TextButton killBtn = new TextButton("Sort by Kill", skin);
        TextButton aliveBtn = new TextButton("Sort by Alive Time", skin);

        scoreBtn.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                sortMode = SortMode.SCORE;
                refreshTable();
            }
        });
        userBtn.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                sortMode = SortMode.USERNAME;
                refreshTable();
            }
        });
        killBtn.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                sortMode = SortMode.KILL;
                refreshTable();
            }
        });
        aliveBtn.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                sortMode = SortMode.ALIVE;
                refreshTable();
            }
        });

        table.add(scoreBtn).pad(5);
        table.add(userBtn).pad(5);
        table.add(killBtn).pad(5);
        table.add(aliveBtn).pad(5);
    }

    private void renderUserRows() {
        ArrayList<User> users = new ArrayList<>(App.getInstance().getUsers());
        User loggedIn = App.getInstance().getCurrentUser();

        Comparator<User> comparator;

        switch (sortMode) {
            case USERNAME:
                comparator = Comparator.comparing(User::getName);
                break;
            case KILL:
                comparator = Comparator.comparingInt(User::getKillCount).reversed();
                break;
            case ALIVE:
                comparator = Comparator.comparingInt(User::getAliveTime).reversed();
                break;
            default:
                comparator = Comparator.comparingInt(User::getScore).reversed();
                break;
        }
        users = users.stream().sorted(comparator).limit(10).collect(Collectors.toCollection(ArrayList::new));

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Color rowColor = Color.WHITE;

            if (user == loggedIn) rowColor = Color.CYAN;
            else if (i == 0) rowColor = Color.GOLD;
            else if (i == 1) rowColor = SILVER;
            else if (i == 2) rowColor = BRONZE;

            Label rank = new Label((i + 1) + ".", skin);
            Label name = new Label(user.getName(), skin);
            Label score = new Label(user.getScore() + "", skin);
            Label kills = new Label(user.getKillCount() + "", skin);
            Label alive = new Label(user.getAliveTime() + "s", skin);

            rank.setColor(rowColor);
            name.setColor(rowColor);
            score.setColor(rowColor);
            kills.setColor(rowColor);
            alive.setColor(rowColor);

            table.row();
            table.add(rank).align(Align.right).padRight(10);
            table.add(name).width(150).align(Align.left);
            table.add(score).width(100).align(Align.right);
            table.add(kills).width(100).align(Align.right);
            table.add(alive).width(100).align(Align.right);
        }
    }

    private void refreshTable() {
        table.clearChildren();
        show();
    }

    private void applyHoverEffect(final TextButton button) {
        button.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public void enter(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                button.addAction(com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel(
                        com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo(1.05f, 1.05f, 0.1f),
                        com.badlogic.gdx.scenes.scene2d.actions.Actions.color(new Color(1f, 1f, 0.7f, 1f), 0.1f)
                ));
            }

            @Override
            public void exit(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                button.addAction(com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel(
                        com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo(1f, 1f, 0.1f),
                        com.badlogic.gdx.scenes.scene2d.actions.Actions.color(Color.WHITE, 0.1f)
                ));
            }
        });
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
