package info.dicj.prototype_poker3d.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

import static android.opengl.GLUtils.texImage2D;
/**
 * Created by PotBa1632703 on 2018-04-04.
 */

public class TextureHelper {
    private static final String Tag = "TextureHelper";
    public static int loadTexture(Context contexte, int idResource){
        final int[] idsTextObj = new int[1];

        //Classique manipulation d'un Bitmap Factory afin de décoder l'image à décomposer dans un Bitmap, pour ensuite l'appliquer à l'environnement OpenGL
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(contexte.getResources(), idResource, options);

        GLES20.glGenTextures(1, idsTextObj, 0);

        if (idsTextObj[0] == 0){
            if (LoggerConfig.ON)
                Log.w(Tag, "N'as pas pu générer un objet texture");
            return 0;
        }

        if (bitmap == null){
            if (LoggerConfig.ON)
                Log.w(Tag, "Id ressource : " + idResource + " indécodable.");

            GLES20.glDeleteTextures(1, idsTextObj, 0);
            return 0;
        }

        //Attachement des textures trouvées
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, idsTextObj[0]);

        //Affectation de paramètres
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

        //Conversion de la bitmap en textures OpenGL
        texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        //Rafraîchissement de la bitmap
        bitmap.recycle();

        return idsTextObj[0];
    }
}
