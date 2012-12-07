package android.game.menu;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;

public class PlayGame extends Menu{
	@Override
	public Scene onLoadScene() {
		_myScene = new Scene();
		_myScene.setBackground(new ColorBackground(1, 1, 1));
		return _myScene;
	}
}
