package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.tillDawn.Main;
import com.tillDawn.Model.*;

import java.util.ArrayList;

public class MonsterController {
    float gameTime = 0f;
    float spawnTimer = 0f;
    float spawnTimer2 = 0f;

    ArrayList<Monster> monsters = App.getInstance().getMonsters();// Time since last spawn
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
             // Total seconds since game started
            int monstersToSpawn = (int) j / (30);

            for (int i = 0; i < monstersToSpawn; i++) {
                spawnMonster(MonsterType.Tentacle);
            }

            spawnTimer -= 3f; // Keep leftover time
        }
        if(spawnTimer2 >= 10f){
            int totalTime = App.getInstance().getGameTime() * 60;
            int monstersToSpawn = (4*(int)j- totalTime + 30)/30;
            for (int i = 0; i < monstersToSpawn; i++) {
                spawnMonster(MonsterType.EyeBat);
            }
            spawnTimer2 -= 10f;
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
        }

        for (Monster monster : monsters) {
            if (!monster.isDead()) {
                monster.draw(Main.getInstance().getBatch());
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
}
