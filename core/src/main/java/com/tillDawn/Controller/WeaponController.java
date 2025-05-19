package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.Bullet;
import com.tillDawn.Model.Weapon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class WeaponController {
//    private  weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    public void handleWeaponRotation(int x, int y) {
        Weapon weapon = App.getInstance().getCurrentPlayer().getWeapon();
        Sprite weaponSprite = weapon.getSprite();

        // Center of the screen
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        // Position the sprite so its center is exactly at screen center
        weaponSprite.setPosition(centerX, centerY);

        // Set origin to center for proper rotation
        weaponSprite.setOriginCenter();

        // Calculate angle in degrees between center and mouse position
        float angle = (float) Math.atan2(y - centerY, x - centerX);

        weaponSprite.setRotation((float) (3 - angle * MathUtils.radiansToDegrees));
    }


    public void handleWeaponShoot(int targetX, int targetY) {
        Weapon weapon = App.getInstance().getCurrentPlayer().getWeapon();

        float startX = Gdx.graphics.getWidth() / 2f;
        float startY = Gdx.graphics.getHeight() / 2f;

        float flippedY = Gdx.graphics.getHeight() - targetY;

        Vector2 direction = new Vector2(targetX - startX, flippedY - startY).nor();
        Bullet newBullet = new Bullet(10, startX, startY, direction);
        newBullet.getSprite().setOriginCenter();
        newBullet.getSprite().setRotation(weapon.getSprite().getRotation());
        bullets.add(newBullet);
        weapon.setAmmo(weapon.getAmmo() - 1);
    }


    public void update() {
        Weapon weapon = App.getInstance().getCurrentPlayer().getWeapon();
        weapon.getSprite().draw(Main.getInstance().getBatch());

        for (Bullet bullet : bullets) {
            bullet.update();
            bullet.draw(Main.getInstance().getBatch());
        }
    }

}
