package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Player {

    @JsonIgnore
    private Texture playerTexture;
    @JsonIgnore
    private Sprite playerSprite;
    private String playerTextureFileName = "player1-.png";
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
    private boolean speedBoosted = false;
    private float boostTimeLeft = 0;
    private boolean damageBoosted = false;
    private float damageBoostedLeft = 0;
    private float originalSpeed;
    private ArrayList<AbilityType> abilities = new ArrayList<>();
    public Player() {
        loadTextures();
    }

    public void loadTextures() {
        if (playerTexture != null) playerTexture.dispose();
        playerTexture = new Texture(Gdx.files.internal(playerTextureFileName));
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(60, 80);
    }
    public Player(CharacterType characterType, Weapon weapon){
        loadTextures();
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(60, 80);
        posX = (float) Gdx.graphics.getWidth() / 2;
        posY = (float) Gdx.graphics.getHeight() / 2;
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight(), 60, 80);
        this.characterType = characterType;
        this.playerHealth = characterType.getHealth();
        this.maxHealth = characterType.getHealth();
        this.speed = characterType.getSpeed();
        this.weapon = weapon;
    }

    public void updateHealth(float x){
        x *= Gdx.graphics.getDeltaTime();
        playerHealth += x;
        if(playerHealth > maxHealth){
            playerHealth = maxHealth;
        }
        if(playerHealth <= 0){
            playerHealth = 0;
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
    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    public void doubleSpeedFor10Seconds() {
        if (!speedBoosted) {
            originalSpeed = speed;
            speed *= 2;
            boostTimeLeft = 10f;
            speedBoosted = true;
        }
    }

    public void update(float deltaTime) {
        updateBoost(deltaTime);
        increaseDamageUpdate(deltaTime);
    }
    public void updateBoost(float deltaTime) {
        if (speedBoosted) {
            boostTimeLeft -= deltaTime;
            if (boostTimeLeft <= 0) {
                speed = originalSpeed;
                speedBoosted = false;
                boostTimeLeft = 0;
            }
        }
    }

    public void addMaxHealth(float maxHealth) {
        this.maxHealth += maxHealth;
    }

    public void increaseDamageFor10Seconds() {
        if (!damageBoosted) {
            damageBoostedLeft = 10f;
            damageBoosted = true;
        }
    }

    public void increaseDamageUpdate(float deltaTime) {
        if (damageBoosted) {
            damageBoostedLeft -= deltaTime;
            if (damageBoostedLeft <= 0) {
                damageBoosted = false;
                damageBoostedLeft = 0;
            }
        }
    }

    public boolean isDamageBoosted() {
        return damageBoosted;
    }

    public void addAbility(AbilityType abilityType){
        if(!abilities.contains(abilityType)){
            abilities.add(abilityType);
        }
    }

    public ArrayList<AbilityType> getAbilities() {
        return abilities;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    @JsonProperty("dead")
    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
