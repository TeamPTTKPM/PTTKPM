package android.game.menu;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;

public class AboutMenu extends Menu{

	private String _content;
	
	@Override
	public Scene onLoadScene() {
		_myScene = new Scene();
		_myScene.setBackground(new ColorBackground(24, 9, 15));
		return _myScene;
	}
	
	public void setContent(String content)
	{
		_content = content;
	}
	
	public void render()
	{
		
	}
}
