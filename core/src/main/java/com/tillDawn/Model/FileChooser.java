package com.tillDawn.Model;

import com.badlogic.gdx.files.FileHandle;

import java.util.function.Consumer;

public interface FileChooser {
    void chooseFile(FileCallback callback);

    interface FileCallback {
        void onFileChosen(com.badlogic.gdx.files.FileHandle fileHandle);
    }
}
