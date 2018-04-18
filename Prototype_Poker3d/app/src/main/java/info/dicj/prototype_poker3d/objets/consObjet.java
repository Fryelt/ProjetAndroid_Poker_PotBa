package info.dicj.prototype_poker3d.objets;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

import info.dicj.prototype_poker3d.util.Geometrie;

/**
 * Created by PotBa1632703 on 2018-03-28.
 */


public class consObjet {
    interface commandeEcriture {
        void ecriture();
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

        consObjet constructeur = new consObjet(taille);

        Geometrie.Cercle topJeton = new Geometrie.Cercle(jeton.centre.mouvementY(jeton.hauteur/ 2f), jeton.rayon);

        constructeur.annexionCercle(topJeton, nbrPoints);
        constructeur.annexionCylindreOuvert(jeton, nbrPoints);

        return constructeur.build();
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

    private consObjet(int tailleVertices){
        donneeVertex = new float[tailleVertices * nbrFloatVertex];
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
