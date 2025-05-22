package com.tillDawn.Controller;

import com.tillDawn.Model.MusicManager;

public class SettingController extends Controller{
    public void setMusicTrack(String track){
        MusicManager.getMusicManager().play("music/"+track, true, 0.8f);
    }
}
