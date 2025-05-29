package com.tillDawn.Model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Fence {
    @JsonIgnore
    private Rectangle bounds;

    private float shrinkRate = 10f;
    private float damagePerSecond = 20f;

    @JsonIgnore
    private boolean active = false;
    @JsonIgnore
    private boolean wasActive = false;

    public Fence() {}

    public Fence(float startWidth, float startHeight, float centerX, float centerY) {
        bounds = new Rectangle(
            centerX - startWidth / 2f,
            centerY - startHeight / 2f,
            startWidth,
            startHeight
        );
    }

    public void update(float delta) {

        App.getInstance().getCurrentGame().fenceData = getData();
        if (!active) return;
        float shrink = shrinkRate * delta;
        bounds.x += shrink / 2f;
        bounds.y += shrink / 2f;
        bounds.width -= shrink;
        bounds.height -= shrink;

        if (bounds.width <= 100 || bounds.height <= 100) {
            App.getInstance().getCurrentGame().player.setDead(true);
        }
    }

    public void deactivate() {
        active = false;
    }

    public void activate() {
        active = true;
        wasActive = true;
    }

    public void render(ShapeRenderer renderer) {
        if (!active) return;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(1, 0, 0, 1);
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        renderer.end();
    }

    public void checkCollision(Player player, float delta) {
        if (!active) return;

        float playerX = player.getRect().getX();
        float playerY = player.getRect().getY();

        if (!bounds.contains(playerX, playerY)) {
            player.updateHealth(-damagePerSecond);
        }

        float clampedX = Math.max(bounds.x, Math.min(playerX, bounds.x + bounds.width - player.getRect().getWidth()));
        float clampedY = Math.max(bounds.y, Math.min(playerY, bounds.y + bounds.height - player.getRect().getHeight()));

        if (clampedX != playerX || clampedY != playerY) {
            player.setPosition(clampedX, clampedY);
            player.getRect().move(clampedX, clampedY);
        }
    }

    public FenceData getData() {
        FenceData data = new FenceData();
        data.x = bounds.x;
        data.y = bounds.y;
        data.width = bounds.width;
        data.height = bounds.height;
        data.active = active;
        data.wasActive = wasActive;
        return data;
    }

    public void loadFromData(FenceData data) {
        this.bounds = new Rectangle(data.x, data.y, data.width, data.height);
        this.active = data.active;
        this.wasActive = data.wasActive;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isWasActive() {
        return wasActive;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setWasActive(boolean wasActive) {
        this.wasActive = wasActive;
    }
}
