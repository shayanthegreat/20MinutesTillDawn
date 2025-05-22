package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.tillDawn.Main;
import com.tillDawn.Model.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class WeaponController {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    public void handleWeaponRotation(int x, int y) {
        Weapon weapon = App.getInstance().getCurrentPlayer().getWeapon();
        Sprite weaponSprite = weapon.getSprite();

        Sprite playerSprite = App.getInstance().getCurrentPlayer().getPlayerSprite();
        float playerX = playerSprite.getX() + playerSprite.getWidth() / 2;
        float playerY = playerSprite.getY() + playerSprite.getHeight() / 2;

        weaponSprite.setPosition(playerX - weaponSprite.getWidth() / 2, playerY - weaponSprite.getHeight() / 2);


        weaponSprite.setOriginCenter();

        Vector3 mouseWorld = CameraController.getCameraController().getCamera()
            .unproject(new Vector3(x, y, 0));

        float angle = MathUtils.atan2(mouseWorld.y - playerY, mouseWorld.x - playerX);
        weaponSprite.setRotation(angle * MathUtils.radiansToDegrees);


        weaponSprite.setRotation((float) (angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int targetX, int targetY) {
        Weapon weapon = App.getInstance().getCurrentPlayer().getWeapon();
        if (weapon.isReloading() || weapon.getAmmo() <= 0) return;

        Sprite playerSprite = App.getInstance().getCurrentPlayer().getPlayerSprite();
        float startX = playerSprite.getX() + playerSprite.getWidth() / 2;
        float startY = playerSprite.getY() + playerSprite.getHeight() / 2;

        // 🛠 Convert screen coordinates to world coordinates using the camera
        Vector3 worldMouse = new Vector3(targetX, targetY, 0);
        CameraController.getCameraController().getCamera().unproject(worldMouse);

        // 🔁 Compute direction from player to mouse in world coordinates
        Vector2 baseDirection = new Vector2(worldMouse.x - startX, worldMouse.y - startY).nor();

        if (weapon.getName().equalsIgnoreCase("Shotgun")) {
            int pelletCount = 4;
            int ammoToUse = Math.min(pelletCount, weapon.getAmmo());

            for (int i = 0; i < ammoToUse; i++) {
                float angleOffset = -12f + (i * 8f);
                Vector2 spreadDir = baseDirection.cpy().rotateDeg(angleOffset).nor();

                Bullet pellet = new Bullet(weapon.getAmmoDamage(), startX, startY, spreadDir);
                pellet.getSprite().setOriginCenter();
                pellet.getSprite().setRotation(spreadDir.angleDeg());
                bullets.add(pellet);
            }

            weapon.setAmmo(weapon.getAmmo() - ammoToUse);
        } else {
            Bullet newBullet = new Bullet(weapon.getAmmoDamage(), startX, startY, baseDirection);
            newBullet.getSprite().setOriginCenter();
            newBullet.getSprite().setRotation(baseDirection.angleDeg());
            bullets.add(newBullet);
            weapon.setAmmo(weapon.getAmmo() - 1);
        }
    }



    public void update() {
        Weapon weapon = App.getInstance().getCurrentPlayer().getWeapon();
        Sprite sprite = weapon.getSprite();
        handlePlayerInput();

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (sprite.getScaleY() > 0) sprite.setScale(1, -1); // flip horizontally
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (sprite.getScaleY() < 0) sprite.setScale(1, 1); // unflip
        }
        sprite.setPosition(App.getInstance().getCurrentPlayer().getPosX(), App.getInstance().getCurrentPlayer().getPosY());
        weapon.update(Gdx.graphics.getDeltaTime());

        sprite.draw(Main.getInstance().getBatch());
        ArrayList<Monster> monsters = App.getInstance().getMonsters();
        for (Bullet bullet : bullets) {
            bullet.update();
            CollisionRect bulletRect = bullet.getRect();
            for (Monster monster : monsters) {
                CollisionRect monsterRect = monster.getRect();
                if(bulletRect.collidesWith(monsterRect)){
                    bullet.monsterHit(monster);
                }
            }
            bullet.draw(Main.getInstance().getBatch());
        }
        Main.getInstance().getBatch().end();
        if (weapon.isReloading()) {
            float reloadPercent = weapon.getReloadTimer() / weapon.getReloadTime();

            Sprite playerSprite = App.getInstance().getCurrentPlayer().getPlayerSprite();
            float playerX = playerSprite.getX() + playerSprite.getWidth() / 2;
            float playerY = playerSprite.getY() + playerSprite.getHeight();

            float barWidth = 40;
            float barHeight = 6;
            float x = playerX - barWidth / 2;
            float y = playerY + 10;

            // ✅ Set camera projection matrix
            shapeRenderer.setProjectionMatrix(CameraController.getCameraController().getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rect(x, y, barWidth, barHeight);

            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(x, y, barWidth * reloadPercent, barHeight);

            shapeRenderer.end();
        }
        Main.getInstance().getBatch().begin();
    }

    public void handlePlayerInput(){
        Weapon weapon = App.getInstance().getCurrentPlayer().getWeapon();
        if(Gdx.input.isKeyPressed(Input.Keys.R)){
            weapon.reload();
            return;
        }
        if(weapon.getAmmo() <= 0 && App.getInstance().isAutoReload()){
            weapon.reload();
            return;
        }
    }
}
