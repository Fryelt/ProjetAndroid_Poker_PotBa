package info.dicj.prototype_poker3d.objets;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

import info.dicj.prototype_poker3d.util.Geometrie;

/**
 * Created by PotBa1632703 on 2018-03-28.
 */


public class construcObjet {
    interface commandeEcriture {
        void ecriture();
    }
    private construcObjet(int tailleVertices){
        donneeVertex = new float[tailleVertices * nbrFloatVertex];
    }
    static class GeneratedData{
        final float[] donneeVertex;
        final List<commandeEcriture> listeEcriture;

        GeneratedData(float[] donneeVertex, List<commandeEcriture> listeEcriture){
            this.donneeVertex = donneeVertex;
            this.listeEcriture = listeEcriture;
        }
    }
    private static final int nbrFloatVertex = 3;
    private final float[] donneeVertex;
    private final List<commandeEcriture> listeEcriture = new ArrayList<commandeEcriture>();
    private int offset = 0;

    static GeneratedData creationJeton(Geometrie.Cylindre jeton, int nbrPoints){
        int taille = tailleCercleVertices(nbrPoints) + tailleCylindreOuvertVertices(nbrPoints);

        construcObjet constructeur = new construcObjet(taille);

        Geometrie.Cercle topJeton = new Geometrie.Cercle(jeton.centre.mouvementY(jeton.hauteur/ 2f), jeton.rayon);

        constructeur.annexionCercle(topJeton, nbrPoints);
        constructeur.annexionCylindreOuvert(jeton, nbrPoints);

        return constructeur.build();
    }

    static Geometrie.Cube creationCube(float longueurCote, float hauteurCote, float profondeurCote, Geometrie.Point point){
        return new Geometrie.Cube(longueurCote, hauteurCote, profondeurCote, point);
    }

    private void annexionCercle(Geometrie.Cercle cercle, int nbrPoints){
        final int debutVertex = offset / nbrFloatVertex;
        final int nbrVertices = tailleCercleVertices(nbrPoints);

        donneeVertex[offset++] = cercle.centre.px;
        donneeVertex[offset++] = cercle.centre.py;
        donneeVertex[offset++] = cercle.centre.pz;

        for (int ind = 0; ind <= nbrPoints; ind++){
            float radAngles = ((float) ind / (float) nbrPoints) * ((float) Math.PI * 2f);

            donneeVertex[offset++] = cercle.centre.px + cercle.rayon * (float)Math.cos(radAngles);
            donneeVertex[offset++] = cercle.centre.py;
            donneeVertex[offset++] = cercle.centre.pz + cercle.rayon * (float)Math.sin(radAngles);
        }

        listeEcriture.add(new commandeEcriture() {
            @Override
            public void ecriture() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, debutVertex, nbrVertices);
            }
        });
    }

    private void annexionCylindreOuvert(Geometrie.Cylindre cylindre, int nbrPoints){
        final int debutVertex = offset / nbrFloatVertex;
        final int nbrVertices = tailleCylindreOuvertVertices(nbrPoints);
        final float debutY = cylindre.centre.py - (cylindre.hauteur / 2f);
        final float finY = cylindre.centre.py + (cylindre.hauteur / 2f);

        float radAngles, posx, posz;
        for (int ind = 0; ind <= nbrPoints; ind++){
            radAngles = ((float) ind / (float) nbrPoints) * ((float) Math.PI * 2f);

            posx = cylindre.centre.px + cylindre.rayon * (float)Math.cos(radAngles);
            posz = cylindre.centre.pz + cylindre.rayon * (float)Math.sin(radAngles);

            donneeVertex[offset++] = posx;
            donneeVertex[offset++] = debutY;
            donneeVertex[offset++] = posz;

            donneeVertex[offset++] = posx;
            donneeVertex[offset++] = finY;
            donneeVertex[offset++] = posz;
        }

        listeEcriture.add(new commandeEcriture() {
            @Override
            public void ecriture() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, debutVertex, nbrVertices);
            }
        });
    }

    private void annexionSphere(){



        listeEcriture.add(new commandeEcriture() {
            @Override
            public void ecriture() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 0);
            }
        });
    }
    private void annexionRectangle(Geometrie.Rectangle rectangle){

        final int debutVertex = offset / nbrFloatVertex;
        final int nbrVertices = 6;

        float[] vertices = new float[] {
                rectangle.centre.px, rectangle.centre.py, rectangle.centre.pz,
                rectangle.centre.px - (rectangle.longueur / 2), rectangle.centre.py, rectangle.centre.pz - (rectangle.largeur / 2),
                rectangle.centre.px + (rectangle.longueur / 2), rectangle.centre.py, rectangle.centre.pz - (rectangle.largeur / 2),
                rectangle.centre.px + (rectangle.longueur / 2), rectangle.centre.py, rectangle.centre.pz + (rectangle.largeur / 2),
                rectangle.centre.px - (rectangle.longueur / 2), rectangle.centre.py, rectangle.centre.pz + (rectangle.largeur / 2),
                rectangle.centre.px - (rectangle.longueur / 2), rectangle.centre.py, rectangle.centre.pz - (rectangle.largeur / 2)};

        for (float v : vertices){
            donneeVertex[offset++] = v;
        }

        listeEcriture.add(new commandeEcriture() {
            @Override
            public void ecriture() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, debutVertex, nbrVertices);
            }
        });
    }


    private static int tailleCercleVertices(int nbrPoints){
        return 1 + (nbrPoints + 1);
    }
    private static int tailleCylindreOuvertVertices(int nbrPoints){
        return (nbrPoints + 1) * 2;
    }

    private GeneratedData build() {
        return new GeneratedData(donneeVertex, listeEcriture);
    }
}
