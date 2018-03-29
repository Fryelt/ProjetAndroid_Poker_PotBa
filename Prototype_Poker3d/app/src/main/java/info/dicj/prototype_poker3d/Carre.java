package info.dicj.prototype_poker3d;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by PotBa1632703 on 2018-03-20.
 */

public class Carre {
    private int positionHandleP;
    private int colorHandleP;
    private int MVPMatrixHandleP;

    private final int programP;

    private FloatBuffer vertexBuffer;
    private ShortBuffer listDrawBuffer;

    static final int nbrCoordVertex = 3;
    static float coordCarre[] = {
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, 0.5f, 0.0f, // top right
            -0.5f, 0.5f, 0.0f, // top left


            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f, // bottom right
            0.5f,  0.5f, 0.0f, // top right
    };
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private short ordreDraw[] = {0,1,2,0,2,3}; //Ordre d'écriture des vertex
    private final int compteVertex = coordCarre.length / nbrCoordVertex;
    private final int strideVertex = nbrCoordVertex * 4;
    float couleur[] = {0.6f, 0.75f, 0.25f, 1.0f};

    public Carre(){
        int vertexShader, fragmentShader;

        vertexShader = PrototypeGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        fragmentShader = PrototypeGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(coordCarre.length * 4);

        byteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(coordCarre);
        vertexBuffer.position(0);

        ByteBuffer drawBuffer = ByteBuffer.allocateDirect(ordreDraw.length * 2);
        drawBuffer.order(ByteOrder.nativeOrder());
        listDrawBuffer = drawBuffer.asShortBuffer();
        listDrawBuffer.put(ordreDraw);
        listDrawBuffer.position(0);

        programP = GLES20.glCreateProgram();
        GLES20.glAttachShader(programP, vertexShader);
        GLES20.glAttachShader(programP, fragmentShader);

        GLES20.glLinkProgram(programP);
    }
    public void draw(float[] mvpMatrix){
        GLES20.glUseProgram(programP);

        positionHandleP = GLES20.glGetAttribLocation(programP, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandleP);
        //Coordonnées
        GLES20.glVertexAttribPointer(positionHandleP, nbrCoordVertex, GLES20.GL_FLOAT, false, strideVertex, vertexBuffer);

        colorHandleP = GLES20.glGetUniformLocation(programP, "vColor");
        GLES20.glUniform4fv(colorHandleP, 1, couleur, 0);

        MVPMatrixHandleP = GLES20.glGetUniformLocation(programP, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(MVPMatrixHandleP, 1, false, mvpMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, compteVertex);
        GLES20.glDisableVertexAttribArray(positionHandleP);

    }
}
