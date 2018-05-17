package info.dicj.prototype_poker3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by PotBa1632703 on 2018-03-20.
 */

public class PrototypeGLSurfaceView extends GLSurfaceView {
    protected PrototypeGLRenderer RendererOpenGL;

    public PrototypeGLSurfaceView(Context context){
        super(context);
        setEvents();
    }

    public PrototypeGLSurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);
        setEvents();
    }

    private void setEvents(){
        // Contexte OpenGL version 2
        setEGLContextClientVersion(2);

        //Création du renderer OpenGL
        RendererOpenGL = new PrototypeGLRenderer(this.getContext());
        //Création du touch listener
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
                                RendererOpenGL.handleTouchPress(normX, normY);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE){
                        queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                RendererOpenGL.handleTouchDrag(normX, normY);
                            }
                        });
                    }
                    return true;
                }
                else
                    return false;
            }
        });

        this.setRenderer(RendererOpenGL);
    }
}
