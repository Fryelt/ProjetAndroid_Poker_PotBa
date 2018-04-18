package info.dicj.prototype_poker3d.donnee;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static info.dicj.prototype_poker3d.Constantes.nbrBytesFloat;


/**
 * Created by PotBa1632703 on 2018-04-02.
 */

public class VertexArray {
    private final FloatBuffer floatBuffer;

    public VertexArray(float[] donneVertex){
        floatBuffer = ByteBuffer
                .allocateDirect(donneVertex.length * nbrBytesFloat)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(donneVertex);
    }

    public void setVertexAttribPointer(int donneeOffset, int posAttribut, int nbrComponent, int stride){
        floatBuffer.position(donneeOffset);
        GLES20.glVertexAttribPointer(posAttribut, nbrComponent, GLES20.GL_FLOAT, false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(posAttribut);

        floatBuffer.position(0);
    }
}
