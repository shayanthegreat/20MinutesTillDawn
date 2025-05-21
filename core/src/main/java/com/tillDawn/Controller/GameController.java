package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.Player;
import com.tillDawn.View.GameView;

public class GameController {
    private GameView view;
    private WorldController worldController;
    private PlayerController playerController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    public void setView(GameView view) {
        this.view = view;
        this.playerController = new PlayerController(App.getInstance().getCurrentPlayer());
        this.worldController = WorldController.getInstance(playerController);
        this.weaponController = new WeaponController();
        this.monsterController = new MonsterController();
    }

    public void updateGame(){

        if(view != null){
            Main.getInstance().getBatch().begin();
            worldController.update();
            playerController.update();
            weaponController.update();
            monsterController.update(Gdx.graphics.getDeltaTime());
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
