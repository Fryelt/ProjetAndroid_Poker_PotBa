package info.dicj.prototype_poker3d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import info.dicj.prototype_poker3d.objets.Jeton;
import info.dicj.prototype_poker3d.programs.ColorShaderProgram;
import info.dicj.prototype_poker3d.programs.TextureShaderProgram;
import info.dicj.prototype_poker3d.util.Geometrie;
import info.dicj.prototype_poker3d.util.MatrixHelper;

/**
 * Created by PotBa1632703 on 2018-03-20.
 */

    public class PrototypeGLRenderer implements GLSurfaceView.Renderer {
    private final Context contexte;

    private boolean jetonPresseB = false;
    //private Geometrie.Point posJetonTouch;

    private Jeton jetonP, jetonA, jetonB;
    private List<Jeton> listeJ = new ArrayList<Jeton>();
    private Jeton jetonPresse;

    private final float[] projectionMatrixP = new float[16];
    private final float[] viewMatrixP = new float[16];
    private final float[] viewProjectionMatrixP = new float[16];
    private final float[] modelMatrixP = new float[16];
    private final float[] modelViewProjectionMatrixP = new float[16];
    private final float[] invertedViewProjectionMatrixP = new float[16];

    private ColorShaderProgram colorProgram;
    private TextureShaderProgram texteProgram;

    private int texture;


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        //Couleur du fond
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        //Rayon, Hauteur, nbrPoints, positions, couleurs
        jetonP = new Jeton(0.18f, 0.035f, 18, 0f, 0.03f / 2f, 0.4f, 0f, 0f, 1f);
        jetonA = new Jeton(0.10f, 0.025f, 12, 0.1f, 0.015f / 2f, -0.2f, 1f, 1f, 0f);
        jetonB = new Jeton(0.14f, 0.030f, 32, 0.5f, 0.025f / 2f, 0.3f, 0f, 1f, 1f);

        listeJ.add(jetonA);
        listeJ.add(jetonB);
        listeJ.add(jetonP);

        texteProgram = new TextureShaderProgram(contexte);
        colorProgram = new ColorShaderProgram(contexte);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        MatrixHelper.perspectiveM(projectionMatrixP, 45, (float) width / (float) height, 1f, 10f);
        Matrix.setLookAtM(viewMatrixP, 0, 0f, 1.2f, 4.2f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        //Recoloration du fond d'écran
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.multiplyMM(viewProjectionMatrixP, 0, projectionMatrixP, 0, viewMatrixP, 0);
        Matrix.invertM(invertedViewProjectionMatrixP, 0, viewProjectionMatrixP, 0);
        //Jeton 1
        //posCentre();
        //colorProgram.utilProgram();
        //colorProgram.setUniforms(modelViewProjectionMatrixP, 1f, 0f, 0f);
        //jetonP.bindData(colorProgram);
        //jetonP.ecriture();
        //Jeton 2
        /*posObjet(posJetonTouch.px, posJetonTouch.py, posJetonTouch.pz);
        colorProgram.utilProgram();
        colorProgram.setUniforms(modelViewProjectionMatrixP, 0f, 0f, 1f);
        jetonP.bindData(colorProgram);
        jetonP.ecriture();
        //Jeton 3
        posObjet(posJetonTouch.px, posJetonTouch.py, posJetonTouch.pz);
        colorProgram.setUniforms(modelViewProjectionMatrixP, 0f, 1f, 0f);
        jetonP.ecriture();
        //Jeton 4
        posObjet(0.1f, jetonP.hauteur / 2f, -0.2f);
        colorProgram.setUniforms(modelViewProjectionMatrixP, 1f, 1f, 0f);
        jetonA.bindData(colorProgram);
        jetonA.ecriture();
        //Jeton 5
        posObjet(0.5f, jetonP.hauteur / 2f, 0.3f);
        colorProgram.setUniforms(modelViewProjectionMatrixP, 0f, 1f, 1f);
        jetonA.ecriture();*/

        colorProgram.utilProgram();

        for(Jeton j : listeJ){
            posObjet(j.pt.px, j.pt.py, j.pt.pz);
            colorProgram.setUniforms(modelViewProjectionMatrixP, j.cr, j.cg, j.cb);
            j.bindData(colorProgram);
            j.ecriture();
        }
    }

    private void posCentre(){
        Matrix.setIdentityM(modelMatrixP, 0);
        Matrix.rotateM(modelMatrixP, 0, -90f, 1f, 0f, 0f);
        Matrix.multiplyMM(modelViewProjectionMatrixP, 0, viewProjectionMatrixP, 0, modelMatrixP, 0);
    }

    private void posObjet(float x, float y, float z){
        Matrix.setIdentityM(modelMatrixP, 0);
        Matrix.translateM(modelMatrixP, 0, x, y, z);
        Matrix.multiplyMM(modelViewProjectionMatrixP, 0, viewProjectionMatrixP, 0, modelMatrixP, 0);
    }

    public static int loadShader(int type, String shaderCode){

        // vertex shader type (GLES20.GL_VERTEX_SHADER) fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


    public PrototypeGLRenderer(Context contexte){
        this.contexte = contexte;
    }

    public void handleTouchPress(float normX, float normY) {
        Geometrie.Ray rayonL =  convert2DPointRay(normX, normY);

        for (Jeton j : listeJ){
            Geometrie.Sphere sphereJetonP = new Geometrie.Sphere(new Geometrie.Point(j.pt.px, j.pt.py, j.pt.pz), j.hauteur / 2f);
            jetonPresseB = Geometrie.intersect(sphereJetonP, rayonL);
            if (jetonPresseB){
                jetonPresse = j;
                break;
            }
        }
    }

    private Geometrie.Ray convert2DPointRay(float normX, float normY) {
        final float[] WPointProche, WPointLoin;
        Geometrie.Point RPointProche, RPointLoin;

        final float[] ndcPointProche = {normX, normY, -1, 1};
        final float[] ndcPointLoin = {normX, normY, 1, 1};
        WPointProche = new float[4];
        WPointLoin = new float[4];

        Matrix.multiplyMV(WPointProche, 0, invertedViewProjectionMatrixP, 0, ndcPointProche, 0);
        Matrix.multiplyMV(WPointLoin, 0, invertedViewProjectionMatrixP, 0, ndcPointLoin, 0);

        divByW(WPointProche);
        divByW(WPointLoin);

        RPointProche = new Geometrie.Point(WPointProche[0], WPointProche[1], WPointProche[2]);
        RPointLoin = new Geometrie.Point(WPointLoin[0], WPointLoin[1], WPointLoin[2]);

        return new Geometrie.Ray(RPointProche, Geometrie.vecteurB(RPointProche, RPointLoin));
    }
    private void divByW(float[] vecteur){
        vecteur[0] /= vecteur[3];
        vecteur[1] /= vecteur[3];
        vecteur[2] /= vecteur[3];
    }

    public void handleTouchDrag(float normX, float normY) {
        if (jetonPresseB){
            Geometrie.Ray rayonL = convert2DPointRay(normX, normY);

            Geometrie.Plane plan = new Geometrie.Plane(new Geometrie.Point(0,0,0), new Geometrie.Vecteur(0,1,0));

            Geometrie.Point pointT = Geometrie.intersectP(rayonL, plan);

            jetonPresse.pt = new Geometrie.Point(pointT.px, jetonPresse.hauteur / 2f, pointT.pz);

        }
    }
}
