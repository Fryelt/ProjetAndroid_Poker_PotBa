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
    protected static final String U_Texture = "u_Texture";
    protected static final String U_Color = "u_Color";
    protected static final String U_MVPMatrix = "u_MVPMatrix";
    protected static final String U_MVMatrix = "u_MVMatrix";
    protected static final String U_LightPos = "u_LightPos";

    protected static final String A_Position = "a_Position";
    protected static final String A_Color = "a_Color";
    protected static final String A_Texture_Coordinates = "a_TextureCoordinates";
    protected static final String A_TexCoordinate = "a_TexCoordinate";
    protected static final String A_Normal = "a_Normal";

    protected final int program;
    protected ShaderProgram(Context contexte,int vertexIdResourceShader, int fragmentIdResourceShader){
        //Création d'un program basé sur les id des shaders
        program = ShaderHelper.constructionProgram(TextResourceReader.lectureTextFileFromRes(contexte, vertexIdResourceShader), TextResourceReader.lectureTextFileFromRes(contexte, fragmentIdResourceShader));
    }

    public void utilProgram(){
        GLES20.glUseProgram(program);
    }
}
