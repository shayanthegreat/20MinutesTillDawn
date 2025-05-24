package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {

    private Texture playerTexture = new Texture(Gdx.files.internal("player1-.png"));
    private Sprite playerSprite = new Sprite(playerTexture);
    private Weapon weapon = null;
    private CharacterType characterType;
    private float posX = 0;
    private float posY = 0;
    private float playerHealth;
    private float maxHealth;
    private CollisionRect rect;
    private float time = 0;
    private float speed;
    private int currentKills;
    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;
    private boolean isDead = false;
    private User user = null;
    public Player(CharacterType characterType, Weapon weapon){
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() / 7, playerTexture.getHeight() / 7);
        posX = (float) Gdx.graphics.getWidth() / 2;
        posY = (float) Gdx.graphics.getHeight() / 2;
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight(), playerTexture.getWidth() / 7, playerTexture.getHeight() / 7);
        this.characterType = characterType;
        this.playerHealth = characterType.getHealth();
        this.maxHealth = characterType.getHealth();
        this.speed = characterType.getSpeed();
        this.user = App.getInstance().getCurrentUser();
        this.weapon = weapon;
    }

    public void updateHealth(float x){
        playerHealth += x;
        if(playerHealth > maxHealth){
            playerHealth = maxHealth;
        }
        if(playerHealth <= 0){
            isDead = true;
        }
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }


    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getCurrentKills() {
        return currentKills;
    }

    public void setCurrentKills(int currentKills) {
        this.currentKills = currentKills;
    }

    public float getSpeed() {
        return speed;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
