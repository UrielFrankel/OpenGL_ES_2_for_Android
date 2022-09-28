package frankel.uriel.opengl

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyFirstRenderer(val context: Context) : GLSurfaceView.Renderer {

    val POSITION_COMPONENT_COUNT = 2
    val BYTES_PER_FLOAT = 4
    lateinit var vertexData: FloatBuffer
    val a_Position = "a_Position"
    val U_COLOR = "u_Color"
    var uColorLocation = 0
    var aPositionLocation = 0

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        glClearColor(0f, 0f, 0f, 0f)


        val tableVerticesWithTriangles = floatArrayOf(
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,

            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,

            -0.5f, 0f,
            0.5f, 0f,


            // Mallets
            0f, -0.25f,
            0f, 0.25f,
        )

        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexData.put(tableVerticesWithTriangles)
        val vertexShaderSource = readTextFileFromResource(context, R.raw.simple_vertex_shader)
        val fragmentShaderSource = readTextFileFromResource(context, R.raw.simple_fragment_shader)
        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)
        val program = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        ShaderHelper.validateProgram(program)
        glUseProgram(program)

        uColorLocation = glGetUniformLocation(program, U_COLOR)
        aPositionLocation = glGetAttribLocation(program, a_Position)
        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData)
        glEnableVertexAttribArray(aPositionLocation)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10) {
        glClear(GL_COLOR_BUFFER_BIT)
        glUniform4f(uColorLocation, 1f, 1f, 1f, 1f)
        glDrawArrays(GL_TRIANGLES, 0, 6)
        glUniform4f(uColorLocation, 1f, 0f, 0f, 1f)
        glDrawArrays(GL_LINES, 6, 2)

        glUniform4f(uColorLocation, 0f, 0f, 1f, 1f)
        glDrawArrays(GL_POINTS, 8, 1)

        glUniform4f(uColorLocation, 1f, 0f, 0f, 1f)
        glDrawArrays(GL_POINTS, 9, 1)
    }

}
