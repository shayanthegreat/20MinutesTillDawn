package com.tillDawn.Controller;

import com.tillDawn.Model.App;
import com.tillDawn.Model.Player;
import com.tillDawn.View.GameView;

public class GameController {
    private GameView view;
    private WorldController worldController;
    private PlayerController playerController;
    private WeaponController weaponController;
    public void setView(GameView view) {
        this.view = view;
        this.playerController = new PlayerController(App.getInstance().getCurrentPlayer());
        this.worldController = new WorldController(playerController);
        this.weaponController = new WeaponController();
    }

    public void updateGame(){
        if(view != null){
            worldController.update();
            playerController.update();
            weaponController.update();
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
