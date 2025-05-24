package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private Music currentMusic = Gdx.audio.newMusic(Gdx.files.internal("music/music1.mp3"));
    private static MusicManager musicManager;
    private float volume = 1.0f;
    public static MusicManager getMusicManager() {
        if (musicManager == null) {
            musicManager = new MusicManager();
        }
        return musicManager;
    }
    public void play(String path, boolean loop, float volume) {
        stop(); // Stop existing music if any
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
        currentMusic.setLooping(loop);
        currentMusic.setVolume(volume);
        currentMusic.play();
    }

    public void stop() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
            currentMusic = null;
        }
    }

    public void setVolume(float volume) {
        if (currentMusic != null) currentMusic.setVolume(volume);
        this.volume = volume;
    }

    public void setLooping(boolean looping) {
        if (currentMusic != null) currentMusic.setLooping(looping);
    }

    public void pause() {
        if (currentMusic != null) currentMusic.pause();
    }

    public void resume() {
        if (currentMusic != null) currentMusic.play();
    }

    public Music getCurrentMusic() {
        return currentMusic;
    }

    public float getVolume() {
        return volume;
    }

    public void changeVolume(float volume) {
        this.volume = volume;
    }
}
