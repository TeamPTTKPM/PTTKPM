package android.game.interfaceSprite;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;

import android.content.Context;

public interface ISprite {
	public void onLoadResources(Engine engine, Context context);
	public void onLoadScene(Scene scene);
}
