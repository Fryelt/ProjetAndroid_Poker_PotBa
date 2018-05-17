package info.dicj.prototype_poker3d.objets;

import android.opengl.GLES20;

import java.nio.ByteBuffer;

import info.dicj.prototype_poker3d.Constantes;
import info.dicj.prototype_poker3d.donnee.VertexArray;
import info.dicj.prototype_poker3d.programs.LightShaderProgram;
import info.dicj.prototype_poker3d.util.Couleur;
import info.dicj.prototype_poker3d.util.Geometrie;

/**
 * Created by PotBa1632703 on 2018-04-02.
 */

public class Cube extends baseObjet {
    protected static final int nbrCompPos = 3;
    protected static final int nbrTexturePos = 3;
    protected static final int stride = (nbrCompPos + nbrTexturePos) * Constantes.nbrBytesFloat;


    public final float longueurCote;
    private int compteur;

    public byte[] byteIA;

    public Cube(float longueurCote, Geometrie.Point point, Couleur couleur){
        definitionCube(longueurCote, longueurCote, longueurCote, point);

        this.longueurCote = longueurCote;
        this.couleur = couleur;

        donneeCouleur = new float[donneePos.length];

        compteur = 0;
        //Affectation des données de couleur
        for (int indice = 0; indice < (donneeCouleur.length / 3); indice++){
            donneeCouleur[compteur++] = couleur.cr;
            donneeCouleur[compteur++] = couleur.cg;
            donneeCouleur[compteur++] = couleur.cb;
        }
    }

    public Cube() {
        longueurCote = 0;
    }

    public void definitionCube(float longueur, float hauteur, float profondeur, Geometrie.Point point){
        //Définition d'un cube selon les paramètres
        Geometrie.Cube cube = construcObjet.creationCube(longueur, hauteur, profondeur, point);

        float cubeX1, cubeX2, cubeY1, cubeY2, cubeZ1, cubeZ2;
        //Création tableaux de données
        donneeNormal = new float[3*6*6];
        donneeCoordText = new float[12*6];

        //Affectation des variables pour déterminer les positions des points/vertices
        cubeX1 = cube.centre.px + (cube.longueur / 2);
        cubeX2 = cube.centre.px - (cube.longueur / 2);
        cubeY1 = cube.centre.py + (cube.hauteur / 2);
        cubeY2 = cube.centre.py - (cube.hauteur / 2);
        cubeZ1 = cube.centre.pz + (cube.profondeur / 2);
        cubeZ2 = cube.centre.pz - (cube.profondeur / 2);

        //1 = plus grand, 2 = plus petit
        Geometrie.Point[] listeVertices = new Geometrie.Point[]{
                new Geometrie.Point(cubeX2, cubeY1, cubeZ1),
                new Geometrie.Point(cubeX1, cubeY1, cubeZ1),
                new Geometrie.Point(cubeX2, cubeY2, cubeZ1),
                new Geometrie.Point(cubeX1, cubeY2, cubeZ1),
                new Geometrie.Point(cubeX2, cubeY1, cubeZ2),
                new Geometrie.Point(cubeX1, cubeY1, cubeZ2),
                new Geometrie.Point(cubeX2, cubeY2, cubeZ2),
                new Geometrie.Point(cubeX1, cubeY2, cubeZ2),
        };

        float[] listeVA = new float[listeVertices.length * 3];

        compteur = 0;
        for (Geometrie.Point pt : listeVertices){
            listeVA[compteur++] = pt.px;
            listeVA[compteur++] = pt.py;
            listeVA[compteur++] = pt.pz;
        }
        donneePos = new float[]{
                // Face frontale

                // -1.0f, 1.0f, 1.0f,
                cubeX2, cubeY1, cubeZ1,
                //-1.0f, -1.0f, 1.0f,
                cubeX2, cubeY2, cubeZ1,
                //1.0f, 1.0f, 1.0f,
                cubeX1, cubeY1, cubeZ1,
                //-1.0f, -1.0f, 1.0f,
                cubeX2, cubeY2, cubeZ1,
                //1.0f, -1.0f, 1.0f,
                cubeX1, cubeY2, cubeZ1,
                //1.0f, 1.0f, 1.0f,
                cubeX1, cubeY1, cubeZ1,

                // Face de droite

                //1.0f, 1.0f, 1.0f,
                cubeX1, cubeY1, cubeZ1,
                //1.0f, -1.0f, 1.0f,
                cubeX1, cubeY2, cubeZ1,
                //1.0f, 1.0f, -1.0f,
                cubeX1, cubeY1, cubeZ2,
                //1.0f, -1.0f, 1.0f,
                cubeX1, cubeY2, cubeZ1,
                //1.0f, -1.0f, -1.0f,
                cubeX1, cubeY2, cubeZ2,
                //1.0f, 1.0f, -1.0f,
                cubeX1, cubeY1, cubeZ2,

                // Face d'en arrière

                //1.0f, 1.0f, -1.0f,
                cubeX1, cubeY1, cubeZ2,
                //1.0f, -1.0f, -1.0f,
                cubeX1, cubeY2, cubeZ2,
                //-1.0f, 1.0f, -1.0f,
                cubeX2, cubeY1, cubeZ2,
                //1.0f, -1.0f, -1.0f,
                cubeX1, cubeY2, cubeZ2,
                //-1.0f, -1.0f, -1.0f,
                cubeX2, cubeY2, cubeZ2,
                //-1.0f, 1.0f, -1.0f,
                cubeX2, cubeY1, cubeZ2,

                // Face de gauche

                //-1.0f, 1.0f, -1.0f,
                cubeX2, cubeY1, cubeZ2,
                //-1.0f, -1.0f, -1.0f,
                cubeX2, cubeY2, cubeZ2,
                //-1.0f, 1.0f, 1.0f,
                cubeX2, cubeY1, cubeZ1,
                //-1.0f, -1.0f, -1.0f,
                cubeX2, cubeY2, cubeZ2,
                //-1.0f, -1.0f, 1.0f,
                cubeX2, cubeY2, cubeZ1,
                //-1.0f, 1.0f, 1.0f,
                cubeX2, cubeY1, cubeZ1,

                // Face du dessus

                //-1.0f, 1.0f, -1.0f,
                cubeX2, cubeY1, cubeZ2,
                //-1.0f, 1.0f, 1.0f,
                cubeX2, cubeY1, cubeZ1,
                //1.0f, 1.0f, -1.0f,
                cubeX1, cubeY1, cubeZ2,
                //-1.0f, 1.0f, 1.0f,
                cubeX2, cubeY1, cubeZ1,
                //1.0f, 1.0f, 1.0f,
                cubeX1, cubeY1, cubeZ1,
                //1.0f, 1.0f, -1.0f,
                cubeX1, cubeY1, cubeZ2,

                // Face d'en dessous

                //1.0f, -1.0f, -1.0f,
                cubeX1, cubeY2, cubeZ2,
                //1.0f, -1.0f, 1.0f,
                cubeX1, cubeY2, cubeZ1,
                //-1.0f, -1.0f, -1.0f,
                cubeX2, cubeY2, cubeZ2,
                //1.0f, -1.0f, 1.0f,
                cubeX1, cubeY2, cubeZ1,
                //-1.0f, -1.0f, 1.0f,
                cubeX2, cubeY2, cubeZ1,
                //-1.0f, -1.0f, -1.0f,
                cubeX2, cubeY2, cubeZ2
        };

        compteur = 0;
        //Affectation des coordonnées pour la texture
        for (int i = 0; i < 6; i++){
            donneeCoordText[compteur++] = cube.centre.px;
            donneeCoordText[compteur++] = cube.centre.py;

            donneeCoordText[compteur++] = cube.centre.px;
            donneeCoordText[compteur++] = cubeY1;

            donneeCoordText[compteur++] = cubeX1;
            donneeCoordText[compteur++] = cube.centre.py;

            donneeCoordText[compteur++] = cube.centre.px;
            donneeCoordText[compteur++] = cubeY1;

            donneeCoordText[compteur++] = cubeX1;
            donneeCoordText[compteur++] = cubeY1;

            donneeCoordText[compteur++] = cubeX1;
            donneeCoordText[compteur++] = cube.centre.py;
        }

        compteur = 0;
        //Affectation des données normales pour le calcul des couleurs
        for (int i = 0; i < 6; i++){
            donneeNormal[compteur++] = cube.centre.px;
            donneeNormal[compteur++] = cube.centre.py;
            donneeNormal[compteur++] = cubeZ1;
        }
        for (int i = 0; i < 6; i++){
            donneeNormal[compteur++] = cubeX1;
            donneeNormal[compteur++] = cube.centre.py;
            donneeNormal[compteur++] = cube.centre.pz;
        }
        for (int i = 0; i < 6; i++){
            donneeNormal[compteur++] = cube.centre.px;
            donneeNormal[compteur++] = cube.centre.py;
            donneeNormal[compteur++] = cubeZ2;
        }
        for (int i = 0; i < 6; i++){
            donneeNormal[compteur++] = cubeX2;
            donneeNormal[compteur++] = cube.centre.py;
            donneeNormal[compteur++] = cube.centre.pz;
        }
        for (int i = 0; i < 6; i++){
            donneeNormal[compteur++] = cube.centre.px;
            donneeNormal[compteur++] = cubeY1;
            donneeNormal[compteur++] = cube.centre.pz;
        }
        for (int i = 0; i < 6; i++){
            donneeNormal[compteur++] = cube.centre.px;
            donneeNormal[compteur++] = cubeY2;
            donneeNormal[compteur++] = cube.centre.pz;
        }

        //Données pour les vertices (Avant l'introduction des textures)
        vertexArray = new VertexArray(listeVA);

        //Tableau de byte pour l'ordre des vertices
        byteIA = new byte[]{
                // Devant
                1, 3, 0, 0, 3, 2,
                // Derrière
                4, 6, 5, 5, 6, 7,
                // Gauche
                0, 2, 4, 4, 2, 6,
                // Droite
                5, 7, 1, 1, 7, 3,
                // Dessus
                5, 1, 4, 4, 1, 0,
                // Fond
                6, 2, 7, 7, 2, 3
        };

        // À noter que ce qui concerne l'indexArray n'importait qu'avant l'implémentation des textures
        indexArray = ByteBuffer.allocateDirect(6*6)
                .put(byteIA);
        indexArray.position(0);

        position = point;

    }

    @Override
    public void ecriture(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        //La commande avant l'introduction des textures => GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_BYTE, indexArray);
    }

    @Override
    public void setVertex(LightShaderProgram programTexture){
        positionArray.setVertexAttribPointer(0, programTexture.getPositionLocation(), Constantes.PositionDataSize, 0);
        couleurArray.setVertexAttribPointer(0, programTexture.getColorLocation(), Constantes.ColorDataSize, 0);
        normalArray.setVertexAttribPointer(0, programTexture.getNormalLocation(), Constantes.NormalDataSize, 0);
        textureArray.setVertexAttribPointer(0, programTexture.getTexCoordinate(), Constantes.TextureCoordinateDataSize, 0);
    }
}
