package android.game.menu;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.view.Display;

public class MainMenu extends BaseGameActivity implements
		IOnMenuItemClickListener {

	private Camera _myCamera;
	private Scene _myScene;
	private int _width, _height;

	// Background
	private BitmapTextureAtlas _bg_BitmapTextureAtlas;
	private TextureRegion _bg_TextureRegion;
	private Sprite _bg_Sprite;

	// IMenuItem
	private BitmapTextureAtlas _menuItem_TextureAtlas;
	private TextureRegion _menuItem_TextureRegion;
	List<String> _listNameMenuItem;

	// Font
	private BitmapTextureAtlas _fontTexture;
	private Font _font;

	// MenuScene
	MenuScene _menuScene;

	public MainMenu() {
		_listNameMenuItem = new ArrayList<String>();
	}

	private void loadNameMenuItem() {
		_listNameMenuItem.add(getString(R.string.newgame));
		_listNameMenuItem.add(getString(R.string.options));
		_listNameMenuItem.add(getString(R.string.about));
		_listNameMenuItem.add(getString(R.string.exit));
	}

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
		
		loadNameMenuItem();

		loadFontRegion();

		loadBitmapRegion();
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		_myScene = new Scene();

		// init
		createStaticMenuScene();

		_bg_Sprite = new Sprite(0, 0, _width, _height, _bg_TextureRegion);
		 _myScene.setBackground(new SpriteBackground(_bg_Sprite));

		// add object
		//_myScene.getLastChild().attachChild(_bg_Sprite);
		_myScene.setChildScene(_menuScene);
		return _myScene;
	}

	@Override
	public void onLoadComplete() {

	}

	// =====================================================================
	// Functions
	// =====================================================================

	private void loadFontRegion() {
		_fontTexture = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		FontFactory.setAssetBasePath("font/");
		_font = FontFactory.createFromAsset(this._fontTexture, this,
				"Flubber.ttf", 32, true, Color.WHITE);
		mEngine.getTextureManager().loadTexture(this._fontTexture);
		mEngine.getFontManager().loadFont(this._font);
	}

	private void loadBitmapRegion() {

		// load Background MainMenu
		_bg_BitmapTextureAtlas = new BitmapTextureAtlas(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		_bg_TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(_bg_BitmapTextureAtlas, this,
						"background.png", 0, 0);
		mEngine.getTextureManager().loadTexture(_bg_BitmapTextureAtlas);

		// Load Bitmap for Button
		_menuItem_TextureAtlas = new BitmapTextureAtlas(512, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		_menuItem_TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(_menuItem_TextureAtlas, this, "MenuItem.png",
						0, 0);

		mEngine.getTextureManager().loadTexture(_menuItem_TextureAtlas);
	}

	private void createStaticMenuScene() {
		_menuScene = new MenuScene(_myCamera);

		for (int i = 0; i < _listNameMenuItem.size(); i++) {

			SpriteMenuItem newMenuItem = new SpriteMenuItem(i,
					_menuItem_TextureRegion);
			int cenX = (int) (newMenuItem.getWidth() - _font
					.getStringWidth(_listNameMenuItem.get(i))) / 2;
			int cenY = (int) (newMenuItem.getHeight() - _font.getLineHeight()) / 2;

			Text text = new Text(cenX, cenY, _font, _listNameMenuItem.get(i));
			newMenuItem.attachChild(text);

			_menuScene.addMenuItem(new ScaleMenuItemDecorator(newMenuItem,
					1.2f, 1.0f));
		}

		_menuScene.buildAnimations();

		_menuScene.setBackgroundEnabled(false);

		_menuScene.setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		return false;
	}
}
