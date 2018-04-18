package info.dicj.prototype_poker3d.programs;

import android.content.Context;
import android.opengl.GLES20;

import info.dicj.prototype_poker3d.R;

/**
 * Created by PotBa1632703 on 2018-04-02.
 */

public class TextureShaderProgram extends ShaderProgram{
    private final int uMatrixLocation;
    private final int uTextureLocation;

    private final int aPositionLocation;
    private final int aTextureCoordLocation;

    public TextureShaderProgram(Context contexte){
        super(contexte, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_Matrix);
        uTextureLocation = GLES20.glGetUniformLocation(program, U_Texture_Unit);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_Position);
        aTextureCoordLocation = GLES20.glGetAttribLocation(program, A_Texture_Coordinates);
    }

    public void setUniforms(float[] matrice, int idTexture){
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrice, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, idTexture);

        GLES20.glUniform1i(uTextureLocation, 0);
    }

    public int getPosAttribLocation() {
        return aPositionLocation;
    }
    public int getTextureCoordLocation() {
        return aTextureCoordLocation;
    }
}
