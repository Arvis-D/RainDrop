package com.arvideichman.raindrop.common;

import com.badlogic.gdx.math.Rectangle;

public interface Intersectable {
    Rectangle getBounds();
    void onIntersection(Intersectable inteserctsWith);
}
