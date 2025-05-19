package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tillDawn.Main;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.Player;

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
        if (isMoving) {
            idleAnimation();
        }
        player.getPlayerSprite().draw(Main.getInstance().getBatch());

    }

    public boolean handlePlayerInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setPosY(player.getPosY() - player.getSpeed());
            flip = false;
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setPosY(player.getPosY() + player.getSpeed());
            flip = false;
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setPosX(player.getPosX() - player.getSpeed());
            flip = false;
            if (!facingRight) {
                flip = true;
                facingRight = true;
            }
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setPosX(player.getPosX() + player.getSpeed());
            flip = false;
            if (facingRight) {
                flip = true;
                facingRight = false;
            }
            return true;
        }
        return false;
    }

    public void idleAnimation() {
        Animation<TextureRegion> animation = GameAssetManager.getInstance().getCharacter1_idle_frames();

        TextureRegion frame = animation.getKeyFrame(player.getTime());

        if (!facingRight && !frame.isFlipX()) {
            frame.flip(true, false);
        } else if (facingRight && frame.isFlipX()) {
            frame.flip(true, false);
        }

        player.getPlayerSprite().setRegion(frame);

        player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

}
