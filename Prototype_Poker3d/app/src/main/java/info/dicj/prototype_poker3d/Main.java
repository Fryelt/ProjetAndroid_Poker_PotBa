package info.dicj.prototype_poker3d;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main extends AppCompatActivity {
    private PrototypeGLSurfaceView GLVue;
    boolean checkRender = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GLVue = new PrototypeGLSurfaceView(this);
        setContentView(GLVue);
        //Actuellement, on ne tient pas en compte les différents cas qui ne tourneraient pas le bool à false, alors on le met à true
        checkRender = true;
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
