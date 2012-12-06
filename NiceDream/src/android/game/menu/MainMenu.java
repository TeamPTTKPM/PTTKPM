package android.game.menu;

import java.util.ArrayList;
import java.util.List;

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
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Display;

public class MainMenu extends BaseGameActivity {
	private Camera _myCamera;
	private Scene _myScene;
	private int _width, _height;

	private BitmapTextureAtlas _bg_BitmapTextureAtlas;
	private TextureRegion _bg_TextureRegion;
	private Sprite _bg_Sprite;
	
	private BitmapTextureAtlas _menuItem_TextureAtlas;
	private TextureRegion _menuItem_TextureRegion;
	List<String> _listNameMenuItem;
	List<MenuItem> _listMenu;
	
	private BitmapTextureAtlas _fontTexture;
	private Font _font;

	
	
	public MainMenu() {
		_listMenu = new ArrayList<MenuItem>();
		_listNameMenuItem = new ArrayList<String>();
	}
	
	public void loadNameMenuItem()
	{
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
				ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), _myCamera).setNeedsMusic(
				true).setNeedsSound(true);
		return new Engine(pEngineOptions);
		
	}

	@Override
	public void onLoadResources() {
		
		// Load Language
		loadNameMenuItem();
		
		// load Background
		_bg_BitmapTextureAtlas = new BitmapTextureAtlas(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		_bg_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(_bg_BitmapTextureAtlas, this, "background.png", 0, 0);
		mEngine.getTextureManager().loadTexture(_bg_BitmapTextureAtlas);
		
		
		// Load Font
		_fontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    _font = new Font(this._fontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 30, true, Color.BLACK);
	    mEngine.getTextureManager().loadTexture(this._fontTexture);
	    mEngine.getFontManager().loadFont(this._font);
	    
		// Load MenuItem
		int centerX = 0;
		
		_menuItem_TextureAtlas = new BitmapTextureAtlas(512, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		_menuItem_TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(_menuItem_TextureAtlas, this, "MenuItem.png",
						0, 0);
		
		mEngine.getTextureManager().loadTexture(_menuItem_TextureAtlas);
		
		centerX = (_width - _menuItem_TextureRegion.getWidth())/2;
		
		for (int i = 0; i < _listNameMenuItem.size(); i++) {
			MenuItem newMenuItem = new MenuItem(this, mEngine, _menuItem_TextureRegion, _font);
			String name = _listNameMenuItem.get(i);
			newMenuItem.set_nameMenuItem(name);
			newMenuItem.setX(centerX);
			newMenuItem.setY(80 + 100 * i);
			_listMenu.add(newMenuItem);
		}
		/*MenuItem newMenuItem1 = new MenuItem(this, mEngine, _menuItem_TextureRegion, _font);
		newMenuItem1.set_nameMenuItem("New Game");
		newMenuItem1.setX(centerX);
		newMenuItem1.setY(50);
		_listMenu.add(newMenuItem1);
		
		MenuItem newMenuItem2 = new MenuItem(this, mEngine, _menuItem_TextureRegion, _font);
		
		newMenuItem2.set_nameMenuItem("Options");
		newMenuItem2.setX(centerX);
		newMenuItem2.setY(150);
		_listMenu.add(newMenuItem2);
		
		MenuItem newMenuItem3 = new MenuItem(this, mEngine, _menuItem_TextureRegion, _font);
		
		newMenuItem3.set_nameMenuItem("About");
		newMenuItem3.setX(centerX);
		newMenuItem3.setY(250);
		_listMenu.add(newMenuItem3);
		
		MenuItem newMenuItem4 = new MenuItem(this, mEngine, _menuItem_TextureRegion, _font);
		
		newMenuItem4.set_nameMenuItem("Exit");
		newMenuItem4.setX(centerX);
		newMenuItem4.setY(350);
		_listMenu.add(newMenuItem4);*/
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		_myScene = new Scene();
		_bg_Sprite = new Sprite(0, 0,_width, _height, _bg_TextureRegion);
		_myScene.setBackground(new SpriteBackground(_bg_Sprite));
		
		for (MenuItem menuItem : _listMenu) {
			menuItem.render(_myScene);
		}
		
		return _myScene;
	}

	@Override
	public void onLoadComplete() {

	}
}
