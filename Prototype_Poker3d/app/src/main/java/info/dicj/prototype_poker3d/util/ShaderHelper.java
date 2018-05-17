package info.dicj.prototype_poker3d.util;

import android.opengl.GLES10;
import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by PotBa1632703 on 2018-04-03.
 */

public class ShaderHelper {
    private static final String Tag = "ShaderHelper";

    public static int compilShaderVertex(String codeShader){
        return compilShader(GLES20.GL_VERTEX_SHADER, codeShader);
    }

    public static int compilFragmentVertex(String codeShader){
        return compilShader(GLES20.GL_FRAGMENT_SHADER, codeShader);
    }

    private static int compilShader(int type, String codeShader) {
        final int idObjetShader = GLES20.glCreateShader(type);
        final int[] statusCompilation = new int[1];

        if (idObjetShader == 0){
            if (LoggerConfig.ON){
                Log.w(Tag, "N'a pas pu créer le shader");
            }
            return 0;
        }

        //Compilation du shader
        GLES20.glShaderSource(idObjetShader, codeShader);
        GLES20.glCompileShader(idObjetShader);

        //Obtention de l'id du shader
        GLES20.glGetShaderiv(idObjetShader, GLES20.GL_COMPILE_STATUS, statusCompilation, 0);

        if (LoggerConfig.ON){
            Log.v(Tag, "Résultat de la compilation : " + "\n" + codeShader + "\n" + GLES20.glGetShaderInfoLog(idObjetShader));
        }

        if (statusCompilation[0] == 0){
            GLES20.glDeleteShader(idObjetShader);
            if (LoggerConfig.ON){
                Log.w(Tag, "N'a pas pu compiler le shader");
            }
            return 0;
        }
        return idObjetShader;
    }
    public static int lierProgram(int idVertexShader, int idFragmentShader){
        final int idObjetProgram =  GLES20.glCreateProgram();
        final int[] statusLien = new int[1];

        if (idObjetProgram == 0){
            if (LoggerConfig.ON){
                Log.w(Tag, "Impossible de creer le programme");
            }
            return 0;
        }

        //Attachement des 2 shaders dans un programme
        GLES20.glAttachShader(idObjetProgram, idVertexShader);
        GLES20.glAttachShader(idObjetProgram, idFragmentShader);
        //Liaison des 2 shaders
        GLES20.glLinkProgram(idObjetProgram);

        //Obtention de l'id du programme
        GLES20.glGetProgramiv(idObjetProgram, GLES20.GL_LINK_STATUS, statusLien, 0);
        if (statusLien[0] == 0){
            GLES20.glDeleteProgram(idObjetProgram);
            if (LoggerConfig.ON){
                Log.w(Tag, "N'a pas pu compiler le shader");
            }
            return 0;
        }

        if (LoggerConfig.ON){
            Log.v(Tag, "Résultat de la compilation :\n" + GLES20.glGetProgramInfoLog(idObjetProgram));
        }

        return idObjetProgram;
    }
    public static boolean validationProgram(int idObjetProgram){
        final int[] statusValidation = new int[1];

        //Validation du programme
        GLES20.glValidateProgram(idObjetProgram);
        //Obtention de l'id du programme
        GLES20.glGetProgramiv(idObjetProgram, GLES20.GL_VALIDATE_STATUS, statusValidation, 0);
        Log.v(Tag, "Résultat de la validation du programme : " + statusValidation[0] + "\nLog : " + GLES20.glGetProgramInfoLog(idObjetProgram));

        return statusValidation[0] != 0;
    }
    public static int constructionProgram(String vertexSourceShader, String fragmentSourceShader){
        int program, vertexShader, fragmentShader;

        vertexShader = compilShaderVertex(vertexSourceShader);
        fragmentShader = compilFragmentVertex(fragmentSourceShader);

        program = lierProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON)
            validationProgram(program);

        return program;
    }
}
