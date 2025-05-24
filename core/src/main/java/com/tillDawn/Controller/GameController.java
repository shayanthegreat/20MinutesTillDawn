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
    private float elapsedTime = 0f;
    private BitmapFont font = new BitmapFont();
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
        this.worldController = WorldController.getInstance(playerController);
        this.weaponController = new WeaponController();
        this.monsterController = new MonsterController(weaponController);
        font.setColor(Color.WHITE);
        font.getData().setScale(2); // Make it larger
    }

    public void updateGame(){

        if(view != null){
            elapsedTime += Gdx.graphics.getDeltaTime();
            Main.getInstance().getBatch().begin();
            worldController.update();
            playerController.update();
            weaponController.update();
            monsterController.update(Gdx.graphics.getDeltaTime());
            font.draw(
                Main.getInstance().getBatch(),
                String.format("Time: %.0f", elapsedTime),
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
                String.format("Left Time: %.0f", App.getInstance().getGameTime() * 60 - elapsedTime),
                CameraController.getCameraController().getCamera().position.x + 750,
                CameraController.getCameraController().getCamera().position.y + CameraController.getCameraController().getCamera().viewportHeight / 2 - 20
            );
            Main.getInstance().getBatch().end();
        }
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
}
