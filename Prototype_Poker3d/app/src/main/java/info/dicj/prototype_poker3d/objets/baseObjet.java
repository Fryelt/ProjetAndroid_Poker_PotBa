package info.dicj.prototype_poker3d.objets;

import android.opengl.GLES20;

import java.nio.ByteBuffer;

import info.dicj.prototype_poker3d.Constantes;
import info.dicj.prototype_poker3d.donnee.VertexArray;
import info.dicj.prototype_poker3d.programs.LightShaderProgram;
import info.dicj.prototype_poker3d.util.Couleur;
import info.dicj.prototype_poker3d.util.Geometrie;

/**
 * Created by PotBa1632703 on 2018-04-25.
 */

public class baseObjet{

    public Geometrie.Point position;
    public Couleur couleur;

    protected VertexArray vertexArray;

    protected VertexArray positionArray;
    protected VertexArray couleurArray;
    protected VertexArray normalArray;
    protected VertexArray textureArray;

    protected ByteBuffer indexArray;

    public float[] donneeNormal;
    public float[] donneeCouleur;
    public float[] donneePos;
    public float[] donneeCoordText;

    public void ecriture(){

    }

    public void setVertex(LightShaderProgram programTexture) {

    }
}
