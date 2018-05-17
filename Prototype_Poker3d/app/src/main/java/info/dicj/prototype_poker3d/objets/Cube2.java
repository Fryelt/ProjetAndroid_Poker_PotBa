package info.dicj.prototype_poker3d.objets;

import java.nio.ByteBuffer;
import java.util.List;

import info.dicj.prototype_poker3d.donnee.VertexArray;
import info.dicj.prototype_poker3d.util.Couleur;
import info.dicj.prototype_poker3d.util.Geometrie;

/**
 * Created by PotBa1632703 on 2018-04-24.
 */

public class Cube2 extends Cube {
    public final float longueur, hauteur, profondeur;

    public Cube2(float longueur, float hauteur, Geometrie.Point point, Couleur couleur){
        super.definitionCube(longueur, hauteur, 0.5f, point);

        this.longueur = longueur;
        this.hauteur = hauteur;
        this.profondeur = 0.5f;

        int compteur = 0;

        //Affectation des données de couleur
        donneeCouleur = new float[donneePos.length];
        for (int indice = 0; indice < (donneeCouleur.length / 3); indice++){
            donneeCouleur[compteur++] = couleur.cr;
            donneeCouleur[compteur++] = couleur.cg;
            donneeCouleur[compteur++] = couleur.cb;
        }
        this.couleur = couleur;
    }
}
