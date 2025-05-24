package com.tillDawn.Controller;

import com.tillDawn.Model.App;
import com.tillDawn.Model.CharacterType;
import com.tillDawn.Model.Player;
import com.tillDawn.Model.Weapon;

public class PreGameController extends Controller{
    public void startGame(int x, int y, int z){
        CharacterType characterType = null;
        Weapon weapon = null;
        switch (x){
            case 0:{
                weapon = new Weapon("Revolver");
                break;
            }
            case 1:{
                weapon = new Weapon("Shotgun");
                break;
            }
            case 2:{
                weapon = new Weapon("Smg");
                break;
            }
            default:{

            }
        }
        switch (y){
            case 0:{
                characterType = CharacterType.Dasher;
                break;
            }
            case 1:{
                characterType = CharacterType.Diamond;
                break;
            }
            case 2:{
                characterType = CharacterType.Lilith;
                break;
            }
            case 3:{
                characterType = CharacterType.Scarlet;
                break;
            }
            case 4:{
                characterType = CharacterType.Shana;
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
        App.getInstance().setAutoAim(false);
        App.getInstance().setCurrentPlayer(new Player(characterType, weapon));
        App.getInstance().getCurrentPlayer().setUser(App.getInstance().getCurrentUser());
    }
}
