package info.dicj.prototype_poker3d.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by PotBa1632703 on 2018-04-03.
 */

public class TextResourceReader {
    public static String lectureTextFileFromRes(Context contexte, int idResource){
        StringBuilder corps = new StringBuilder();
        InputStream inputStream;
        InputStreamReader inputReader;
        BufferedReader readerB;

        String ligneT;

        try {
            inputStream = contexte.getResources().openRawResource(idResource);
            inputReader = new InputStreamReader(inputStream);
            readerB = new BufferedReader(inputReader);

            while ((ligneT = readerB.readLine()) != null){
                corps.append(ligneT);
                corps.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossibilité d'ouvrir la ressource ouverte" + idResource, e);
        } catch (Resources.NotFoundException nfe){
            throw new RuntimeException("Resource non-trouvée : " + idResource, nfe);
        }
        return corps.toString();
    }
}
