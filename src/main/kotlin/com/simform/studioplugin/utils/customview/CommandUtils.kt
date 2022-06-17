package com.simform.studioplugin.utils.customview

import com.intellij.openapi.application.ApplicationManager
import com.simform.studioplugin.getRootDirectory
import com.simform.studioplugin.utils.OsCheck
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import javax.swing.SwingUtilities

fun executeCookieCutter(
    template: String,
    saveLocation: String,
    vararg keyValues: Pair<String, String>,
    onPreExecute: () -> Unit,
    onPostExecute: () -> Unit,
    logCallback: (String) -> Unit
) {

    ApplicationManager.getApplication().executeOnPooledThread {
        onPreExecute()
        try {
            var line: String?

            val list = keyValues.map { it.first + "=" + it.second }
            val commands = mutableListOf<String>()
            commands.add("${getPathPrefixBasedOnOs()}cookiecutter")
            commands.add(template)
            commands.add("--no-input")
            commands.addAll(list)

            val builder = ProcessBuilder(commands)
            builder.directory(File(saveLocation))
            builder.redirectErrorStream(true)
            val process = builder.start()

            val reader = BufferedReader(InputStreamReader(process.inputStream))

            try {
                process.waitFor()
            } catch (e: java.lang.Exception) {
                logCallback(e.message ?: "")
            }
            while (reader.readLine().also { line = it } != null) {
                logCallback(line ?: "")
            }
            SwingUtilities.invokeLater(onPostExecute)
        } catch (e: Exception) {
            logCallback(e.message ?: "")
        }
    }

}

fun getFlutterSdkPath(): String? {
    val builder = ProcessBuilder(
        "flutter",
        "sdk-path"
    )
    builder.directory(getRootDirectory())
    builder.redirectErrorStream(true)
    val process = builder.start()

    val reader = BufferedReader(InputStreamReader(process.inputStream))
    try {
        process.waitFor()
    } catch (e: java.lang.Exception) {
        println(e.message)
    }

    return reader.readLine()
}

fun isCookieCutterExist(): Boolean {
    return softwareExists("cookiecutter", getPathPrefixBasedOnOs())
}

fun isPythonExists(): Boolean {
    return softwareExists("python3", getPathPrefixBasedOnOs())
}

fun isFlutterExists(): Boolean {
    return softwareExists("flutter", getPathPrefixBasedOnOs())
}

fun getPathPrefixBasedOnOs(): String {
    if (OsCheck.operatingSystemType == OsCheck.OSType.MacOS) {
        return "/usr/local/bin/"
    }
    return ""
}

@Throws(IOException::class)
fun softwareExists(binaryName: String, pathPrefix: String = ""): Boolean {
    var line: String?
    val builder = ProcessBuilder("/usr/bin/which", pathPrefix + binaryName)
    builder.redirectErrorStream(true)
    val process = builder.start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    try {
        process.waitFor()
    } catch (e: InterruptedException) {
        println(e.message)
    }
    while (reader.readLine().also { line = it } != null) {
        println(line)
        break // Reads only the first line
    }
    val result = line != null && line!!.isNotEmpty()
    return result
}

/*fun runCommands(vararg command:String):Boolean{
    var line: String?
    val builder = ProcessBuilder(command)
    builder.redirectErrorStream(true)
    val process = builder.start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    try {
        process.waitFor()
    } catch (e: InterruptedException) {
        println(e.message)
    }
    while (reader.readLine().also { line = it } != null) {
        println(line)
        break // Reads only the first line
    }
    val result = line != null && line!!.isNotEmpty()
    return result
}*/

fun softwareExistMessage(binaryName: String, callback: (String) -> Unit) {
    try {
        var line: String?
        val builder = ProcessBuilder("/usr/bin/which", binaryName)
        builder.redirectErrorStream(true)
        val process = builder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        try {
            process.waitFor()
        } catch (e: InterruptedException) {
            callback(e.message!!)
        }
        val errorStream = process.errorStream.bufferedReader()
        while (reader.readLine().also { line = it } != null) {
            callback(line!!)
        }
        while (errorStream.readLine().also { line = it } != null) {
            callback(line!!)
        }
    } catch (e: Exception) {
        callback(e.message!!)
    }
}

fun softwareExistPlainMessage(binaryName: String, callback: (String) -> Unit) {
    try {
        var line: String?
        val split=binaryName.split(" ")
        val builder = ProcessBuilder(split[0],split[1])
        builder.redirectErrorStream(true)
        val process = builder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        try {
            process.waitFor()
        } catch (e: InterruptedException) {
            callback(e.message!!)
        }
        val errorStream = process.errorStream.bufferedReader()
        while (reader.readLine().also { line = it } != null) {
            callback(line!!)
        }
        while (errorStream.readLine().also { line = it } != null) {
            callback(line!!)
        }
    } catch (e: Exception) {
        callback(e.message!!)
    }
}