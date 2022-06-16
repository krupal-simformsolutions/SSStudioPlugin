package com.simform.studioplugin

import java.io.File
import javax.swing.JOptionPane
import javax.swing.JPanel


@kotlin.jvm.Throws(IllegalArgumentException::class)
fun String.requiredField(message: String): String {
    if (trim().isEmpty()) {
        throw java.lang.IllegalArgumentException(message)
    }
    return this
}

fun String.requiredFilePath(message: String): String {
    if (trim().isEmpty()) {
        throw java.lang.IllegalArgumentException(message)
    }
    if (File(this).exists().not()) {
        throw java.lang.IllegalArgumentException(message)
    }
    return this
}

fun String.requireRegex(regex: Regex, message: String) {


}

fun JPanel.showAlert(message: String) {
    JOptionPane.showMessageDialog(this, message)
}