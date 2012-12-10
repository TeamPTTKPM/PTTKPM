package android.game.menu;

import java.util.ArrayList;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;

import android.game.controller.Controller;
import android.game.controller.Controller.IOnControllerListener;
import android.game.map.Map;
import android.game.player.CCharacter;
import android.game.player.PLAYER_MOVE_STATE;

public class PlayGame extends Menu {

	public class Area {
		public static final int NONE = 0;
		public static final int TOP = 1;
		public static final int CENTER_LEFT = 2;
		public static final int CENTER_RIGHT = 3;
		public static final int BOTTOM = 4;
	}

	private Controller _myController;

	private CCharacter _character;

	private TMXTiledMap _tmxTiledMap;
	private TMXLayer _obstructionTMXLayer;
	private TMXTile tmxTiled;

	int _resultArea = -1;

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

	private void input() {
		_myController = new Controller(_width, _height, _myScene, 0.1f,
				new IOnControllerListener() {
					@Override
					public void onControlChange(Controller controller,
							float pValueX, float pValueY, int iResultArea) {

						switch (iResultArea) {
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
						case Area.NONE:
							/*switch (_character.getMoveState()) {
							case PLAYER_MOVE_STATE.MOVE_UP: 
								_character.setMoveState(PLAYER_MOVE_STATE.UN_MOVE_UP);
								break;
							case PLAYER_MOVE_STATE.MOVE_DOWN:
								_character.setMoveState(PLAYER_MOVE_STATE.UN_MOVE_DOWN);
								break;
							case PLAYER_MOVE_STATE.MOVE_LEFT:
								_character.setMoveState(PLAYER_MOVE_STATE.UN_MOVE_LEFT);
								break;
							case PLAYER_MOVE_STATE.MOVE_RIGHT:
								_character.setMoveState(PLAYER_MOVE_STATE.UN_MOVE_RIGHT);
								break;
							default:
								break;
							}*/
						}
					}
				});
	}

	
}
