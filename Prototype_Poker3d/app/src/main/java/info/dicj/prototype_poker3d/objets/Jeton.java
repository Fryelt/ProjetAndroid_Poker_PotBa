package info.dicj.prototype_poker3d.objets;

import android.graphics.Color;

import java.util.List;

import info.dicj.prototype_poker3d.donnee.VertexArray;
import info.dicj.prototype_poker3d.programs.ColorShaderProgram;
import info.dicj.prototype_poker3d.util.Geometrie;

/**
 * Created by PotBa1632703 on 2018-04-02.
 */

public class Jeton {
    private static final int nbrCompPos = 3;
    public final float rayon, hauteur;
    public final float cr, cg, cb;
    public Geometrie.Point pt;

    private final VertexArray vertexArray;
    private final List<consObjet.commandeEcriture> ListeEcriture;

    public Jeton(float rayon, float hauteur, int nbrPts, float x, float y, float z, float r, float g, float b){
        consObjet.GeneratedData generatedData = consObjet.creationJeton(new Geometrie.Cylindre(new Geometrie.Point(0f, 0f, 0f), rayon, hauteur), nbrPts);

        this.rayon = rayon;
        this.hauteur = hauteur;

        this.pt = new Geometrie.Point(x, y, z);

        this.cr = r;
        this.cg = g;
        this.cb = b;

        vertexArray = new VertexArray(generatedData.donneeVertex);
        ListeEcriture = generatedData.listeEcriture;
    }
    public void bindData(ColorShaderProgram programCouleur){
        vertexArray.setVertexAttribPointer(0, programCouleur.getPosAttribLocation(), nbrCompPos, 0);
    }
    public void ecriture(){
        for (consObjet.commandeEcriture commandeE : ListeEcriture){
            commandeE.ecriture();
        }
    }

}
