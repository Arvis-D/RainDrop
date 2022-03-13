package com.arvideichman.raindrop.common.state;

import com.arvideichman.raindrop.common.Disposable;
import com.arvideichman.raindrop.common.Loadable;
import com.arvideichman.raindrop.common.Updateable;
import com.arvideichman.raindrop.common.utils.Fonts;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements State, Runnable, Updateable, Loadable, Disposable {
    private Thread stateLoaderThread;
    private State state = null;
    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font;
    private boolean finishedLoading = false;

    public State getState() {
        if (!finishedLoading) {
            return this;
        }

        batch.dispose();

        return state;
    }

    public LoadingScreen(State state) {
        this.state = state;
        stateLoaderThread = new Thread(this);
        stateLoaderThread.start();
    }

    public LoadingScreen() {}

    @Override
    public void update(float delta) {
        batch.begin();
        font.draw(batch, "Loading...", 100, 100);
        batch.end();
    }

    @Override
    public void dispose() {
        if (state instanceof Disposable) ((Disposable) state).dispose();
    }

    @Override
    public void run() {
        if (state instanceof Loadable) ((Loadable) state).load();
        finishedLoading = true;
    }

    @Override
    public void load() {
        font = Fonts.getDefault();
    }
}
