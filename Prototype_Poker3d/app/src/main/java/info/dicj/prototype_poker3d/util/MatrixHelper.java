package info.dicj.prototype_poker3d.util;

/**
 * Created by PotBa1632703 on 2018-04-03.
 */

public class MatrixHelper {
    public static void perspectiveM(float[] m, float FovYDeg, float aspect, float n, float f){
        final float radAngle = (float) (FovYDeg * Math.PI / 180);
        final float a = (float) (1.0 / Math.tan(radAngle / 2.0));

        //Basée sur une formule mathématique sur les matrices quant à la perception en 3d
        m[0] = a / aspect;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;

        m[4] = 0f;
        m[5] = a;
        m[6] = 0f;
        m[7] = 0f;

        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((f + n) / (f - n));
        m[11] = -1f;

        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * f * n) / (f - n));
        m[15] = 0f;

    }
}
