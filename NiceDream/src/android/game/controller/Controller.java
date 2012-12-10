package android.game.controller;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;

import android.game.menu.PlayGame.Area;
import android.view.MotionEvent;

public class Controller{

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int INVALID_POINTER_ID = -1;

	// ===========================================================
	// Fields
	// ===========================================================

	private Scene _scene;
	
	private float _touchAreaLocalX;
	private float _touchAreaLocalY;

	private int _width, _height;
	
	// private final IOnScreenControlListener mOnScreenControlListener;
	private final IOnControllerListener _onControllerListener;

	private int _activePointerID = INVALID_POINTER_ID;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Controller(final int w, final int h, final Scene scene, final float _betweenTimeUpdates, final IOnControllerListener onControllerListener) {
		
		_scene = scene;
		_width = w;
		_height = h;
		
		this._onControllerListener = onControllerListener;
		
		_scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				return Controller.this.onHandlerControllerScene(pSceneTouchEvent, pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			}
		});
		
		_scene.registerUpdateHandler(new TimerHandler(_betweenTimeUpdates, true, new ITimerCallback() {	
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if(_activePointerID != INVALID_POINTER_ID){
					int result = getAreaTouch(_touchAreaLocalX, _touchAreaLocalY);
					Controller.this._onControllerListener.onControlChange(Controller.this, _touchAreaLocalX, _touchAreaLocalY, result);
				}
			}
		}));
		
		_scene.setOnSceneTouchListenerBindingEnabled(true);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	// ===========================================================
	// Methods
	// ===========================================================

	protected boolean onHandlerControllerScene(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		final int pointerID = pSceneTouchEvent.getPointerID();

		switch(pSceneTouchEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(this._activePointerID == INVALID_POINTER_ID) {
					this._activePointerID = pointerID;
					updateTouchPosition(pTouchAreaLocalX, pTouchAreaLocalY);
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if(this._activePointerID == pointerID) {
					this._activePointerID = INVALID_POINTER_ID;
					return true;
				}
				break;
			default:
				if(this._activePointerID == pointerID) {
					updateTouchPosition(pTouchAreaLocalX, pTouchAreaLocalY);
					return true;
				}
				break;
		}
		return true;
	}
	
	protected void updateTouchPosition(float pTouchAreaLocalX, float pTouchAreaLocalY) {
		_touchAreaLocalX = pTouchAreaLocalX;
		_touchAreaLocalY = pTouchAreaLocalY;
	}
	
	private int getAreaTouch(float fX, float fY) {
		float x1 = _width/ 4;
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

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static interface IOnControllerListener {
		public void onControlChange(final Controller controller, final float pValueX, final float pValueY, final int iResultArea);
	}

}
