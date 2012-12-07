package android.game.menu;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;

public class OptionsMenu extends Menu{
	@Override
	public Scene onLoadScene() {
		_myScene = new Scene();
		_myScene.setBackground(new ColorBackground(24, 9, 15));
		return _myScene;
	}
}
