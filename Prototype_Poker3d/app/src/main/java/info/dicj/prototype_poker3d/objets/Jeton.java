package info.dicj.prototype_poker3d.objets;

import java.util.List;

import info.dicj.prototype_poker3d.donnee.VertexArray;
import info.dicj.prototype_poker3d.programs.ColorShaderProgram;
import info.dicj.prototype_poker3d.util.Couleur;
import info.dicj.prototype_poker3d.util.Geometrie;

/**
 * Created by PotBa1632703 on 2018-04-02.
 */

public class Jeton extends baseObjet {
    private static final int nbrCompPos = 3;
    public final float rayon, hauteur;

    private final VertexArray vertexArray;
    private final List<construcObjet.commandeEcriture> ListeEcriture;

    public Jeton(float rayon, float hauteur, int nbrPts, Geometrie.Point point, Couleur couleur){
        construcObjet.GeneratedData generatedData = construcObjet.creationJeton(new Geometrie.Cylindre(new Geometrie.Point(0f, 0f, 0f), rayon, hauteur), nbrPts);

        this.rayon = rayon;
        this.hauteur = hauteur;

        position = point;
        this.couleur = couleur;

        vertexArray = new VertexArray(generatedData.donneeVertex);
        ListeEcriture = generatedData.listeEcriture;
    }

    @Override
    public void bindData(ColorShaderProgram programCouleur){
        vertexArray.setVertexAttribPointer(0, programCouleur.getPosAttribLocation(), nbrCompPos, 0);
    }

    @Override
    public void ecriture(){
        for (construcObjet.commandeEcriture commandeE : ListeEcriture){
            commandeE.ecriture();
        }
    }

}
