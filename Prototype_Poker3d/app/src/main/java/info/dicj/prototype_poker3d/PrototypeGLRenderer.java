package info.dicj.prototype_poker3d;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by PotBa1632703 on 2018-03-20.
 */

public class PrototypeGLRenderer implements GLSurfaceView.Renderer {
    public volatile float angleP;

    private Triangle triangleP;
    private Carre carreP;

    private final float[] MVPMatrixP = new float[16];
    private final float[] projectionMatrixP = new float[16];
    private final float[] viewMatrixP = new float[16];

    private float[] rotationMatrixP = new float[16];

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        //Couleur du fond
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        carreP = new Carre();
        triangleP = new Triangle();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        float ratio = (float) width/height;

        Matrix.frustumM(projectionMatrixP, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];
        //Recoloration du fond d'écran
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.setLookAtM(viewMatrixP, 0, 0, 2, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(MVPMatrixP, 0, projectionMatrixP, 0, viewMatrixP, 0);

        //triangleP.draw(MVPMatrixP);

        //Rotation pour le triangle
        //long time = SystemClock.uptimeMillis() % 4000L;
        //float angle  = 0.090f * ((int) time);
        Matrix.setRotateM(rotationMatrixP, 0, angleP, 0, 0,  -1.0f);

        Matrix.multiplyMM(scratch, 0, MVPMatrixP, 0, rotationMatrixP, 0);
        carreP.draw(scratch);
    }

    public static int loadShader(int type, String shaderCode){

        // vertex shader type (GLES20.GL_VERTEX_SHADER) fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public float getAngle(){
        return angleP;
    }
    public void setAngle(float angle){
        angleP = angle;
    }
}
