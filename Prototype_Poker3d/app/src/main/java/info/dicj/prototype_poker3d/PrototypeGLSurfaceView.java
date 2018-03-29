package info.dicj.prototype_poker3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by PotBa1632703 on 2018-03-20.
 */

public class PrototypeGLSurfaceView extends GLSurfaceView {
    private final PrototypeGLRenderer mRenderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX, previousY;

    public PrototypeGLSurfaceView(Context context){
        super(context);

        // Contexte OpenGL version 2
        setEGLContextClientVersion(2);

        mRenderer = new PrototypeGLRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e){
        float x, y, dx, dy;
        x = e.getX();
        y = e.getY();

        switch (e.getAction()){
            case MotionEvent.ACTION_MOVE:
                dx = x - previousX;
                dy = y - previousY;

                if (y > getHeight() / 2){
                    dx = dx * -1;
                }
                if (x < getWidth() / 2){
                    dy = dy * -1;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                        ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }
        previousX = x;
        previousY = y;
        return true;
    }
}
