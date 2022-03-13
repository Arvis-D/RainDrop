package com.arvideichman.raindrop.play.entity.rain;

import com.arvideichman.raindrop.common.Intersectable;
import com.arvideichman.raindrop.common.Updateable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class RainDropEntity implements Updateable, Intersectable {
    private Texture texture;
    private SpriteBatch spriteBatch;
    private Rectangle rect = new Rectangle();
    private RainDropDisappearListener listener;
    private OnBucketListener onBucketListener;

    public void setOnBucketListener(OnBucketListener onBucketListener) {
        this.onBucketListener = onBucketListener;
    }

    public RainDropEntity(Texture texture, SpriteBatch spriteBatch, RainDropDisappearListener listener) {
        this.texture = texture;
        this.spriteBatch = spriteBatch;
        this.listener = listener;

        rect.height = 24;
        rect.width = 16;
        rect.x = 400;
        rect.y = 50;
    }

    @Override
    public void update(float delta) {
        spriteBatch.draw(texture, rect.x, rect.y, rect.width, rect.height);

        rect.y -= delta * 200;

        if (checkIfOutside()) {
            listener.onDisappear(this);
        }
    }

    @Override
    public Rectangle getBounds() {
        return rect;
    }

    public void setPosition(float x, float y) {
        rect.x = x;
        rect.y = y;
    }

    private boolean checkIfOutside() {
        var screenRect = new Rectangle();
        screenRect.x = 0;
        screenRect.y = 0;
        screenRect.height = 480;
        screenRect.width = 800;

        if (rect.overlaps(screenRect)) {
            return false;
        }

        return true;
    }

    @Override
    public void onIntersection(Intersectable inteserctsWith) {
        onBucketListener.onBucket();
        listener.onDisappear(this);
    }
    
}
