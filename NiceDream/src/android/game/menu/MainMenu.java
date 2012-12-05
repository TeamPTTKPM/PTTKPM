package android.game.menu;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.view.Display;

public class MainMenu extends BaseGameActivity {
	private Camera _myCamera;
	private Scene _myScene;
	private int _width, _height;

	private BitmapTextureAtlas _bg_BitmapTextureAtlas;
	private TextureRegion _bg_TextureRegion;
	private Sprite _bg_Sprite;

	@Override
	public Engine onLoadEngine() {
		final Display display = getWindowManager().getDefaultDisplay();
		_width = display.getWidth();
		_height = display.getHeight();

		_myCamera = new Camera(0, 0, _width, _height);

		EngineOptions pEngineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), _myCamera).setNeedsMusic(
				true).setNeedsSound(true);
		return new Engine(pEngineOptions);
	}

	@Override
	public void onLoadResources() {
		_bg_BitmapTextureAtlas = new BitmapTextureAtlas(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		_bg_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(_bg_BitmapTextureAtlas, this, "background.png", 0, 0);
		
		mEngine.getTextureManager().loadTexture(_bg_BitmapTextureAtlas);
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		_myScene = new Scene();
		//_bg_Sprite = new Sprite(0, 0,_width, _height, _bg_TextureRegion);
		_myScene.setBackground(new SpriteBackground(_bg_Sprite));
		
		return _myScene;
	}

	@Override
	public void onLoadComplete() {

	}
}
