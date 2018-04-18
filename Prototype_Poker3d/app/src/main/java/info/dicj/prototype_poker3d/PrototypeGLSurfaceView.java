package info.dicj.prototype_poker3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by PotBa1632703 on 2018-03-20.
 */

public class PrototypeGLSurfaceView extends GLSurfaceView {
    protected final PrototypeGLRenderer mRenderer;

    public PrototypeGLSurfaceView(Context context){
        super(context);

        // Contexte OpenGL version 2
        setEGLContextClientVersion(2);

        mRenderer = new PrototypeGLRenderer(this.getContext());
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null){
                    final float normX = (event.getX() / (float) v.getWidth()) * 2 - 1;
                    final float normY = -((event.getY() / (float) v.getHeight()) * 2 - 1);

                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                        queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                mRenderer.handleTouchPress(normX, normY);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE){
                        queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                mRenderer.handleTouchDrag(normX, normY);
                            }
                        });
                    }
                    return true;
                }
                else
                    return false;
            }
        });
    }
}
