package info.dicj.prototype_poker3d.programs;

import android.content.Context;
import android.opengl.GLES20;

import info.dicj.prototype_poker3d.R;

/**
 * Created by PotBa1632703 on 2018-05-06.
 */

public class LightShaderProgram extends ShaderProgram {
    private final int uMVPMatrixLocation;
    private final int uMVMatrixLocation;
    private final int uLightPosition;

    private final int aPositionLocation;
    private final int aColorLocation;
    private final int aNormalLocation;

    private final int uTexture;
    private final int aTexCoordinate;

    public LightShaderProgram(Context contexte) {
        super(contexte, R.raw.pixel_vertex_shader, R.raw.pixel_fragment_shader);

        uMVPMatrixLocation = GLES20.glGetUniformLocation(program, U_MVPMatrix);
        uMVMatrixLocation = GLES20.glGetUniformLocation(program, U_MVMatrix);
        uLightPosition = GLES20.glGetUniformLocation(program, U_LightPos);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_Position);
        aColorLocation = GLES20.glGetAttribLocation(program, A_Color);
        aNormalLocation = GLES20.glGetAttribLocation(program, A_Normal);

        uTexture = GLES20.glGetUniformLocation(program, U_Texture);
        aTexCoordinate = GLES20.glGetAttribLocation(program, A_TexCoordinate);

    }

    public int getMVPMatrixLocation() {
        return uMVPMatrixLocation;
    }
    public int getMVMatrixLocation() {
        return uMVMatrixLocation;
    }
    public int getLightPosition() {
        return uLightPosition;
    }
    public int getPositionLocation() {
        return aPositionLocation;
    }
    public int getColorLocation() {
        return aColorLocation;
    }
    public int getNormalLocation() {
        return aNormalLocation;
    }
    public int getTexture() {
        return uTexture;
    }
    public int getTexCoordinate() {
        return aTexCoordinate;
    }
}
