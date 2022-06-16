package com.simform.studioplugin

import java.awt.Color
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JProgressBar
import javax.swing.JTextArea
import javax.swing.JTextField

class FirebaseTestUi {

    lateinit var mainPanel:JPanel
    lateinit var tfServerKey:JTextField
    lateinit var tfDeviceToken:JTextField
    lateinit var tfTitle:JTextField
    lateinit var tfBody:JTextArea
    lateinit var btnSendNotification:JButton
    lateinit var pbLoading:JProgressBar

    init {

        pbLoading.foreground= Color.CYAN

    }
}