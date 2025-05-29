package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tillDawn.Controller.WorldController;

public class Monster {
    private float posX;
    private float posY;
    private float health;
    private boolean dead = false;
    private CollisionRect rect;
    private float time = 0;
    private float speed;
    private float damage;
    private float stateTime = 0f;
    private float shootTimer = 0f;
    private MonsterType monsterType;

    // Animations
    @JsonIgnore
    private Animation<TextureRegion> animation;
    @JsonIgnore
    private Animation<TextureRegion> normalAnimation;
    @JsonIgnore
    private Animation<TextureRegion> dashAnimation;

    // Death animation fields
    @JsonIgnore
    private Animation<TextureRegion> deathAnimation;
    @JsonIgnore
    private float deathAnimationTime = 0f;
    @JsonIgnore
    private boolean playingDeathAnimation = false;
    @JsonIgnore
    private boolean finishedDeathAnimation = false;
    @JsonIgnore
    private float deathAnimationDuration = 1f; // Adjust as needed

    private float dashCooldown = 5f;
    private float dashTimer = 0f;
    private boolean isDashing = false;
    private float dashDuration = 0.5f;
    private float dashTimeLeft = 0f;
    private float dashDirX = 0;
    private float dashDirY = 0;

    // Serializable asset paths for Shub animations
    private String[] normalAnimationFrames;
    private String[] dashAnimationFrames;

    // Add death animation frames (example)
    private String[] deathAnimationFrames;

    public Monster() {}

    public Monster(float posX, float posY, float time, MonsterType monsterType) {
        this.posX = posX;
        this.posY = posY;
        this.health = monsterType.getHp();
        this.rect = new CollisionRect(posX, posY, monsterType.getWidth(), monsterType.getHeight());
        this.time = time;
        this.speed = 15;
        this.damage = 10;
        this.monsterType = monsterType;

        if (monsterType == MonsterType.Shub) {
            this.speed = 20;
            ensureShubAnimationFrames();
        }

        ensureDeathAnimationFrames();
        loadAssets();
    }

    private void ensureShubAnimationFrames() {
        if (normalAnimationFrames == null || dashAnimationFrames == null) {
            normalAnimationFrames = new String[] {
                "shub/T_ShubNiggurath_0.png",
                "shub/T_ShubNiggurath_1.png",
                "shub/T_ShubNiggurath_2.png",
                "shub/T_ShubNiggurath_3.png",
                "shub/T_ShubNiggurath_4.png"
            };
            dashAnimationFrames = new String[] {
                "shub/T_ShubNiggurath_5.png",
                "shub/T_ShubNiggurath_6.png",
                "shub/T_ShubNiggurath_7.png",
                "shub/T_ShubNiggurath_8.png",
                "shub/T_ShubNiggurath_9.png",
                "shub/T_ShubNiggurath_10.png"
            };
        }
    }

    private void ensureDeathAnimationFrames() {
        if (deathAnimationFrames == null) {
            // You must provide death animation frames here:
            deathAnimationFrames = new String[] {
                "damage/FireballExplosion_0.png",
                "damage/FireballExplosion_1.png",
                "damage/FireballExplosion_2.png",
                "damage/FireballExplosion_3.png",
                "damage/FireballExplosion_4.png",
                "damage/FireballExplosion_5.png",
            };
        }
    }

    public void loadAssets() {
        ensureDeathAnimationFrames();
        if (monsterType != null) {
            animation = monsterType.getAnimation();

            if (monsterType == MonsterType.Shub) {
                ensureShubAnimationFrames();
                normalAnimation = createAnimation(normalAnimationFrames, 1f);
                dashAnimation = createAnimation(dashAnimationFrames, 0.3f);
            }

            // Load death animation
            deathAnimation = createAnimation(deathAnimationFrames, deathAnimationDuration / deathAnimationFrames.length);
        }
    }

    private Animation<TextureRegion> createAnimation(String[] framePaths, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[framePaths.length];
        for (int i = 0; i < framePaths.length; i++) {
            Texture tex = new Texture(Gdx.files.internal(framePaths[i]));
            frames[i] = new TextureRegion(tex);
        }
        return new Animation<>(frameDuration, frames);
    }

    public void knockback(Vector2 direction, float force) {
        Vector2 knock = new Vector2(direction).nor().scl(force);
        this.posX += knock.x;
        this.posY += knock.y;

        if (rect != null) {
            rect.move(posX, posY);
        }
    }

    public void update(float deltaTime, float playerX, float playerY) {
        // If playing death animation, advance it and stop other updates
        if (playingDeathAnimation) {
            deathAnimationTime += deltaTime;
            if (deathAnimation.isAnimationFinished(deathAnimationTime)) {
                playingDeathAnimation = false;
                finishedDeathAnimation = true;
            }
            return;
        }

        if (dead || finishedDeathAnimation) return;

        if (monsterType == MonsterType.Shub) {
            dashTimer += deltaTime;

            if (!isDashing && dashTimer >= dashCooldown) {
                isDashing = true;
                dashTimeLeft = dashDuration;
                dashTimer = 0f;

                dashDirX = playerX - posX;
                dashDirY = playerY - posY;
                float length = (float) Math.sqrt(dashDirX * dashDirX + dashDirY * dashDirY);
                if (length != 0) {
                    dashDirX /= length;
                    dashDirY /= length;
                }
            }

            if (isDashing) {
                dashTimeLeft -= deltaTime;

                posX += dashDirX * speed * 50f * deltaTime;
                posY += dashDirY * speed * 50f * deltaTime;

                if (rect != null) {
                    rect.move(posX, posY);
                }

                if (dashTimeLeft <= 0f) {
                    isDashing = false;
                }

                return;
            }
        }

        float dirX = playerX - posX;
        float dirY = playerY - posY;
        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY);

        if (length != 0) {
            dirX /= length;
            dirY /= length;

            posX += dirX * speed * deltaTime;
            posY += dirY * speed * deltaTime;

            if (rect != null) {
                rect.move(posX, posY);
            }
        }
    }

    public void damage(Player player) {
        player.updateHealth(-damage);
    }

    public void updateHealth(float x) {
        this.health -= x;
        if (this.health <= 0 && !this.dead && !this.playingDeathAnimation) {
            if (monsterType == MonsterType.EyeBat) {
                WorldController.getInstance().addEgg(new Egg("eyeBat/T_EyeBat_EM.png", posX, posY));
            } else if (monsterType == MonsterType.Tentacle) {
                WorldController.getInstance().addEgg(new Egg("tentacle/BrainMonster_Em.png", posX, posY));
            }
            this.playingDeathAnimation = true;
            this.deathAnimationTime = 0f;
            this.dead = true;
        }
    }

    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        if (playingDeathAnimation && deathAnimation != null) {
            TextureRegion frame = deathAnimation.getKeyFrame(deathAnimationTime, false);
            batch.draw(frame, posX, posY, monsterType.getWidth(), monsterType.getHeight());
            return;
        }

        if (finishedDeathAnimation) return;

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        if (monsterType == MonsterType.Shub) {
            if (isDashing) {
                currentFrame = dashAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = normalAnimation.getKeyFrame(stateTime, true);
            }
        }

        if (monsterType == MonsterType.Shub && isDashing) {
            batch.setColor(1f, 0.3f, 0.3f, 1f); // Red tint
        }

        batch.draw(currentFrame, posX, posY, monsterType.getWidth(), monsterType.getHeight());

        if (monsterType == MonsterType.Shub && isDashing) {
            batch.setColor(1f, 1f, 1f, 1f); // Reset color
        }
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isFinishedDeathAnimation() {
        return finishedDeathAnimation;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void updateShootTimer(float delta) {
        shootTimer += delta;
    }

    public void resetShootTimer() {
        shootTimer = 0f;
    }

    public boolean canShoot() {
        return shootTimer >= 3f;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
