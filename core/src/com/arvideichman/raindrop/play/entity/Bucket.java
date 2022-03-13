package com.arvideichman.raindrop.play.entity;

import com.arvideichman.raindrop.common.Disposable;
import com.arvideichman.raindrop.common.Intersectable;
import com.arvideichman.raindrop.common.Updateable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bucket implements Updateable, Disposable, Intersectable {
    private Texture texture = new Texture(Gdx.files.internal("sprites/bucket.png"));
    private SpriteBatch batch;
    private Rectangle rect = new Rectangle();

    public Bucket(SpriteBatch batch) {
        this.batch = batch;
        rect.height = 100;
        rect.width = 100;
        rect.x = 400;
        rect.y = 20;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
            rect.x += delta * 200;
        }

        if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            rect.x -= delta * 200;
        }

        

        batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
    }

    @Override
    public void dispose() {
        texture.dispose();
        
    }

    @Override
    public Rectangle getBounds() {
        return rect;
    }

    @Override
    public void onIntersection(Intersectable inteserctsWith) {

    }
    
}
