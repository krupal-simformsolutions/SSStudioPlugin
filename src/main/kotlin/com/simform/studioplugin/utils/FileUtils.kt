package com.simform.studioplugin

import java.io.File
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JFileChooser


fun JButton.openFolderChooserAction(callback: (String) -> Unit) {
    addActionListener {
        openFolderChooser(callback)
    }
}

fun JComponent.openFolderChooser(callback: (String) -> Unit) {
    val jFileChooser = JFileChooser()
    jFileChooser.dialogTitle = "Choose Destination"
    jFileChooser.currentDirectory = getHomeDirectory()
    jFileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    val valueX = jFileChooser.showOpenDialog(this)
    if (valueX == JFileChooser.APPROVE_OPTION) {
        callback(jFileChooser.selectedFile.absolutePath)
    }
}

fun getHomeDirectory(): File {
    return File(System.getProperty("user.home"))
}

fun getRootDirectory(): File {
    return File(".")
}