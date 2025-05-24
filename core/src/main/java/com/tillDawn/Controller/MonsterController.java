package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tillDawn.Main;
import com.tillDawn.Model.*;

import java.util.ArrayList;

public class MonsterController {
    private boolean isHalf = false;
    float gameTime = 0f;
    float spawnTimer = 0f;
    float spawnTimer2 = 0f;
    ArrayList<Monster> monsters = App.getInstance().getMonsters();
    private WeaponController weaponController;
    public MonsterController(WeaponController weaponController) {
        this.weaponController = weaponController;
    }
    private void spawnMonster(MonsterType type) {
        float x = MathUtils.random(-800, 800); // Choose spawn area as needed
        float y = MathUtils.random(-600, 600);
        CollisionRect rect = new CollisionRect(x, y, 40, 40); // Example values
        Monster monster = new Monster(x, y, rect, 0, type);
        monsters.add(monster);
    }

    public void update(float deltaTime) {
        gameTime += deltaTime;
        spawnTimer += deltaTime;
        spawnTimer2 += deltaTime;
        float j = gameTime;
        if (spawnTimer >= 3f) {
            int monstersToSpawn = (int) j / (30);

            for (int i = 0; i < monstersToSpawn; i++) {
                spawnMonster(MonsterType.Tentacle);
            }

            spawnTimer -= 3f;
        }
        if(spawnTimer2 >= 10f){
            int totalTime = App.getInstance().getGameTime() * 60;
            int monstersToSpawn = (4*(int)j- totalTime + 30)/30;
            for (int i = 0; i < monstersToSpawn; i++) {
                spawnMonster(MonsterType.EyeBat);
            }
            spawnTimer2 -= 10f;
        }
        if(gameTime >= App.getInstance().getGameTime() / 2f && !isHalf) {
            spawnMonster(MonsterType.Shub);
            isHalf = true;
        }
        Player player = App.getInstance().getCurrentPlayer();
        CollisionRect playerRect = player.getRect();
        // Update all monsters
        for (Monster monster : monsters) {
            CollisionRect monsterRect = monster.getRect();
            if (!monsterRect.collidesWith(playerRect)) {
                monster.update(deltaTime, player.getPosX(), player.getPosY()); // Move toward player
            } else {
                monster.damage(player);
            }
            if (monster.getMonsterType() == MonsterType.EyeBat) {
                monster.updateShootTimer(deltaTime);
                if (monster.canShoot()) {
                    shootBulletAtPlayer(monster, player);
                    monster.resetShootTimer();
                }
            }
        }

        ArrayList<Monster> deadMonsters = new ArrayList<>();
        for(Monster monster : monsters) {
            if(monster.isDead()) {
                deadMonsters.add(monster);
            }
        }
        for(Monster monster : deadMonsters) {
            monsters.remove(monster);
        }
        player.setCurrentKills(deadMonsters.size() + player.getCurrentKills());
    }

    private void shootBulletAtPlayer(Monster monster, Player player) {
        float startX = monster.getPosX();
        float startY = monster.getPosY();
        Vector2 direction = new Vector2(player.getPosX() - startX, player.getPosY() - startY).nor();

        Bullet bullet = new Bullet(5, startX, startY, direction); // You can adjust damage
        weaponController.addEnemyBullet(bullet);
    }
}
