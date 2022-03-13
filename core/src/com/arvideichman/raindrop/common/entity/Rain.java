package com.arvideichman.raindrop.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.arvideichman.raindrop.common.Disposable;
import com.arvideichman.raindrop.common.Loadable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Rain implements Loadable, Disposable, Runnable {
    private List<Sound> thunderSounds = new ArrayList<>(3);
    private Thread thunderThread;
    private Music rainSound;

    @Override
    public void dispose() {
        thunderThread.interrupt();
        rainSound.dispose();

        for (Sound sound : thunderSounds) {
            sound.dispose();
        }
    }

    private void loadThunderSounds() {
        for (int i = 1; i <= 10; i++) {
            thunderSounds.add(Gdx.audio.newSound(Gdx.files.internal("sounds/thunder" + i + ".ogg")));
        }
    }

    @Override
    public void run() {
        var intervalBase = 40L;
        var intervalRandomnessRange = 50L;

        while (!Thread.currentThread().isInterrupted()) {
            var interval = intervalBase + Math.round((Math.random() - 0.5) * intervalRandomnessRange);
            var thunderIndex = Math.round(Math.random() * (thunderSounds.size() - 1));

            try {
                Thread.sleep(interval * 1000);
            } catch (Exception e) {
                return;
            }

            if (thunderIndex >= 0) {
                thunderSounds.get((int) thunderIndex).play();
            }
        }
    }

    @Override
    public void load() {
        loadThunderSounds();
        rainSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/rain.ogg"));
        thunderThread = new Thread(this);

        Gdx.app.postRunnable(() -> { startPlaying(); });
    }

    private void startPlaying() {
        rainSound.setLooping(true);
        rainSound.play();
        thunderThread.start();
    }
}
