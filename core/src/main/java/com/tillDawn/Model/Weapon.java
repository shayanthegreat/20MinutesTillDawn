package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon {
    private final Texture texture;
    private final Sprite sprite;
    private int ammo = 30;

    public Weapon(String name) {
        switch (name) {
            case "Revolver":
                texture = new Texture(Gdx.files.internal("gun/revolver.png"));
                break;
            case "Smg":{
                texture = new Texture(Gdx.files.internal("gun/smg.png"));
                break;
            }
            default:
                texture = new Texture(Gdx.files.internal("gun/default.png"));
                break;
        }

        sprite = new Sprite(texture);
        sprite.setX((float) Gdx.graphics.getWidth() / 2);
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50, 50);
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
}
