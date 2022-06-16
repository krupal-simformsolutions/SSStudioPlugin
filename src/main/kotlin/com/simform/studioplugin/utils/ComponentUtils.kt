package com.simform.studioplugin

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.JTextField

fun JTextField.copyToClipboard() {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(StringSelection(this.text), null)



}