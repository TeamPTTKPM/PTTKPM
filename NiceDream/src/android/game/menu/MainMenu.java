package android.game.menu;

import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
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

import android.content.Intent;
import android.graphics.Color;

public class MainMenu extends Menu implements
		IOnMenuItemClickListener {

	// Background
	private BitmapTextureAtlas _bg_BitmapTextureAtlas;
	private TextureRegion _bg_TextureRegion;
	private Sprite _bg_Sprite;

	// IMenuItem
	private BitmapTextureAtlas _menuItem_TextureAtlas;
	private TextureRegion _menuItem_TextureRegion;
	String[] _listNameMenuItem;

	// Font
	private BitmapTextureAtlas _fontTexture;
	private Font _font;

	// MenuScene
	MenuScene _menuScene;
	

	protected static final int MENU_NEWGAME = 0;
	protected static final int MENU_OPTIONS = MENU_NEWGAME + 1;
	protected static final int MENU_ABOUT = MENU_OPTIONS + 1;
	protected static final int MENU_QUIT = MENU_ABOUT + 1;
	
	
	/*protected static final int MENU_SCORES = MENU_NEWGAME + 1;
	protected static final int MENU_HELP = MENU_OPTIONS + 1;*/
	
	public MainMenu() {
		_listNameMenuItem = new String[MENU_QUIT + 1];
	}

	private void loadNameMenuItem() {
		_listNameMenuItem[MENU_NEWGAME] = getString(R.string.newgame);
		_listNameMenuItem[MENU_OPTIONS] = getString(R.string.options);
		_listNameMenuItem[MENU_ABOUT] = getString(R.string.about);
		_listNameMenuItem[MENU_QUIT] = getString(R.string.exit);
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
		_myScene.setChildScene(_menuScene);
		return _myScene;
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
		
		for (int i = MENU_NEWGAME; i <= MENU_QUIT; i++) {
			
			SpriteMenuItem newMenuItem = new SpriteMenuItem(i,
					_menuItem_TextureRegion);
			int cenX = (int) (newMenuItem.getWidth() - _font
					.getStringWidth(_listNameMenuItem[i])) / 2;
			int cenY = (int) (newMenuItem.getHeight() - _font.getLineHeight()) / 2;

			Text text = new Text(cenX, cenY, _font, _listNameMenuItem[i]);
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
		
		switch (pMenuItem.getID()) {
		case MENU_NEWGAME:
			Intent intent = new Intent(this, PlayGame.class);
			startActivity(intent);
			break;
		case MENU_OPTIONS:
			break;
		case MENU_ABOUT:
			break;
		case MENU_QUIT:
			this.finish();
			break;
		}
		return false;
	}
}
