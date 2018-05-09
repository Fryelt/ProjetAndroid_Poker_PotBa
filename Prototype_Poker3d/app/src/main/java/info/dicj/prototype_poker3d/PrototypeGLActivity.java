package info.dicj.prototype_poker3d;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrototypeGLActivity extends AppCompatActivity {
    private PrototypeGLSurfaceView GLVue;
    private Button bouton;
    boolean checkRender = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        PrototypeGLSurfaceView K = (PrototypeGLSurfaceView)this.findViewById(R.id.PGLVue);
        GLVue = K;
        checkRender = true;

        bouton = (Button)findViewById(R.id.Test1);
        bouton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                GLVue.mRenderer.ajoutCarte();
            }
        });

        bouton = (Button)findViewById(R.id.Test2);
        bouton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                GLVue.mRenderer.supprimListe();
            }
        });

        bouton = (Button)findViewById(R.id.Test5);
        bouton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                GLVue.mRenderer.ajoutCube();
            }
        });
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
