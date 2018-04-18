package info.dicj.prototype_poker3d.programs;

        import android.content.Context;
        import android.opengl.GLES20;

        import info.dicj.prototype_poker3d.util.ShaderHelper;
        import info.dicj.prototype_poker3d.util.TextResourceReader;

/**
 * Created by PotBa1632703 on 2018-04-02.
 */

public class ShaderProgram {
    protected static final String U_Matrix = "u_Matrix";
    protected static final String U_Texture_Unit = "u_TextureUnit";
    protected static final String U_Color = "u_Color";

    protected static final String A_Position = "a_Position";
    protected static final String A_Color = "a_Color";
    protected static final String A_Texture_Coordinates = "a_TextureCoordinates";

    protected final int program;
    protected ShaderProgram(Context contexte,int vertexIdResourceShader, int fragmentIdResourceShader){
        program = ShaderHelper.consProgram(TextResourceReader.lectureTextFileFromRes(contexte, vertexIdResourceShader), TextResourceReader.lectureTextFileFromRes(contexte, fragmentIdResourceShader));
    }

    public void utilProgram(){
        GLES20.glUseProgram(program);
    }
}
