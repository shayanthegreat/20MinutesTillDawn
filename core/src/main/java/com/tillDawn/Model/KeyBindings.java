package com.tillDawn.Model;
import com.badlogic.gdx.Input;
public class KeyBindings {
    public static int MOVE_LEFT = Input.Keys.A;
    public static int MOVE_UP = Input.Keys.W;
    public static int MOVE_DOWN = Input.Keys.S;
    public static int MOVE_RIGHT = Input.Keys.D;
    public static int ACTION_RELOAD = Input.Keys.R;
    public static int ACTION_AIM = Input.Keys.SPACE;
    public static int ACTION_CLICK = Input.Keys.NUM_LOCK;
    public static void setBinding(String action, int keycode) {
        switch (action) {
            case "MOVE_LEFT": MOVE_LEFT = keycode; break;
            case "MOVE_UP": MOVE_UP = keycode; break;
            case "MOVE_DOWN": MOVE_DOWN = keycode; break;
            case "MOVE_RIGHT": MOVE_RIGHT = keycode; break;
            case "ACTION_RELOAD": ACTION_RELOAD = keycode; break;
            case "ACTION_AIM": ACTION_AIM = keycode; break;
            case "ACTION_CLICK": ACTION_CLICK = keycode; break;
        }
    }

    public static int getBinding(String action) {
        switch (action) {
            case "MOVE_LEFT": return MOVE_LEFT;
            case "MOVE_UP": return MOVE_UP;
            case "MOVE_DOWN": return MOVE_DOWN;
            case "MOVE_RIGHT": return MOVE_RIGHT;
            case "ACTION_RELOAD": return ACTION_RELOAD;
            case "ACTION_AIM": return ACTION_AIM;
            case "ACTION_CLICK": return ACTION_CLICK;
        }
        return -1;
    }
}
