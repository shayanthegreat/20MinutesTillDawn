package com.tillDawn.Model;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
public class Fence {
    private Rectangle bounds;
    private float shrinkRate = 20f; // pixels per second
    private boolean active = false;
    private float damagePerSecond = 20f;
    private boolean wasActive = false;
    public Fence(float startWidth, float startHeight, float centerX, float centerY) {
        bounds = new Rectangle(
            centerX - startWidth / 2f,
            centerY - startHeight / 2f,
            startWidth,
            startHeight
        );
    }

    public void update(float delta) {
        if (!active) return;

        float shrink = shrinkRate * delta;
        bounds.x += shrink / 2f;
        bounds.y += shrink / 2f;
        bounds.width -= shrink;
        bounds.height -= shrink;

        if (bounds.width <= 100 || bounds.height <= 100) {
            active = false; // Stop shrinking
        }
    }

    public void deactivate() {
        active = false;
    }
    public void activate() {
        active = true;
        wasActive = true;
    }

    public boolean isActive() {
        return active;
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

        // Damage if outside bounds
        if (!bounds.contains(playerX, playerY)) {
            player.updateHealth(-damagePerSecond);
        }

        // Clamp the player's position inside the fence
        float clampedX = Math.max(bounds.x, Math.min(playerX, bounds.x + bounds.width - player.getRect().getWidth()));
        float clampedY = Math.max(bounds.y, Math.min(playerY, bounds.y + bounds.height - player.getRect().getHeight()));

        if (clampedX != playerX || clampedY != playerY) {
            player.setPosition(clampedX, clampedY);
            player.getRect().move(clampedX, clampedY);
        }
    }

    public boolean isWasActive() {
        return wasActive;
    }
}
