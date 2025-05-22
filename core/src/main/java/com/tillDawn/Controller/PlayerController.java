package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.Player;
import com.tillDawn.Model.Tree;

import java.util.ArrayList;

public class PlayerController {
    private Player player;
    boolean facingRight = true;
    boolean flip = false;
    public PlayerController(Player player) {
        this.player = player;
    }

    public void update() {
        boolean isMoving = handlePlayerInput();
        if (!isMoving) {
            idleAnimation();
        }
        else{
            runAnimation();
        }
        player.getPlayerSprite().setPosition(player.getPosX(), player.getPosY());
        player.getPlayerSprite().draw(Main.getInstance().getBatch());

    }

    public boolean handlePlayerInput() {
        float oldX = player.getPosX();
        float oldY = player.getPosY();
        float speed = player.getSpeed();
        boolean moved = false;

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setPosY(oldY - speed);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setPosY(oldY + speed);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setPosX(oldX - speed);
            moved = true;
            if (facingRight) {
                flip = true;
                facingRight = false;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setPosX(oldX + speed);
            moved = true;
            if (!facingRight) {
                flip = true;
                facingRight = true;
            }
        }

        // Update collision rect
        player.getRect().move(player.getPosX(), player.getPosY());

        ArrayList<Tree> trees = App.getInstance().getTrees();
        // Check collision with trees
        for (Tree tree : trees) {
            tree.updateRect(); // make sure it's current
            if (player.getRect().collidesWith(tree.getRect())) {
                // Revert movement on collision
                player.setPosX(oldX);
                player.setPosY(oldY);
                player.getRect().move(oldX, oldY);
                return false;
            }
        }

        return moved;
    }


    public void idleAnimation() {
        Animation<TextureRegion> animation = GameAssetManager.getInstance().getCharacter_idle_frames();

        animation.setPlayMode(Animation.PlayMode.LOOP); // Make sure it's looping

        TextureRegion frame = animation.getKeyFrame(player.getTime(), true); // second param = looping

        if (!facingRight && !frame.isFlipX()) {
            frame.flip(true, false);
        } else if (facingRight && frame.isFlipX()) {
            frame.flip(true, false);
        }

        player.getPlayerSprite().setRegion(frame);
        player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
    }

    public void runAnimation() {
        Animation<TextureRegion> animation = GameAssetManager.getInstance().getCharacter_run_frames();

        animation.setPlayMode(Animation.PlayMode.LOOP); // Make sure it's looping

        TextureRegion frame = animation.getKeyFrame(player.getTime(), true); // second param = looping

        if (!facingRight && !frame.isFlipX()) {
            frame.flip(true, false);
        } else if (facingRight && frame.isFlipX()) {
            frame.flip(true, false);
        }

        player.getPlayerSprite().setRegion(frame);
        player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
    }
}
