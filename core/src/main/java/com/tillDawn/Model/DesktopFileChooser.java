package com.tillDawn.Model;

import com.badlogic.gdx.files.FileHandle;

import javax.swing.*;

public class DesktopFileChooser implements FileChooser {
    @Override
    public void chooseFile(FileCallback callback) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Avatar Image");

        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            FileHandle fileHandle = new FileHandle(file);
            callback.onFileChosen(fileHandle);
        } else {
            callback.onFileChosen(null); // User canceled
        }
    }
}
