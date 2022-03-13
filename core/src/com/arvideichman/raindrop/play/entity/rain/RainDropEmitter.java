package com.arvideichman.raindrop.play.entity.rain;

import com.arvideichman.raindrop.common.Disposable;
import com.arvideichman.raindrop.common.Loadable;
import com.arvideichman.raindrop.common.Updateable;
import com.arvideichman.raindrop.play.PlayState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.apache.commons.lang3.ArrayUtils;

public class RainDropEmitter implements Updateable, Disposable, RainDropDisappearListener, Runnable, Loadable {
    private Texture rainDropTexture = new Texture(Gdx.files.internal("sprites/waterDrop.png"));
    private Sound rainDropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/waterDrop.ogg"));
    private SpriteBatch spriteBatch;
    private RainDropEntity[] rainDrops = new RainDropEntity[]{};
    private Thread timerThread;
    private PlayState playstate;

    public RainDropEmitter(SpriteBatch spriteBatch, PlayState playstate) {
        this.spriteBatch = spriteBatch;
        this.playstate = playstate;
    }

    @Override
    public void update(float delta) {
        if (!timerThread.isAlive()) timerThread.start();

        for (RainDropEntity entity : rainDrops) {
            entity.update(delta);
        }

        playstate.addIntersectables(rainDrops);
    }

    @Override
    public void dispose() {
        rainDropTexture.dispose();
        
    }

    @Override
    public void load() {
        timerThread = new Thread(this);
    }

    @Override
    public void onDisappear(RainDropEntity rainDrop) {
        rainDrops = ArrayUtils.removeElement(rainDrops, rainDrop);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                return;
            }

            Gdx.app.postRunnable(() -> {
                emitRainDrop();
            });
        }
    }

    private void emitRainDrop() {
        var rainDrop = new RainDropEntity(rainDropTexture, spriteBatch, this);
        rainDrop.setPosition(Math.round(Math.random() * 800), 479);
        rainDrop.setOnBucketListener(() -> {
            rainDropSound.play();
        });
        rainDrops = ArrayUtils.add(rainDrops, rainDrop);
    }
}
