package info.dicj.prototype_poker3d.programs;

import android.content.Context;
import android.opengl.GLES20;

import info.dicj.prototype_poker3d.R;

/**
 * Created by PotBa1632703 on 2018-04-02.
 */

public class ColorShaderProgram extends ShaderProgram{
    private final int uMatrixLocation;
    private final int uColorLocation;

    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context contexte){
        super(contexte, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_Matrix);
        uColorLocation = GLES20.glGetUniformLocation(program, U_Color);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_Position);
        aColorLocation = GLES20.glGetAttribLocation(program, A_Color);
    }

    public void setUniforms(float[] matrice, float r, float g, float b){
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrice, 0);
        GLES20.glUniform4f(uColorLocation, r, g, b, 1f);
    }

    public int getPosAttribLocation(){
        return aPositionLocation;
    }
    public int getColorAttribLocation(){
        return aColorLocation;
    }
}
