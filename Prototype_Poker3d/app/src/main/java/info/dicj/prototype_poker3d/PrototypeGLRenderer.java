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

import info.dicj.prototype_poker3d.objets.Cube2;
import info.dicj.prototype_poker3d.objets.Cube;
import info.dicj.prototype_poker3d.objets.baseObjet;
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

    //Variable d'objet pour les mouvements
    private boolean ObjetPresseB = false;
    //
    private int texture;

    //Objets
    //private Jeton jetonP, jetonA, jetonB;
    private Cube cubeA;
    private Cube2 cubeB;
    private List<baseObjet> listeO = new ArrayList<baseObjet>();
    private baseObjet objetPresse;

    //Matrices de projection, de vue et de modèle
    private final float[] projectionMatrixP = new float[16];
    private final float[] viewMatrixP = new float[16];
    private final float[] viewProjectionMatrixP = new float[16];
    private final float[] modelMatrixP = new float[16];
    private final float[] modelViewProjectionMatrixP = new float[16];
    private final float[] invertedViewProjectionMatrixP = new float[16];

    //Matrices des lumières
    private float[] modelLumiereMatrixP = new float[16];
    private final float[] mLightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] mLightPosInWorldSpace = new float[4];
    private final float[] mLightPosInEyeSpace = new float[4];

    //Buffers du cube1
    private FloatBuffer mCubePositions;
    private FloatBuffer mCubeColors;
    private FloatBuffer mCubeNormals;
    private FloatBuffer mCubeTextureCoordinates;

    //Buffers du cube2
    private FloatBuffer mCubePositions2;
    private FloatBuffer mCubeColors2;
    private FloatBuffer mCubeNormals2;
    private FloatBuffer mCubeTextureCoordinates2;

    //Programmes
    private LightDotShaderProgram lumierePointProgram;
    private LightShaderProgram textureProgram;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        //Couleur du fond + Nettoyage
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        //Permettre le cull_face pour les textures
        GLES20.glEnable(GLES20.GL_CULL_FACE);

        //Création objets
        cubeA = new Cube(1.0f, new Geometrie.Point(0.3f, 0.2f, 0.1f), new Couleur(0.5f, 1f, 0.5f));
        cubeB = new Cube2(0.3f, 0.7f, new Geometrie.Point(0.0f, 0.2f, 0f), new Couleur(0.3f, 0.9f, 0.3f));

        //Ajout objets à la liste
        listeO.add(cubeA);
        listeO.add(cubeB);

        //Création programmes
        textureProgram = new LightShaderProgram(contexte);
        lumierePointProgram = new LightDotShaderProgram(contexte);

        //Instance des buffers pour le cube
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

        //Instance des buffers pour l'autre cube
        mCubePositions2 = ByteBuffer.allocateDirect(cubeB.donneePos.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubePositions2.put(cubeB.donneePos).position(0);

        mCubeColors2 = ByteBuffer.allocateDirect(cubeB.donneeCouleur.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeColors2.put(cubeB.donneeCouleur).position(0);

        mCubeNormals2 = ByteBuffer.allocateDirect(cubeB.donneeNormal.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeNormals2.put(cubeB.donneeNormal).position(0);

        mCubeTextureCoordinates2 = ByteBuffer.allocateDirect(cubeB.donneeCoordText.length * Constantes.nbrBytesFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeTextureCoordinates2.put(cubeB.donneeCoordText).position(0);

        //Instance de la texture
        texture = TextureHelper.loadTexture(contexte, R.drawable.test);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        //Association des limites pour la nouvelle surface
        GLES20.glViewport(0,0,width,height);

        //Instance des matrices de vision
        MatrixHelper.perspectiveM(projectionMatrixP, 45, (float) width / (float) height, 1f, 10f);
        Matrix.setLookAtM(viewMatrixP, 0, 0f, 1.2f, 3.2f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        //Recoloration du fond d'écran
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //Variables pour la rotation du point de lumière
        long time = SystemClock.uptimeMillis() % 10000L;
        float degre = (360.0f / 10000.0f) * ((int) time);

        //Utilisation du programme de texture
        textureProgram.utilProgram();
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glUniform1i(textureProgram.getTexture(), 0);

        //Affecation du point lumineux
        Matrix.setIdentityM(modelLumiereMatrixP, 0);
        Matrix.translateM(modelLumiereMatrixP, 0, 0.0f, 0.0f, -5.0f);
        Matrix.rotateM(modelLumiereMatrixP, 0, degre, 0.0f, 1.0f, 0.0f);
        Matrix.translateM(modelLumiereMatrixP, 0, 0.0f, 0.0f, 2.0f);

        //Multiplication des matrices de positions de la lumière
        Matrix.multiplyMV(mLightPosInWorldSpace, 0, modelLumiereMatrixP, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, viewMatrixP, 0, mLightPosInWorldSpace, 0);

        //Multiplication de la matrice pour les évènements ontouch
        Matrix.multiplyMM(viewProjectionMatrixP, 0, projectionMatrixP, 0, viewMatrixP, 0);
        Matrix.invertM(invertedViewProjectionMatrixP, 0, viewProjectionMatrixP, 0);

        //Écriture des objets dans la liste
        for(baseObjet j : listeO){
            posObjet(j.position);
            lumiereObjet(j);
        }

        //Écriture point lumineux
        ajoutLumiere();
    }

    public void lumiereObjet(baseObjet j) {
        //Utilisation du programe de texture
        textureProgram.utilProgram();

        if (j.getClass().getSimpleName().equals("Cube")){
            //Affectation des données selon les buffers
            mCubePositions.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getPositionLocation(), Constantes.PositionDataSize, GLES20.GL_FLOAT, false, 0, mCubePositions);
            GLES20.glEnableVertexAttribArray(textureProgram.getPositionLocation());

            mCubeColors.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getColorLocation(), Constantes.ColorDataSize, GLES20.GL_FLOAT, false, 0, mCubeColors);
            GLES20.glEnableVertexAttribArray(textureProgram.getColorLocation());

            mCubeNormals.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getNormalLocation(), Constantes.NormalDataSize, GLES20.GL_FLOAT, false, 0, mCubeNormals);
            GLES20.glEnableVertexAttribArray(textureProgram.getNormalLocation());

            mCubeTextureCoordinates.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getTexCoordinate(), Constantes.TextureCoordinateDataSize, GLES20.GL_FLOAT, false, 0, mCubeTextureCoordinates);
            GLES20.glEnableVertexAttribArray(textureProgram.getTexCoordinate());
        }

        if (j.getClass().getSimpleName().equals("Cube2")){
            //Affectation des données selon les buffers
            mCubePositions2.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getPositionLocation(), Constantes.PositionDataSize, GLES20.GL_FLOAT, false, 0, mCubePositions2);
            GLES20.glEnableVertexAttribArray(textureProgram.getPositionLocation());

            mCubeColors2.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getColorLocation(), Constantes.ColorDataSize, GLES20.GL_FLOAT, false, 0, mCubeColors2);
            GLES20.glEnableVertexAttribArray(textureProgram.getColorLocation());

            mCubeNormals2.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getNormalLocation(), Constantes.NormalDataSize, GLES20.GL_FLOAT, false, 0, mCubeNormals2);
            GLES20.glEnableVertexAttribArray(textureProgram.getNormalLocation());

            mCubeTextureCoordinates2.position(0);
            GLES20.glVertexAttribPointer(textureProgram.getTexCoordinate(), Constantes.TextureCoordinateDataSize, GLES20.GL_FLOAT, false, 0, mCubeTextureCoordinates2);
            GLES20.glEnableVertexAttribArray(textureProgram.getTexCoordinate());
        }

        //Multiplications pour la matrice MVP
        Matrix.multiplyMM(modelViewProjectionMatrixP, 0, viewMatrixP, 0, modelMatrixP, 0);
        GLES20.glUniformMatrix4fv(textureProgram.getMVMatrixLocation(), 1, false, modelViewProjectionMatrixP, 0);
        Matrix.multiplyMM(modelViewProjectionMatrixP, 0, projectionMatrixP, 0, modelViewProjectionMatrixP, 0);
        GLES20.glUniformMatrix4fv(textureProgram.getMVPMatrixLocation(), 1, false, modelViewProjectionMatrixP, 0);

        //Uniformisation de la lumière
        GLES20.glUniform3f(textureProgram.getLightPosition(), mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);

        //Écriture de l'objet
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

    public void ajoutCube2(){
        //Rayon, Hauteur, nbrPoints, positions, couleurs
        Couleur cl = new Couleur(1f, 0f, 1f);
        listeO.add(new Cube2(cubeB.longueur, cubeB.hauteur, cubeB.position, cl));
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
        //Position de l'objet dans la matrice modèle
        Matrix.setIdentityM(modelMatrixP, 0);
        Matrix.translateM(modelMatrixP, 0, pos.px, pos.py, pos.pz);
        //Multiplication dans la matrice MVP
        Matrix.multiplyMM(modelViewProjectionMatrixP, 0, viewProjectionMatrixP, 0, modelMatrixP, 0);
    }

    public PrototypeGLRenderer(Context contexte){
        this.contexte = contexte;
    }

    public void handleTouchPress(float normX, float normY) {
        //On transforme les coordonnées x/y que l'utilisateur envoie en
        Geometrie.Ray rayonL =  convert2DPointRay(normX, normY);

        for (baseObjet j : listeO){
            //On crée une sphère autour de l'objet afin
            Geometrie.Sphere sphereJetonP = new Geometrie.Sphere(j.position, (j.position.py / 2f)*3);
            //On vérifie si l'objet est pressé en
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

        //Multiplication de matrices vers les tableaux des point W
        Matrix.multiplyMV(WPointProche, 0, invertedViewProjectionMatrixP, 0, ndcPointProche, 0);
        Matrix.multiplyMV(WPointLoin, 0, invertedViewProjectionMatrixP, 0, ndcPointLoin, 0);

        //Division appropriée des vecteurs
        divByW(WPointProche);
        divByW(WPointLoin);

        //Détermination des points basé sur les tableaux W
        RPointProche = new Geometrie.Point(WPointProche[0], WPointProche[1], WPointProche[2]);
        RPointLoin = new Geometrie.Point(WPointLoin[0], WPointLoin[1], WPointLoin[2]);

        //Retour d'un rayon basé sur les points R
        return new Geometrie.Ray(RPointProche, Geometrie.vecteurB(RPointProche, RPointLoin));
    }
    private void divByW(float[] vecteur){
        vecteur[0] /= vecteur[3];
        vecteur[1] /= vecteur[3];
        vecteur[2] /= vecteur[3];
    }

    public void handleTouchDrag(float normX, float normY) {
        if (ObjetPresseB){
            //Création d'un rayon à partir des coordonnées X et Y données par l'utilisateur
            Geometrie.Ray rayonL = convert2DPointRay(normX, normY);

            //Création d'un plan horizontal de l'environnement openGL
            Geometrie.Plane plan = new Geometrie.Plane(new Geometrie.Point(0,0,0), new Geometrie.Vecteur(0,1,0));

            //Détermination du point selon l'intersection entre le plan et le rayon
            Geometrie.Point pointT = Geometrie.intersectP(rayonL, plan);

            //Affectation de la position de l'objet pressé
            objetPresse.position = new Geometrie.Point(pointT.px, objetPresse.position.py, pointT.pz);
        }
    }
}
