package com.tillDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tillDawn.Model.App;
import com.tillDawn.Model.Player;

public class CameraController {
    private static CameraController cameraController;

    private OrthographicCamera camera;
    public static CameraController getCameraController() {
        if (cameraController == null) {
            cameraController = new CameraController();
        }
        return cameraController;
    }
    public CameraController() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false); // Origin bottom-left
        centerOnPlayer();
        camera.update();
    }

    public void update() {
        centerOnPlayer();
        camera.update();
    }

    private void centerOnPlayer() {
        Player player = App.getInstance().getCurrentPlayer();
        camera.position.set(player.getPosX(), player.getPosY(), 0);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
