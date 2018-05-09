package info.dicj.prototype_poker3d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import info.dicj.prototype_poker3d.objets.Carte;
import info.dicj.prototype_poker3d.objets.Cube;
import info.dicj.prototype_poker3d.objets.Jeton;
import info.dicj.prototype_poker3d.objets.baseObjet;
import info.dicj.prototype_poker3d.programs.ColorShaderProgram;
import info.dicj.prototype_poker3d.programs.LightDotShaderProgram;
import info.dicj.prototype_poker3d.programs.LightShaderProgram;
import info.dicj.prototype_poker3d.util.Couleur;
import info.dicj.prototype_poker3d.util.Geometrie;
import info.dicj.prototype_poker3d.util.MatrixHelper;
import info.dicj.prototype_poker3d.util.TextureHelper;

/**
 * Created by PotBa1632703 on 2018-03-20.
 */

    public class PrototypeGLRenderer implements GLSurfaceView.Renderer {
    private final Context contexte;

    private boolean ObjetPresseB = false;
    //private Geometrie.Point posJetonTouch;

    private Jeton jetonP, jetonA, jetonB;
    private Cube cubeA;
    private Carte carteA;
    private List<baseObjet> listeO = new ArrayList<baseObjet>();
    private baseObjet objetPresse;

    private final float[] projectionMatrixP = new float[16];
    private final float[] viewMatrixP = new float[16];
    private final float[] viewProjectionMatrixP = new float[16];
    private final float[] modelMatrixP = new float[16];
    private final float[] modelViewProjectionMatrixP = new float[16];
    private final float[] invertedViewProjectionMatrixP = new float[16];

    private float[] modelLumiereMatrixP = new float[16];

    private final float[] mLightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] mLightPosInWorldSpace = new float[4];
    private final float[] mLightPosInEyeSpace = new float[4];

    private FloatBuffer mCubePositions;
    private FloatBuffer mCubeColors;
    private FloatBuffer mCubeNormals;
    private FloatBuffer mCubeTextureCoordinates;

    private FloatBuffer mCubePositions2;
    private FloatBuffer mCubeColors2;
    private FloatBuffer mCubeNormals2;
    private FloatBuffer mCubeTextureCoordinates2;

    //private ColorShaderProgram colorProgram;
    //private TextureShaderProgram textureProgram;
    private LightDotShaderProgram lumierePointProgram;
    private LightShaderProgram textureProgram;

    private int texture;


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        //Couleur du fond
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        //Rayon, Hauteur, nbrPoints, position, couleur

        //jetonP = new Jeton(0.18f, 0.035f, 18, new Geometrie.Point(0f, 0.03f / 2f, 0.4f), new Couleur(0f, 0f, 1f));
        //jetonA = new Jeton(0.10f, 0.025f, 12, new Geometrie.Point(0.1f, 0.015f / 2f, -0.2f), new Couleur(1f, 1f, 0f));
        //jetonB = new Jeton(0.14f, 0.030f, 32, new Geometrie.Point(0.5f, 0.025f / 2f, 0.3f), new Couleur(0f, 1f, 1f));

        cubeA = new Cube(1.0f, new Geometrie.Point(0.3f, 0.2f, 0.1f), new Couleur(0.5f, 1f, 0.5f));

        carteA = new Carte(0.3f, 0.7f, new Geometrie.Point(0.0f, 0.2f, 0f), new Couleur(0.3f, 0.9f, 0.3f));

        //listeO.add(jetonA);
        //listeO.add(jetonB);
        //listeO.add(jetonP);
        listeO.add(cubeA);
        listeO.add(carteA);

        //textureProgram = new TextureShaderProgram(contexte);
        //colorProgram = new ColorShaderProgram(contexte);
        textureProgram = new LightShaderProgram(contexte);
        lumierePointProgram = new LightDotShaderProgram(contexte);
        //textureProgram = new TextureShaderProgram(contexte);

        mCubePositions = ByteBuffer.allocateDirect(cubeA.donneePos.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubePositions.put(cubeA.donneePos).position(0);

        mCubeColors = ByteBuffer.allocateDirect(cubeA.donneeCouleur.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeColors.put(cubeA.donneeCouleur).position(0);

        mCubeNormals = ByteBuffer.allocateDirect(cubeA.donneeNormal.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeNormals.put(cubeA.donneeNormal).position(0);

        mCubeTextureCoordinates = ByteBuffer.allocateDirect(cubeA.donneeCoordText.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeTextureCoordinates.put(cubeA.donneeCoordText).position(0);

        ///

        mCubePositions2 = ByteBuffer.allocateDirect(carteA.donneePos.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubePositions2.put(carteA.donneePos).position(0);

        mCubeColors2 = ByteBuffer.allocateDirect(carteA.donneeCouleur.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeColors2.put(carteA.donneeCouleur).position(0);

        mCubeNormals2 = ByteBuffer.allocateDirect(carteA.donneeNormal.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeNormals2.put(carteA.donneeNormal).position(0);

        mCubeTextureCoordinates2 = ByteBuffer.allocateDirect(carteA.donneeCoordText.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeTextureCoordinates2.put(carteA.donneeCoordText).position(0);

        texture = TextureHelper.loadTexture(contexte, R.drawable.test);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        MatrixHelper.perspectiveM(projectionMatrixP, 45, (float) width / (float) height, 1f, 10f);
        Matrix.setLookAtM(viewMatrixP, 0, 0f, 1.2f, 3.2f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        //Recoloration du fond d'écran
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        long time = SystemClock.uptimeMillis() % 10000L;
        float degre = (360.0f / 10000.0f) * ((int) time);



        textureProgram.utilProgram();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);

        GLES20.glUniform1i(textureProgram.getTexture(), 0);

        Matrix.setIdentityM(modelLumiereMatrixP, 0);
        Matrix.translateM(modelLumiereMatrixP, 0, 0.0f, 0.0f, -5.0f);
        Matrix.rotateM(modelLumiereMatrixP, 0, degre, 0.0f, 1.0f, 0.0f);
        Matrix.translateM(modelLumiereMatrixP, 0, 0.0f, 0.0f, 2.0f);

        Matrix.multiplyMV(mLightPosInWorldSpace, 0, modelLumiereMatrixP, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, viewMatrixP, 0, mLightPosInWorldSpace, 0);

        Matrix.multiplyMM(viewProjectionMatrixP, 0, projectionMatrixP, 0, viewMatrixP, 0);
        Matrix.invertM(invertedViewProjectionMatrixP, 0, viewProjectionMatrixP, 0);



        for(baseObjet j : listeO){
            //colorProgram.utilProgram();
            posObjet(j.position);
            //colorProgram.setUniforms(modelViewProjectionMatrixP, j.couleur);
            //j.bindData(colorProgram);
            lumiereObjet(j);
            //j.ecriture();
        }

        ajoutLumiere();
    }

    public void lumiereObjet(baseObjet j) {
        textureProgram.utilProgram();

        //j.setVertex(textureProgram);

        if (j.getClass().getSimpleName().equals("Cube")){
            mCubePositions.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getPositionLocation(), Constantes.mPositionDataSize, GLES20.GL_FLOAT, false, 0, mCubePositions);
            GLES20.glEnableVertexAttribArray(textureProgram.getPositionLocation());

            mCubeColors.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getColorLocation(), Constantes.mColorDataSize, GLES20.GL_FLOAT, false, 0, mCubeColors);
            GLES20.glEnableVertexAttribArray(textureProgram.getColorLocation());

            mCubeNormals.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getNormalLocation(), Constantes.mNormalDataSize, GLES20.GL_FLOAT, false, 0, mCubeNormals);
            GLES20.glEnableVertexAttribArray(textureProgram.getNormalLocation());

            mCubeTextureCoordinates.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getTexCoordinate(), Constantes.mTextureCoordinateDataSize, GLES20.GL_FLOAT, false, 0, mCubeTextureCoordinates);
            GLES20.glEnableVertexAttribArray(textureProgram.getTexCoordinate());
        }
        if (j.getClass().getSimpleName().equals("Carte")){
            mCubePositions2.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getPositionLocation(), Constantes.mPositionDataSize, GLES20.GL_FLOAT, false, 0, mCubePositions2);
            GLES20.glEnableVertexAttribArray(textureProgram.getPositionLocation());

            mCubeColors2.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getColorLocation(), Constantes.mColorDataSize, GLES20.GL_FLOAT, false, 0, mCubeColors2);
            GLES20.glEnableVertexAttribArray(textureProgram.getColorLocation());

            mCubeNormals2.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getNormalLocation(), Constantes.mNormalDataSize, GLES20.GL_FLOAT, false, 0, mCubeNormals2);
            GLES20.glEnableVertexAttribArray(textureProgram.getNormalLocation());

            mCubeTextureCoordinates2.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getTexCoordinate(), Constantes.mTextureCoordinateDataSize, GLES20.GL_FLOAT, false, 0, mCubeTextureCoordinates2);
            GLES20.glEnableVertexAttribArray(textureProgram.getTexCoordinate());
        }


        Matrix.multiplyMM(modelViewProjectionMatrixP, 0, viewMatrixP, 0, modelMatrixP, 0);

        GLES20.glUniformMatrix4fv(textureProgram.getMVMatrixLocation(), 1, false, modelViewProjectionMatrixP, 0);

        Matrix.multiplyMM(modelViewProjectionMatrixP, 0, projectionMatrixP, 0, modelViewProjectionMatrixP, 0);

        GLES20.glUniformMatrix4fv(textureProgram.getMVPMatrixLocation(), 1, false, modelViewProjectionMatrixP, 0);

        GLES20.glUniform3f(textureProgram.getLightPosition(), mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);

        j.ecriture();
    }

    public void ajoutLumiere() {
        lumierePointProgram.utilProgram();

        GLES20.glVertexAttrib3f(lumierePointProgram.getPosAttribLocation(), mLightPosInModelSpace[0], mLightPosInModelSpace[1], mLightPosInModelSpace[2]);
        GLES20.glDisableVertexAttribArray(lumierePointProgram.getPosAttribLocation());

        Matrix.multiplyMM(modelViewProjectionMatrixP, 0, viewMatrixP, 0, modelLumiereMatrixP, 0);
        Matrix.multiplyMM(modelViewProjectionMatrixP, 0, projectionMatrixP, 0, modelViewProjectionMatrixP, 0);
        GLES20.glUniformMatrix4fv(lumierePointProgram.getMVPMatrixLocation(), 1, false, modelViewProjectionMatrixP, 0);

        lumierePointProgram.setUniforms(modelViewProjectionMatrixP, lumierePointProgram.getMVPMatrixLocation());

        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
    }

    public void ajoutCarte(){
        //Rayon, Hauteur, nbrPoints, positions, couleurs
        Couleur cl = new Couleur(1f, 0f, 1f);
        listeO.add(new Carte(carteA.longueur, carteA.hauteur, carteA.position, cl));
    }

    public void ajoutCube(){
        //Rayon, Hauteur, nbrPoints, positions, couleurs
        Couleur cl = new Couleur(0.6f, 0f, 0.2f);
        listeO.add(new Cube(cubeA.longueurCote, cubeA.position, cl));
    }

    public void supprimListe(){
        listeO.clear();
    }

    private void posObjet(Geometrie.Point pos){
        Matrix.setIdentityM(modelMatrixP, 0);
        Matrix.translateM(modelMatrixP, 0, pos.px, pos.py, pos.pz);
        //Matrix.multiplyMM(modelViewProjectionMatrixP, 0, viewProjectionMatrixP, 0, modelMatrixP, 0);
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

        for (baseObjet j : listeO){
            Geometrie.Sphere sphereJetonP = new Geometrie.Sphere(j.position, (j.position.py / 2f)*3);
            ObjetPresseB = Geometrie.intersect(sphereJetonP, rayonL);
            if (ObjetPresseB){
                objetPresse = j;
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
        if (ObjetPresseB){
            Geometrie.Ray rayonL = convert2DPointRay(normX, normY);

            Geometrie.Plane plan = new Geometrie.Plane(new Geometrie.Point(0,0,0), new Geometrie.Vecteur(0,1,0));

            Geometrie.Point pointT = Geometrie.intersectP(rayonL, plan);

            objetPresse.position = new Geometrie.Point(pointT.px, objetPresse.position.py, pointT.pz);
        }
    }
}
