package info.dicj.prototype_poker3d;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class PrototypeGLActivity extends AppCompatActivity {
    private PrototypeGLSurfaceView GLVue;
    boolean checkRender = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GLVue = new PrototypeGLSurfaceView(this);
        checkRender = true;

        GLVue.setRenderer(GLVue.mRenderer);
        setContentView(GLVue);
        // P.179 !!
    }
    @Override
    protected void onPause(){
        super.onPause();
        if (checkRender){
            GLVue.onPause();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (checkRender){
            GLVue.onResume();
        }
    }

}
