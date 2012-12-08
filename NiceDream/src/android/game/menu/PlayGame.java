package android.game.menu;

import java.util.ArrayList;

import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;

import android.game.map.Map;
import android.game.player.CCharacter;
import android.game.player.PLAYER_MOVE_STATE;

public class PlayGame extends Menu implements IOnSceneTouchListener {

	public class Area {
		public static final int NONE = 0;
		public static final int TOP = 1;
		public static final int CENTER_LEFT = 2;
		public static final int CENTER_RIGHT = 3;
		public static final int BOTTOM = 4;
	}

	private CCharacter _character;

	private TMXTiledMap _tmxTiledMap;
	private TMXLayer _obstructionTMXLayer;
	private TMXTile tmxTiled;

	private DigitalOnScreenControl _digitalOnScreenControl;

	public PlayGame() {
		_character = new CCharacter();
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		_myScene = new Scene();
		_myScene.setBackground(new ColorBackground(1, 1, 1));

		// loadMap();
		input();

		_character.onLoadScene(_myScene);
		return _myScene;
	}

	@Override
	public void onLoadResources() {
		_character.onLoadResources(mEngine, this);
	}

	@Override
	public void onLoadComplete() {
	}

	public void loadMap() {
		_tmxTiledMap = Map.getTMXTiledMap(_myScene, mEngine, this,
				"example.tmx");

		ArrayList<TMXLayer> mapLayers = _tmxTiledMap.getTMXLayers();
		for (TMXLayer layer : mapLayers) {
			if (layer.getName().equals("Obstruction")) {
				_obstructionTMXLayer = layer;
			}

			_myScene.attachChild(layer);
		}
	}

	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			int result = getAreaTouch(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			
			switch (result) {
			case Area.TOP:
				_character.setMoveState(PLAYER_MOVE_STATE.MOVE_UP);
				_character.moveUp();
				break;
			case Area.BOTTOM:
				_character.setMoveState(PLAYER_MOVE_STATE.MOVE_DOWN);
				_character.moveDown();
				break;
			case Area.CENTER_LEFT:
				_character.setMoveState(PLAYER_MOVE_STATE.MOVE_LEFT);
				_character.moveLeft();
				break;
			case Area.CENTER_RIGHT:
				_character.setMoveState(PLAYER_MOVE_STATE.MOVE_RIGHT);
				_character.moveRight();
				break;

			default:
				break;
			}
			
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				
				_character.setMoveState(PLAYER_MOVE_STATE.STAND);
			}
		}
		return false;
	}

	private int getAreaTouch(float fX, float fY) {
		float x1 = _width / 4;
		float x2 = x1 * 3;

		float y1 = _height / 3;
		float y2 = y1 * 2;

		if (!(fX > x1 && fX < x2)) {
			if (fY < y1) {
				return Area.TOP;
			} else if (fY > y2) {
				return Area.BOTTOM;
			} else {
				if (fX < x1) {
					return Area.CENTER_LEFT;
				} else {
					return Area.CENTER_RIGHT;
				}
			}
		}
		return Area.NONE;
	}

	private void input() {
		_myScene.setOnSceneTouchListenerBindingEnabled(true);
		_myScene.setOnSceneTouchListener(this);
	}
}
