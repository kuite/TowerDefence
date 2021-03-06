package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Save {

    public static GameData gd;
    public static FileHandle fl = Gdx.files.internal("highscores.sav");

    public static void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("/sdcard/highscores.sav")
            );
            out.writeObject(gd);
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void load() {
        try {
            if(!saveFileExists()) {
                init();
                return;
            }
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("/sdcard/highscores.sav")
            );
            gd = (GameData) in.readObject();
            in.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists() {
        File f = new File("/sdcard/highscores.sav");
        return f.exists();
    }

    public static void init() {
        gd = new GameData();
        gd.init();
        save();
    }

}
