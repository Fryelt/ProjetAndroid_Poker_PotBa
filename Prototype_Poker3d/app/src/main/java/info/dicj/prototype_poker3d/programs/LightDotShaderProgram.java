package info.dicj.prototype_poker3d.programs;

import android.content.Context;
import android.opengl.GLES20;

import info.dicj.prototype_poker3d.R;

/**
 * Created by PotBa1632703 on 2018-05-06.
 */

public class LightDotShaderProgram extends ShaderProgram{
    private final int uMVPMatrixLocation;

    private final int aPositionLocation;

    public LightDotShaderProgram(Context contexte){
        super(contexte, R.raw.light_vertex_shader, R.raw.light_fragment_shader);

        //Calcul des localisations et uniformes
        aPositionLocation = GLES20.glGetAttribLocation(program, A_Position);
        uMVPMatrixLocation = GLES20.glGetUniformLocation(program, U_MVPMatrix);
    }

    public void setUniforms(float[] matrice, int idPositionMVPMatrice){
        GLES20.glUniformMatrix4fv(idPositionMVPMatrice, 1, false, matrice, 0);
    }

    public int getPosAttribLocation() {
        return aPositionLocation;
    }
    public int getMVPMatrixLocation() {
        return uMVPMatrixLocation;
    }
}
