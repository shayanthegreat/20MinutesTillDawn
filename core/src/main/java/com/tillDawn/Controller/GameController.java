package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.Monster;
import com.tillDawn.Model.Player;
import com.tillDawn.View.GameView;


public class GameController {

    private GameView view;
    private WorldController worldController;
    private PlayerController playerController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private float lastTime;

    public GameController() {
        lastTime = App.getInstance().getCurrentGame().elapsedTime;
        App.getInstance().getCurrentGame().pause = false;
    }
    public Monster getNearestMonster(float maxRange) {
        float playerX = App.getInstance().getCurrentPlayer().getPlayerSprite().getX() + App.getInstance().getCurrentPlayer().getPlayerSprite().getWidth() / 2;
        float playerY = App.getInstance().getCurrentPlayer().getPlayerSprite().getY() + App.getInstance().getCurrentPlayer().getPlayerSprite().getHeight() / 2;

        Monster nearest = null;
        float nearestDistSq = maxRange * maxRange;

        for (Monster monster : App.getInstance().getMonsters()) {
            if(monster.isDead())
                continue;
            float dx = monster.getPosX() - playerX;
            float dy = monster.getPosY() - playerY;
            float distSq = dx * dx + dy * dy;

            if (distSq < nearestDistSq) {
                nearestDistSq = distSq;
                nearest = monster;
            }
        }
        return nearest;
    }
    public void setView(GameView view) {
        this.view = view;
        this.playerController = new PlayerController(App.getInstance().getCurrentPlayer());
        this.worldController = new WorldController(this.playerController);
        this.weaponController = new WeaponController();
        this.monsterController = new MonsterController(weaponController);
         // Make it larger
    }

    public void updateGame(){

        if(view != null){
            App.getInstance().getCurrentGame().elapsedTime += Gdx.graphics.getDeltaTime();
            if(!App.getInstance().isPause())
                lastTime = App.getInstance().getCurrentGame().elapsedTime;
            Main.getInstance().getBatch().begin();
            worldController.update();
            if(!App.getInstance().isPause()){
                playerController.update();
                weaponController.update();
                monsterController.update(Gdx.graphics.getDeltaTime());
            }
            Main.getInstance().getBatch().end();
        }
    }


    public void pauseGame() {
        App.getInstance().pause();
    }

    public void resumeGame() {
        App.getInstance().getCurrentGame().elapsedTime = lastTime;
        App.getInstance().resume();
    }



    public WorldController getWorldController() {
        return worldController;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public boolean isPaused() {
        return App.getInstance().isPause();
    }

    public float getLastTime() {
        return lastTime;
    }
}
