package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon {
    private final String name;
    private final Texture texture;
    private final Sprite sprite;
    private int ammo;
    private int maxAmmo;
    private boolean isReloading;
    private float reloadTime; // in seconds
    private float reloadTimer;
    private int ammoDamage;
    public Weapon(String name) {
        switch (name) {
            case "Revolver":
                this.name = "Revolver";
                texture = new Texture(Gdx.files.internal("gun/revolver.png"));
                maxAmmo = 6;
                reloadTime = 1;
                ammoDamage = 20;
                break;
            case "Smg":{
                this.name = "Smg";
                texture = new Texture(Gdx.files.internal("gun/smg.png"));
                maxAmmo = 24;
                reloadTime = 2;
                ammoDamage = 8;
                break;
            }
            case "Shotgun":{
                this.name = "Shotgun";
                texture = new Texture(Gdx.files.internal("gun/shotgun.png"));
                maxAmmo = 8;
                reloadTime = 1;
                ammoDamage = 10;
                break;
            }
            default: {
                this.name = "Unknown";
                texture = new Texture(Gdx.files.internal("gun/default.png"));
            }
        }
        this.ammo = maxAmmo;
        this.isReloading = false;
        this.reloadTimer = 0f;

        sprite = new Sprite(texture);
        sprite.setX((float) Gdx.graphics.getWidth() / 2);
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50, 50);
    }

    public void reload() {
        if (!isReloading && ammo < maxAmmo) {
            isReloading = true;
            reloadTimer = 0;
        }
    }

    public void update(float deltaTime) {
        if (isReloading) {
            reloadTimer += deltaTime;
            if (reloadTimer >= reloadTime) {
                ammo = maxAmmo;
                isReloading = false;
            }
        }
    }
    public Sprite getSprite() {
        return sprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public float getReloadTimer() {
        return reloadTimer;
    }

    public String getName() {
        return name;
    }

    public int getAmmoDamage() {
        return ammoDamage;
    }
}
