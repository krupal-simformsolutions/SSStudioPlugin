package com.simform.studioplugin

import java.text.NumberFormat
import javax.swing.*
import javax.swing.text.NumberFormatter

class CreateFlutterProjectUi {
    lateinit var textArea1: JTextField
    lateinit var PanelMain: JPanel
    lateinit var tfAppName: JTextField
    lateinit var tfPackageName: JTextField
    lateinit var btnBrowse: JButton
    lateinit var tfSaveLocation: JTextField
    lateinit var tfRepoName: JTextField
    lateinit var tfSentry: JTextField
    lateinit var btnGenerate: JButton
    lateinit var pbLoading: JProgressBar
    lateinit var cbPortraitUp: JCheckBox
    lateinit var cbPortraitDown: JCheckBox
    lateinit var cbLandscapeLeft: JCheckBox
    lateinit var cbLandscapeRight: JCheckBox
    lateinit var spMinAndroid: JSpinner
    lateinit var spMinIos: JSpinner
    lateinit var tfMinIos: JFormattedTextField
    lateinit var tfFlutterSdk: JTextField
    lateinit var btnBrowseFlutter: JButton

    init {
        spMinAndroid.value = 28
        spMinIos.value = 11
        /*  tfMinIos.formatterFactory = DefaultFormatterFactory(createNumberFormatter())*/
    }

    fun createNumberFormatter(): NumberFormatter {
        val format: NumberFormat = NumberFormat.getInstance()
        val formatter = NumberFormatter(format)
        formatter.valueClass = Int::class.java
        formatter.minimum = 0
        formatter.maximum = 100
        formatter.allowsInvalid = false
        formatter.commitsOnValidEdit = true
        return formatter
    }
}