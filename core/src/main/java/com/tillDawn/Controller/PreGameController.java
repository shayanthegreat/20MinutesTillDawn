package com.tillDawn.Controller;

import com.tillDawn.Model.App;
import com.tillDawn.Model.CharacterType;
import com.tillDawn.Model.Player;
import com.tillDawn.Model.Weapon;

public class PreGameController extends Controller{
    public void startGame(int x, int y, int z){
        Player player = App.getInstance().getCurrentPlayer();
        switch (x){
            case 0:{
                player.setWeapon(new Weapon("Revolver"));
                break;
            }
            case 1:{
                player.setWeapon(new Weapon("Shotgun"));
                break;
            }
            case 2:{
                player.setWeapon(new Weapon("Smg"));
                break;
            }
            default:{

            }
        }
        switch (y){
            case 0:{
                player.setCharacterType(CharacterType.Dasher);
                break;
            }
            case 1:{
                player.setCharacterType(CharacterType.Diamond);
                break;
            }
            case 2:{
                player.setCharacterType(CharacterType.Lilith);
                break;
            }
            case 3:{
                player.setCharacterType(CharacterType.Scarlet);
                break;
            }
            case 4:{
                player.setCharacterType(CharacterType.Shana);
                break;
            }
            default:{

            }
        }

        switch (z){
            case 0:{
                App.getInstance().setGameTime(2);
                break;
            }
            case 1:{
                App.getInstance().setGameTime(5);
                break;
            }
            case 2:{
                App.getInstance().setGameTime(10);
                break;
            }
            case 3:{
                App.getInstance().setGameTime(20);
                break;
            }
            default:{

            }
        }
    }
}
