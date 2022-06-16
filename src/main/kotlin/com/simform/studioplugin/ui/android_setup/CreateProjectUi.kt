package com.simform.studioplugin

import javax.swing.*
import javax.swing.JTextArea

class CreateProjectUi {

    lateinit var textArea1: JTextField
    lateinit var PanelMain: JPanel
    lateinit var tfAppName: JTextField
    lateinit var tfPackageName: JTextField
    lateinit var btnBrowse: JButton
    lateinit var tfSaveLocation: JTextField
    lateinit var tfRepoName: JTextField
    lateinit var tfAppCenter: JTextField
    lateinit var btnGenerate: JButton
    lateinit var pbLoading: JProgressBar
    lateinit var rbIncludeTest: JCheckBox
    lateinit var rbIncludeDb: JCheckBox
    lateinit var taLogs: JTextArea

    init {

        taLogs.lineWrap=true
        taLogs.rows = 4
    }
}