package frankel.uriel.opengl

import android.opengl.GLES20
import android.opengl.GLES20.*
import android.util.Log

class ShaderHelper {
    companion object {
        private val TAG = "ShaderHelper"

        fun compileVertexShader(shaderCode: String): Int {
            return compileShader(GL_VERTEX_SHADER, shaderCode)
        }

        fun compileFragmentShader(shaderCode: String): Int {
            return compileShader(GL_FRAGMENT_SHADER, shaderCode)
        }

        private fun compileShader(type: Int, shaderCode: String): Int {
            val shaderObjectId = glCreateShader(type)
            if (shaderObjectId == 0) {
                Log.w(TAG, "could not create new shader")
                return 0
            }

            glShaderSource(shaderObjectId,shaderCode)
            glCompileShader(shaderObjectId)
            val compileStatus = IntArray(1)
            glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0)
            Log.v(TAG, "Results of compiling source:\n $shaderCode \n ${glGetShaderInfoLog(shaderObjectId)}")
            if (compileStatus[0] == 0) {
                glDeleteShader(shaderObjectId)
                Log.w(TAG, "compilation of shader failed")
                return 0
            }
            return shaderObjectId
        }

        public fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
            val program = glCreateProgram()
            if (program == 0) {
                Log.w(TAG, "Could not create new program")
                return 0
            }
            glAttachShader(program, vertexShaderId)
            glAttachShader(program, fragmentShaderId)
            glLinkProgram(program)
            val linkStatus = IntArray(1)
            glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0)
            Log.v(TAG, "Results of linking program:\n ${glGetProgramInfoLog(program)}")
            if (linkStatus[0] == 0) {
                glDeleteProgram(program)
                Log.w(TAG, "linking program failed")
                return 0
            }
            return program
        }


        fun validateProgram(programId: Int): Boolean {
            glValidateProgram(programId)
            val validateStatus = IntArray(1)
            glGetProgramiv(programId, GL_VALIDATE_STATUS, validateStatus, 0)
            Log.v(TAG, "Results of validating program:\n ${glGetProgramInfoLog(programId)}")
            return validateStatus[0] != 0
        }
    }
}