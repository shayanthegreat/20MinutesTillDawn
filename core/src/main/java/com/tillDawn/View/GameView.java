package com.tillDawn.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDawn.Controller.CameraController;
import com.tillDawn.Controller.GameController;
import com.tillDawn.Controller.WorldController;
import com.tillDawn.Main;
import com.tillDawn.Model.*;

import java.util.ArrayList;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController gameController;

    public GameView(GameController gameController, Skin skin) {
        this.gameController = gameController;
        gameController.setView(this);
    }

    @Override
    public boolean keyDown(int keycode) {
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

        multiplexer.addProcessor(this);   // your GameView InputProcessor
        multiplexer.addProcessor(stage);  // stage

        Gdx.input.setInputProcessor(multiplexer);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getInstance().getBatch().begin();
        Main.getInstance().getBatch().end();
        gameController.updateGame();
        Main.getInstance().getBatch().begin();
        drawMonster();
        drawTree();
        drawEgg();
        if(gameController.getWorldController().getFence().isActive()){
            drawFence();
        }
        Main.getInstance().getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        //System.out.println(App.getInstance().getCurrentPlayer().getPlayerHealth());
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
        ArrayList<Egg> collectedEgg = new ArrayList<>();
        for (Egg egg : WorldController.getInstance().getEggs()) {
            if(egg.isCollected()){
                collectedEgg.add(egg);
                continue;
            }
            egg.render(Main.getInstance().getBatch());
        }
        for (Egg egg : collectedEgg) {
            WorldController.getInstance().getEggs().remove(egg);
        }
    }
    public void drawFence(){
        WorldController worldController = gameController.getWorldController();
        ShapeRenderer shapeRenderer = worldController.getShapeRenderer();
        shapeRenderer.setProjectionMatrix(CameraController.getCameraController().getCamera().combined);
        worldController.getFence().render(shapeRenderer);
    }
}
