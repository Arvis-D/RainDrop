package com.arvideichman.raindrop.play;

import com.arvideichman.raindrop.common.Disposable;
import com.arvideichman.raindrop.common.Intersectable;
import com.arvideichman.raindrop.common.Loadable;
import com.arvideichman.raindrop.common.Updateable;
import com.arvideichman.raindrop.common.entity.Rain;
import com.arvideichman.raindrop.common.state.State;
import com.arvideichman.raindrop.play.entity.Bucket;
import com.arvideichman.raindrop.play.entity.rain.RainDropEmitter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import org.apache.commons.lang3.ArrayUtils;

public class PlayState implements State, Loadable, Disposable, Updateable {
    private SpriteBatch batch = new SpriteBatch();
    private OrthographicCamera camera;
    private Object[] entities;
    private Intersectable[] intersectables = new Intersectable[]{};

    public void addIntersectables(Intersectable[] intersectables) {
        this.intersectables = ArrayUtils.addAll(this.intersectables, intersectables);
    }

    public PlayState() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch.setProjectionMatrix(camera.combined);

        entities = new Object[] {
            new Rain(),
            new Bucket(batch),
            new RainDropEmitter(batch, this)
        };
    }

    @Override
    public void update(float delta) {
        camera.update();
        ScreenUtils.clear(0xa7/255f, 0xb8/255f, 0xc4/255f, 1);
        batch.begin();

        intersectables = new Intersectable[]{};

        for (int i = 0; i < entities.length; i++) {
            var entity = entities[i];
            if (entity instanceof Updateable) ((Updateable) entity).update(delta);

            if (entity instanceof Intersectable) {
                intersectables = ArrayUtils.add(intersectables, (Intersectable) entity);
            }
        }

        for (int i = 0; i < intersectables.length; i++) {
            var intersectable = intersectables[i];
            for (int j = 0; j < intersectables.length; j++) {
                if (j == i) continue;

                var nextIntersectable = intersectables[j];

                if (intersectable.getBounds().overlaps(nextIntersectable.getBounds())) {
                    intersectable.onIntersection(nextIntersectable);
                }
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {
        for (Object entity : entities) {
            if (entity instanceof Disposable) ((Disposable) entity).dispose();
        }
        
        batch.dispose();
    }

    @Override
    public State getState() {
        return this;
    }

    @Override
    public void load() {
        for (Object entity : entities) {
            if (entity instanceof Loadable) ((Loadable) entity).load();
        }
    }
    
}
