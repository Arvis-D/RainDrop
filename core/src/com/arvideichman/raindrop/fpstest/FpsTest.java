package com.arvideichman.raindrop.fpstest;

import com.arvideichman.raindrop.common.Disposable;
import com.arvideichman.raindrop.common.Loadable;
import com.arvideichman.raindrop.common.Updateable;
import com.arvideichman.raindrop.common.state.State;
import com.arvideichman.raindrop.common.utils.Fonts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class FpsTest implements Updateable, Disposable, State, Loadable {
    private Texture drop = new Texture(Gdx.files.internal("sprites/waterDrop.png"));
    private SpriteBatch batch = new SpriteBatch();
    private Mover[] rects = new Mover[]{};
    private BitmapFont font = Fonts.getDefault();

    @Override
    public State getState() {
        return this;
    }

    @Override
    public void dispose() {
        batch.dispose();
        drop.dispose();
    }

    @Override
    public void update(float delta) {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        handleInput(delta);
        renderSprites(delta);
        showFps(delta);
        showNumberOfSprites();
        batch.end();
    }

    private void renderSprites(float delta) {
        var width = 800;
        var height = 480;

        for (int i = 0; i < rects.length; i++) {
            var rect = rects[i];

            rect.setX(rect.getX() + (rect.getxVelocity() * delta));
            rect.setY(rect.getY() + (rect.getyVelocity() * delta));

            if (rect.getX() > width || rect.getX() < 0) rect.setxVelocity(-rect.getxVelocity());
            if (rect.getY() > height || rect.getY() < 0) rect.setyVelocity(-rect.getyVelocity());

            batch.draw(drop, rect.getX(), rect.getY());
        }
    }

    private void handleInput(float delta) {
        var increase = 0;

        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            increase++;
            increase += rects.length * delta;
            System.out.println(increase);
        }

        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
            increase--;
            increase -= rects.length * delta;
        }

        var newSize = increase + rects.length;

        if (increase == 0 || newSize == 0) return;


        var newArr = new Mover[newSize];

        for (int i = 0; i < newArr.length; i++) {
            if (i < rects.length) {
                newArr[i] = rects[i];
            } else {
                newArr[i] = new Mover();
            }
        }

        rects = null;
        rects = newArr;
    }

    private void showFps(float delta) {
        var fps = "FPS: " + Math.round(1 / delta);
        font.draw(batch, fps, 10, 20);
    }

    private void showNumberOfSprites() {
        var fps = "SPRITES: " + rects.length;
        Fonts.getDefault().draw(batch, fps, 10, 50);
    }

    @Override
    public void load() {
        var initialSize = 1000;

        rects = new Mover[initialSize];

        for (int i = 0; i < initialSize; i++) {
            rects[i] = new Mover();
        }
    }
    
}
