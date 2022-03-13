package com.arvideichman.raindrop;

import com.arvideichman.raindrop.common.Disposable;
import com.arvideichman.raindrop.common.Loadable;
import com.arvideichman.raindrop.common.Updateable;
import com.arvideichman.raindrop.common.state.LoadingScreen;
import com.arvideichman.raindrop.common.state.State;
import com.arvideichman.raindrop.play.PlayState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class RainDrop extends ApplicationAdapter {
	private State state;

	@Override
	public void create () {
		state = new LoadingScreen(new PlayState());
		if (state instanceof Loadable) ((Loadable) state).load();
	}

	@Override
	public void render () {
		state = state.getState();

		if (state == null) {
			Gdx.app.exit();
		} else {
			if (state instanceof Updateable)
				((Updateable) state).update(Gdx.graphics.getDeltaTime());
		}
	}
	
	@Override
	public void dispose () {
		if (state instanceof Disposable) ((Disposable) state).dispose();
	}
}
