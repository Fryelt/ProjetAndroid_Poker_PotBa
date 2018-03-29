package info.dicj.prototype_poker3d;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PotBa1632703 on 2018-03-28.
 */

public class consObjet {
    private static final int nbrFloatVertex = 3;
    private final float[] donneeVertex;
    //private final List<commandeEcriture> listeEcriture = new ArrayList<commandeEcriture>();
    private int offset = 0;
    static class GeneratedData{
        //final float[] donneeVertex;
        //final List<commandeEcriture> listeEcriture;

        //GeneratedData(float[] donneeVertex, List<commandeEcriture> listeEcriture){
        //    this.donneeVertex = donneeVertex;
        //    this.listeEcriture = listeEcriture;
        //}
    }
    static GeneratedData creationJeton(Geometrie.Cylindre jeton, int nbrPoints){
        int taille = tailleCercleVertices(nbrPoints) + tailleCylindreOuvertVertices(nbrPoints);

        consObjet constructeur = new consObjet(taille);

        Geometrie.Cercle topJeton = new Geometrie.Cercle(jeton.centre.traductionY(jeton.hauteur/ 2f), jeton.rayon);

        //constructeur.annexionCercle();
        //constructeur.annexionCylindreOuvert();

        // P.158 !!!

        //return constructeur.build();
        return null;
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

   // private GeneratedData build() {
        //return new GeneratedData(donneeVertex, listeEcriture);
    //}

}
