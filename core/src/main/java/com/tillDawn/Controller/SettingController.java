package com.tillDawn.Controller;

import com.tillDawn.Model.App;
import com.tillDawn.Model.MusicManager;

public class SettingController extends Controller{
    public void setMusicTrack(String track){
        if(!App.getInstance().isMusicSound())
            return;
        MusicManager.getMusicManager().play("music/"+track, true, MusicManager.getMusicManager().getVolume());
    }
}
