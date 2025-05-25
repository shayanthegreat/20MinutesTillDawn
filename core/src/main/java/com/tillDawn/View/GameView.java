package com.tillDawn.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDawn.Controller.CameraController;
import com.tillDawn.Controller.GameController;
import com.tillDawn.Controller.WorldController;
import com.tillDawn.Main;
import com.tillDawn.Model.*;
import com.tillDawn.Model.Tree;

import java.util.ArrayList;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController gameController;
    private ProgressBar xpBar;
    private Label levelLabel;
    private BitmapFont font = new BitmapFont();
    private boolean levelUpPopupShown = false;
    private boolean pauseMenuButton = false;
    TextButton giveUpButton;
    public GameView(GameController gameController, Skin skin) {
        this.gameController = gameController;
        gameController.setView(this);
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
        giveUpButton = new TextButton("PauseMenu", skin);
        giveUpButton.setWidth(350);
        giveUpButton.setHeight(100);
    }

    @Override
    public boolean keyDown(int keycode) {

        if(gameController.isPaused())
            return false;
        if(KeyBindings.ACTION_CLICK == 0){
            return false;
        }
        if (keycode == KeyBindings.ACTION_CLICK) {
            if(App.getInstance().isAutoAim()){
                Monster monster = gameController.getNearestMonster(2000);
                if (monster != null) {
                    float deltaX = monster.getPosX() - App.getInstance().getCurrentPlayer().getPosX();
                    float deltaY = monster.getPosY() - App.getInstance().getCurrentPlayer().getPosY();
                    float x = deltaX + Gdx.graphics.getWidth() / 2;
                    float y = -deltaY + Gdx.graphics.getHeight() / 2;

                    Gdx.input.setCursorPosition((int) x, (int) y);
                    gameController.getWeaponController().handleWeaponShoot((int) x, (int) y);
                    return false;
                }
            }
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            gameController.getWeaponController().handleWeaponShoot(mouseX, mouseY);
            return false;
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(KeyBindings.ACTION_CLICK != 0){
            return false;
        }
        if(gameController.isPaused())
            return false;
        if(App.getInstance().isAutoAim()){
            Monster monster = gameController.getNearestMonster(2000);
            if (monster != null) {
                float deltaX = monster.getPosX() - App.getInstance().getCurrentPlayer().getPosX();
                float deltaY = monster.getPosY() - App.getInstance().getCurrentPlayer().getPosY();
                float x = deltaX + Gdx.graphics.getWidth() / 2;
                float y = -deltaY + Gdx.graphics.getHeight() / 2;

                Gdx.input.setCursorPosition((int) x, (int) y);
                gameController.getWeaponController().handleWeaponShoot((int) x, (int) y);
                return false;
            }
        }
        gameController.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        gameController.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        Skin skin = GameAssetManager.getInstance().getSkin();

        xpBar = new ProgressBar(0, 100, 1, false, skin);
        xpBar.setWidth(500);
        xpBar.setAnimateDuration(0.25f);

        levelLabel = new Label("Level: 1", skin);

        // Top UI table (XP bar & level label)
        Table topTable = new Table();
        topTable.setFillParent(true);
        topTable.top();
        topTable.padTop(20);
        topTable.add(levelLabel).padRight(15).padBottom(10);
        topTable.add(xpBar).width(800).height(20).padBottom(10);
        stage.addActor(topTable);


        giveUpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Define what happens when Give Up is pressed
                Gdx.app.exit(); // Example: Exit the game
            }
        });

        Table bottomLeftTable = new Table();
        bottomLeftTable.setFillParent(true);
        bottomLeftTable.bottom().left().pad(60);
        bottomLeftTable.add(giveUpButton);
        stage.addActor(bottomLeftTable);
    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getInstance().getBatch().begin();
        Main.getInstance().getBatch().end();
        gameController.updateGame();
        Main.getInstance().getBatch().begin();
        drawTime();
        drawMonster();
        drawTree();
        drawEgg();
        if (gameController.getWorldController().getFence().isActive()) {
            drawFence();
        }
        Main.getInstance().getBatch().end();
        drawLevelBar();
        if (GameController.getInstance().isPaused() && !levelUpPopupShown && !pauseMenuButton) {
            showLevelUpPopup();
            levelUpPopupShown = true;
        }
        stage.act(delta);
        stage.draw();
    }

    private void drawTime() {
        font.draw(
            Main.getInstance().getBatch(),
            String.format("Time: %.0f", gameController.getLastTime()),
            CameraController.getCameraController().getCamera().position.x - CameraController.getCameraController().getCamera().viewportWidth / 2 + 20,
            CameraController.getCameraController().getCamera().position.y + CameraController.getCameraController().getCamera().viewportHeight / 2 - 20
        );
        font.draw(Main.getInstance().getBatch(),
            String.format("Kill : %d",App.getInstance().getCurrentPlayer().getCurrentKills()),
            CameraController.getCameraController().getCamera().position.x - CameraController.getCameraController().getCamera().viewportWidth / 2 + 25,
            CameraController.getCameraController().getCamera().position.y + CameraController.getCameraController().getCamera().viewportHeight / 2 - 60
        );
        font.draw(Main.getInstance().getBatch(),
            String.format("Ammo : %d",App.getInstance().getCurrentPlayer().getWeapon().getAmmo()),
            CameraController.getCameraController().getCamera().position.x - CameraController.getCameraController().getCamera().viewportWidth / 2 + 25,
            CameraController.getCameraController().getCamera().position.y + CameraController.getCameraController().getCamera().viewportHeight / 2 - 100
        );
        font.draw(
            Main.getInstance().getBatch(),
            String.format("Left Time: %.0f", App.getInstance().getGameTime() * 60 - gameController.getLastTime()),
            CameraController.getCameraController().getCamera().position.x + 750,
            CameraController.getCameraController().getCamera().position.y + CameraController.getCameraController().getCamera().viewportHeight / 2 - 20
        );
    }

    private void drawLevelBar() {
        // Update XP bar and label
        User user = App.getInstance().getCurrentUser();
        int level = user.getLevel();
        float xp = user.getXp();
        float xpToNext = user.getLevel() * 20;

        levelLabel.setText("Level: " + level);
        xpBar.setRange(0, xpToNext);
        xpBar.setValue(xp);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void showLevelUpPopup() {
        Skin skin = GameAssetManager.getInstance().getSkin();

        Table table = new Table();
        table.setFillParent(true);
        table.center(); // Center the entire table on screen

        // Title label
        Label title = new Label("Choose an Ability:", skin);
        table.add(title).colspan(3).padBottom(40).row();

        // Create buttons
        AbilityType abilityType1 = AbilityType.Procrease;
        AbilityType abilityType2 = AbilityType.getRandomAbility();
        AbilityType abilityType3 = AbilityType.getRandomAbility();
        TextButton ability1 = new TextButton(abilityType1.getName(), skin);
        TextButton ability2 = new TextButton(abilityType2.getName(), skin);
        TextButton ability3 = new TextButton(abilityType3.getName(), skin);

        // Set size for visual clarity
        float buttonWidth = 500;
        float buttonHeight = 150;

        table.add(ability1).pad(30).width(buttonWidth).height(buttonHeight);
        table.add(ability2).pad(30).width(buttonWidth).height(buttonHeight);
        table.add(ability3).pad(30).width(buttonWidth).height(buttonHeight);

        // Add button listeners
        ability1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkAbility(abilityType1);
                closeLevelUpPopup(table);
            }
        });

        ability2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkAbility(abilityType2);
                closeLevelUpPopup(table);
            }
        });

        ability3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkAbility(abilityType3);
                closeLevelUpPopup(table);
            }
        });

        stage.addActor(table);

    }

    private void closeLevelUpPopup(Actor popupTable) {
        popupTable.remove();
        levelUpPopupShown = false;
        gameController.resumeGame();
    }

    public void drawMonster() {
        ArrayList<Monster> monsters = App.getInstance().getMonsters();

        CameraController camController = CameraController.getCameraController();
        float camX = camController.getCamera().position.x;
        float camY = camController.getCamera().position.y;
        float viewWidth = camController.getCamera().viewportWidth;
        float viewHeight = camController.getCamera().viewportHeight;

        float left = camX - viewWidth / 2 - 500;
        float right = camX + viewWidth / 2 + 500;
        float bottom = camY - viewHeight / 2 - 500;
        float top = camY + viewHeight / 2 + 500;

        for (Monster monster : monsters) {
            if (!monster.isDead()) {
                float x = monster.getPosX();
                float y = monster.getPosY();
                float width = monster.getMonsterType().getWidth();
                float height = monster.getMonsterType().getHeight();

                boolean isVisible = x + width > left && x < right && y + height > bottom && y < top;

                if (isVisible) {
                    monster.draw(Main.getInstance().getBatch());
                }
            }
        }
    }


    public void drawTree(){
        for (Tree tree : App.getInstance().getTrees()) {
                tree.update(Gdx.graphics.getDeltaTime());
                tree.draw(Main.getInstance().getBatch());
        }
    }

    public void drawEgg(){
        for (Egg egg : WorldController.getInstance().getEggs()) {
            egg.render(Main.getInstance().getBatch());
        }
    }
    public void drawFence(){
        WorldController worldController = gameController.getWorldController();
        ShapeRenderer shapeRenderer = worldController.getShapeRenderer();
        shapeRenderer.setProjectionMatrix(CameraController.getCameraController().getCamera().combined);
        worldController.getFence().render(shapeRenderer);
    }

    public void checkAbility(AbilityType ability) {
        switch (ability){
            case Speedy : {
                App.getInstance().getCurrentPlayer().doubleSpeedFor10Seconds();
                break;
            }
            case Vitality:{
                App.getInstance().getCurrentPlayer().addMaxHealth(100);
                break;
            }
            case Amocrease:{
                App.getInstance().getCurrentPlayer().getWeapon().addMaxAmmo();
                break;
            }
            case Procrease:{
                App.getInstance().getCurrentPlayer().getWeapon().addProjectile();
                break;
            }
            case Damager:{
                App.getInstance().getCurrentPlayer().increaseDamageFor10Seconds();
                break;
            }
        }
    }
}
