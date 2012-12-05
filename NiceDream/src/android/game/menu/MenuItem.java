package android.game.menu;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

public class MenuItem {
	private BitmapTextureAtlas _menuItem_TextureAtlas;
	private TextureRegion _menuItem_TextureRegion;
	private String _nameMenuItem;
	private Context _context;
	private Engine _engine;
	private Font _font;
	
	private int _x;
	private int _y;

	private Sprite _menuItem_Sprite;
	
	public MenuItem(Context context, Engine engine, TextureRegion textureRegion, Font font) {
		_context = context;
		_engine = engine;
		_menuItem_TextureRegion = textureRegion;
		_font = font;
	}

	public void loadStringResource() {
		//
	}

	public void render(Scene scene)
	{
		_menuItem_Sprite = new Sprite(_x, _y, _menuItem_TextureRegion);
		Text text = new Text(0, 0, _font, "New Game");
		scene.attachChild(_menuItem_Sprite);
	}
	
	public TextureRegion get_menuItemTextureRegion() {
		return _menuItem_TextureRegion;
	}

	public void set_menuItemTextureRegion(TextureRegion _menuItemTextureRegion) {
		this._menuItem_TextureRegion = _menuItemTextureRegion;
	}

	public String get_nameMenuItem() {
		return _nameMenuItem;
	}

	public void set_nameMenuItem(String _nameMenuItem) {
		this._nameMenuItem = _nameMenuItem;
	}
	

	public int getX() {
		return _x;
	}
	

	public void setX(int x) {
		this._x = x;
	}

	public int getY() {
		return _y;
	}

	public void setY(int y) {
		this._y = y;
	}
}
