package frankel.uriel.opengl

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

public fun readTextFileFromResource(context: Context, @RawRes resourcesId: Int): String {
    val body = StringBuilder()
    try {
        val inputStream = context.resources.openRawResource(resourcesId)
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var nextLine = bufferedReader.readLine()
        while (nextLine != null) {
            body.append(nextLine).append('\n')
            nextLine = bufferedReader.readLine()
        }
    } catch (e: IOException) {
        throw RuntimeException("could not open resource: $resourcesId", e)
    } catch (e: Resources.NotFoundException) {
        throw RuntimeException("resource not found: $resourcesId", e)
    }
    return body.toString()
}