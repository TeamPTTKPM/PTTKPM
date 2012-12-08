package android.game.player;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.game.interfaceSprite.ISprite;
import android.game.items.Items;

public class CCharacter implements ISprite {

	private Scene _myScene ;
	
	private int MOVE_STATE = PLAYER_MOVE_STATE.STAND;
	private final long[] DURATION = { 40, 40, 40, 40, 40, 40, 40, 40 };
	private final int[] ANIMATED_DOWN = { 0, 1, 2, 3, 4, 5, 6, 7 };
	private final int[] ANIMATED_UP = { 8, 9, 10, 11, 12, 13, 14, 15 };
	private final int[] ANIMATED_RIGHT = { 16, 17, 18, 19, 20, 21, 21, 23 };
	private final int[] ANIMATED_LEFT = { 24, 25, 26, 27, 28, 29, 30, 31 };

	private int _speed = 5;

	private float _positionX = 0;
	private float _positionY = 0;

	// Nomal Character
	private BitmapTextureAtlas _bta_charater_stand;
	private TiledTextureRegion _ttr_character_stand;
	private AnimatedSprite _as_character_stand;

	private BitmapTextureAtlas _bta_charater_move;
	private TiledTextureRegion _ttr_character_move;
	private AnimatedSprite _as_character_move;

	private List<Items> _items;

	public CCharacter() {
		_items = new ArrayList<Items>();
	}

	// =======================================|| onLoadResources
	// ||================================
	@Override
	public void onLoadResources(Engine engine, Context context) {

		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("gfx/Character/");

		// Load Image Relax State
		_bta_charater_stand = new BitmapTextureAtlas(128, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		_ttr_character_stand = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(_bta_charater_stand, context,
						"Character_Stand.png", 0, 0, 3, 1);

		engine.getTextureManager().loadTexture(_bta_charater_stand);

		// Load Image Move State
		_bta_charater_move = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		_ttr_character_move = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(_bta_charater_move, context,
						"Character.png", 0, 0, 8, 4);

		engine.getTextureManager().loadTexture(_bta_charater_move);
	}

	@Override
	public void onLoadScene(Scene scene) {
		_myScene = scene;
		
		_as_character_stand = new AnimatedSprite(_positionX, _positionY,
				_ttr_character_stand);
		
		
		_as_character_move = new AnimatedSprite(_positionX, _positionY,
				_ttr_character_move);
		
		playerMoveState();
	}

	private void playerMoveState() {
		if (MOVE_STATE == PLAYER_MOVE_STATE.STAND) {
			
			if(_myScene.getChildIndex(_as_character_move) >= 0)
			{
				_myScene.detachChild(_as_character_move);
			}

			if(_myScene.getChildIndex(_as_character_stand) < 0)
			{
				_as_character_move.setPosition(_positionX, _positionY);
				_myScene.attachChild(_as_character_stand);
			}
			
			
			_as_character_stand.animate(40);
		} else {

			if(_myScene.getChildIndex(_as_character_stand) >= 0)
			{
				_myScene.detachChild(_as_character_stand);
			}

			if(_myScene.getChildIndex(_as_character_move) < 0)
			{
				_as_character_move.setPosition(_positionX, _positionY);
				_myScene.attachChild(_as_character_move);
			}
			
			switch (MOVE_STATE) {
			case PLAYER_MOVE_STATE.MOVE_LEFT: {
				_as_character_move.animate(DURATION, ANIMATED_LEFT, 1000);
				break;
			}
			case PLAYER_MOVE_STATE.MOVE_RIGHT: {
				_as_character_move.animate(DURATION, ANIMATED_RIGHT, 1000);
				break;
			}
			case PLAYER_MOVE_STATE.MOVE_UP: {
				_as_character_move.animate(DURATION, ANIMATED_UP, 1000);
				break;
			}
			case PLAYER_MOVE_STATE.MOVE_DOWN: {
				_as_character_move.animate(DURATION, ANIMATED_DOWN, 1000);
				break;
			}
			case PLAYER_MOVE_STATE.UN_MOVE_LEFT: {
				break;
			}
			case PLAYER_MOVE_STATE.UN_MOVE_RIGHT: {
				break;
			}
			case PLAYER_MOVE_STATE.UN_MOVE_UP: {
				break;
			}
			case PLAYER_MOVE_STATE.UN_MOVE_DOWN: {
				break;
			}
			}
		}
	}

	// =========|| STATE ||================
	public void setMoveState(int state) {
		MOVE_STATE = state;
		playerMoveState();
	}

	public int getMoveState() {
		return MOVE_STATE;
	}

	// =========|| Position ||================
	public void setPositionX(float positionX) {
		_positionX = positionX;
	}

	public float getPositionX() {
		return _positionY;
	}

	public void setPositionY(float positionY) {
		_positionY = positionY;
	}

	public float getPositionY() {
		return _positionY;
	}

	public void setPositionXY(float positionX, float positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
	}

	// =======================================|| Move
	// ||================================
	public void moveX(float moveX) {
		_positionX = moveX;
		movePlayer();
	}

	public void moveY(float moveY) {
		_positionY = moveY;
		movePlayer();
	}

	public void moveXY(float moveX, float moveY) {
		_positionX = moveX;
		_positionY = moveY;
		movePlayer();
	}

	public void moveRelativeX(float moveRelativeX) {
		_positionX += moveRelativeX;
		movePlayer();
	}

	public void moveRelativeY(float moveRelativeY) {
		_positionY += moveRelativeY;
		movePlayer();
	}

	public void moveRelativeXY(float moveRelativeX, float moveRelativeY) {
		_positionX += moveRelativeX;
		_positionY += moveRelativeY;
		movePlayer();
	}

	public void moveUp() {
		moveRelativeY(-_speed);
	}

	public void moveDown() {
		moveRelativeY(_speed);
	}

	public void moveLeft() {
		moveRelativeX(-_speed);
	}

	public void moveRight() {
		moveRelativeX(_speed);
	}

	/**
	 * Move player
	 */
	private void movePlayer() {
		_as_character_move.setPosition(_positionX, _positionY);

	}
}
