package android.game.map;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.Debug;

import android.content.Context;

public class Map {
	public static  TMXTiledMap getTMXTiledMap(Scene myScene, Engine engine, Context context, String nameMap)
	{
		try {
		TMXTiledMap tiledMap;
		final TMXLoader tmxLoader = new TMXLoader(context,
				engine.getTextureManager(),
				TextureOptions.BILINEAR_PREMULTIPLYALPHA,
				new ITMXTilePropertiesListener() {
			
			@Override
			public void onTMXTileWithPropertiesCreated(TMXTiledMap pTMXTiledMap,
					TMXLayer pTMXLayer, TMXTile pTMXTile,
					TMXProperties<TMXTileProperty> pTMXTileProperties) {
				
			}
		});
		
		String path = "tmx/" + nameMap;
		tiledMap = tmxLoader.loadFromAsset(context, path);
		return tiledMap;
		
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}
		return null;
		
	}
}
