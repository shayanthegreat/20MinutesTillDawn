package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.CollisionRect;
import com.tillDawn.Model.Monster;
import com.tillDawn.Model.Player;

import java.util.ArrayList;

public class MonsterController {
    float gameTime = 0f;            // Time since game started
    float spawnTimer = 0f;
    ArrayList<Monster> monsters = App.getInstance().getMonsters();// Time since last spawn
    private void spawnMonster() {
        float x = MathUtils.random(0, 800); // Choose spawn area as needed
        float y = MathUtils.random(0, 600);
        CollisionRect rect = new CollisionRect(x, y, 40, 40); // Example values
        Monster monster = new Monster(x, y, 100, rect, 0, 30, 5);
        monsters.add(monster);
    }

    public void update(float deltaTime) {
        gameTime += deltaTime;
        spawnTimer += deltaTime;

        if (spawnTimer >= 3f) {
            float j = gameTime; // Total seconds since game started
            int monstersToSpawn = (int) j / (30);

            for (int i = 0; i < monstersToSpawn; i++) {
                spawnMonster();
            }

            spawnTimer -= 3f; // Keep leftover time
        }
        Player player = App.getInstance().getCurrentPlayer();
        CollisionRect playerRect = player.getRect();
        // Update all monsters
        for (Monster monster : monsters) {
            CollisionRect monsterRect = monster.getRect();
            if (!monsterRect.collidesWith(new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2, playerRect.getWidth(), playerRect.getHeight()))) {
                monster.update(deltaTime, (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2); // Move toward player
            } else {
                monster.damage(player);
            }

        }

        for(Monster monster : monsters) {
            if(monster.isDead())
                continue;
            monster.getSprite().draw(Main.getInstance().getBatch());
        }
    }

}
