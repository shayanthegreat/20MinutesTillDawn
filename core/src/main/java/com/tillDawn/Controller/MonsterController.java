package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tillDawn.Main;
import com.tillDawn.Model.*;

import java.util.ArrayList;

public class MonsterController {
    float spawnTimer = 0f;
    float spawnTimer2 = 0f;
    private WeaponController weaponController;
    public MonsterController(WeaponController weaponController) {
        this.weaponController = weaponController;
    }
    private void spawnMonster(MonsterType type) {
        Player player = App.getInstance().getCurrentPlayer();
        float x = MathUtils.random(-1500+player.getPosX(), 1500+player.getPosX()); // Choose spawn area as needed
        float y = MathUtils.random(-1500+player.getPosY(), 1500+player.getPosY());
        if(type == MonsterType.Shub){
            x = MathUtils.random(-800+player.getPosX(), 800+player.getPosX()); // Choose spawn area as needed
            y = MathUtils.random(-1200+player.getPosY(), 1200+player.getPosY());
        }
        Monster monster = new Monster(x, y, 0, type);
        App.getInstance().getMonsters().add(monster);
    }

    public void update(float deltaTime) {
        spawnTimer += deltaTime;
        spawnTimer2 += deltaTime;
        float j = App.getInstance().getCurrentGame().elapsedTime;
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
        if(App.getInstance().getCurrentGame().elapsedTime >= App.getInstance().getGameTime() * 30 && !App.getInstance().getCurrentGame().bigBoss) {
            App.getInstance().getCurrentGame().bigBoss = true;
            spawnMonster(MonsterType.Shub);
        }
        Player player = App.getInstance().getCurrentPlayer();
        CollisionRect playerRect = player.getRect();
        for (Monster monster : App.getInstance().getMonsters()) {
            CollisionRect monsterRect = monster.getRect();
            if (monster.getMonsterType() == MonsterType.Shub) {
                monster.update(deltaTime, player.getPosX(), player.getPosY());
                if (monster.getRect().collidesWith(playerRect)) {
                    monster.damage(player);
                }
            }
            else{
                if (!monsterRect.collidesWith(playerRect)) {
                    monster.update(deltaTime, player.getPosX(), player.getPosY()); // Move toward player
                } else {
                    monster.damage(player);
                }
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
        for(Monster monster : App.getInstance().getMonsters()) {
            if(monster.isDead() && monster.isFinishedDeathAnimation()) {
                deadMonsters.add(monster);
            }
        }
        for(Monster monster : deadMonsters) {
            if(monster.getMonsterType() == MonsterType.Shub)
                App.getInstance().getCurrentGame().fence.deactivate();
            App.getInstance().getMonsters().remove(monster);
        }
        player.setCurrentKills(deadMonsters.size() + player.getCurrentKills());
    }

    private void shootBulletAtPlayer(Monster monster, Player player) {
        float startX = monster.getPosX();
        float startY = monster.getPosY();
        Vector2 direction = new Vector2(player.getPosX() - startX, player.getPosY() - startY).nor();

        Bullet bullet = new Bullet(5, startX, startY, direction);
        weaponController.addEnemyBullet(bullet);
    }
}
