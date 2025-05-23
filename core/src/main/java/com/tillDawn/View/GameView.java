package com.tillDawn.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDawn.Controller.GameController;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.KeyBindings;
import com.tillDawn.Model.Monster;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController gameController;

    public GameView(GameController gameController, Skin skin) {
        this.gameController = gameController;
        gameController.setView(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(KeyBindings.ACTION_CLICK == Input.Keys.NUM_LOCK){
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

        if(KeyBindings.ACTION_CLICK != Input.Keys.NUM_LOCK){
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

        gameController.updateGame();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
}
