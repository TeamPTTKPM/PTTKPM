package android.game.menu;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.view.Display;

public abstract class Menu extends BaseGameActivity{

	protected Camera _myCamera;
	protected Scene _myScene;
	protected int _width, _height;
	
	@Override
	public Engine onLoadEngine() {
		final Display display = getWindowManager().getDefaultDisplay();
		_width = display.getWidth();
		_height = display.getHeight();

		_myCamera = new Camera(0, 0, _width, _height);

		EngineOptions pEngineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(_width,
						_height), _myCamera).setNeedsMusic(true).setNeedsSound(
				true);
		return new Engine(pEngineOptions);
	}

	@Override
	public void onLoadResources() {
		
	}

	@Override
	public Scene onLoadScene() {
		return null;
	}

	@Override
	public void onLoadComplete() {
	}
	
}
