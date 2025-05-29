package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class Weapon {
    private String name;
    @JsonIgnore
    private Texture texture;
    @JsonIgnore
    private Sprite sprite;

    private String textureFilePath;     // store texture file path for reload

    private int ammo;
    private int maxAmmo;
    private boolean isReloading;
    private float reloadTime; // in seconds
    private float reloadTimer;
    private int ammoDamage;
    private int projectile;

    // No-arg constructor needed for serialization
    public Weapon() {}

    public Weapon(String name) {
        this.name = name;
        initWeaponByName(name);
    }

    private void initWeaponByName(String name) {
        switch (name) {
            case "Revolver":
                textureFilePath = "gun/revolver.png";
                maxAmmo = 6;
                reloadTime = 1;
                ammoDamage = 20;
                projectile = 1;
                break;
            case "Smg":
                textureFilePath = "gun/smg.png";
                maxAmmo = 24;
                reloadTime = 2;
                ammoDamage = 8;
                projectile = 1;
                break;
            case "Shotgun":
                textureFilePath = "gun/shotgun.png";
                maxAmmo = 8;
                reloadTime = 1;
                ammoDamage = 10;
                projectile = 4;
                break;
//            default:
//                textureFilePath = "gun/default.png";
//                maxAmmo = 0;
//                reloadTime = 0;
//                ammoDamage = 0;
//                projectile = 0;
//                break;
        }
        ammo = maxAmmo;
        isReloading = false;
        reloadTimer = 0f;
        loadTextureAndSprite();
    }

    // Call this after loading from JSON to restore texture and sprite
    public void loadTextureAndSprite() {
        if (textureFilePath != null && !textureFilePath.isEmpty()) {
            texture = new Texture(Gdx.files.internal(textureFilePath));
            sprite = new Sprite(texture);
            sprite.setX((float) Gdx.graphics.getWidth() / 2);
            sprite.setY((float) Gdx.graphics.getHeight() / 2);
            sprite.setSize(50, 50);
            reloadTimer = 0f;
        }
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

    public void setReloading(boolean reloading) {
        this.isReloading = reloading;
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

    public int getProjectile() {
        return projectile;
    }

    public void addProjectile() {
        this.projectile++;
    }

    public int getAmmoDamage() {
        return ammoDamage;
    }

    public void addMaxAmmo() {
        this.maxAmmo += 5;
    }

    public String getTextureFilePath() {
        return textureFilePath;
    }

    public void setTextureFilePath(String textureFilePath) {
        this.textureFilePath = textureFilePath;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }
}
