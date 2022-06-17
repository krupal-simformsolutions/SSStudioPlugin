package com.simform.studioplugin.ui

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.ui.DialogWrapper
import com.simform.studioplugin.ui.software_checker.softwareinstaller
import com.simform.studioplugin.utils.OsCheck
import com.simform.studioplugin.utils.customview.*

abstract class ProjectSetupAction : AnAction() {

    sealed class Link(val url: String) {
        class FLUTTER_MAC : Link("https://docs.flutter.dev/get-started/install/macos")
        class FLUTTER_WIN : Link("https://docs.flutter.dev/get-started/install/windows")
        class FLUTTER_LINUX : Link("https://docs.flutter.dev/get-started/install/linux")
    }

    fun validateInstalledPackages(includeFlutter: Boolean = false, onAvailable: () -> Unit) {
        DialogBuilder().apply {
            val softwareinstaller = softwareinstaller()
            setCenterPanel(softwareinstaller.mainpanel)

            softwareinstaller.lblCookieCutter.text = if (isCookieCutterExist()) "Available" else "Not Available"
            softwareinstaller.lblPython.text = if (isPythonExists()) "Available" else "Not Available"
            softwareinstaller.lblFlutterStatus.text = if (isFlutterExists()) "Available" else "Not Available"

            softwareinstaller.btnInstallCookie.isEnabled = isCookieCutterExist().not()
            softwareinstaller.btnInstallPython.isEnabled = isPythonExists().not()
            softwareinstaller.btnFlutterInstall.isEnabled = isFlutterExists().not()

            softwareinstaller.lblFlutter.isVisible = includeFlutter
            softwareinstaller.lblFlutterStatus.isVisible = includeFlutter
            softwareinstaller.btnFlutterInstall.isVisible = includeFlutter

            softwareinstaller.which.addActionListener {
                softwareExistPlainMessage(softwareinstaller.tfWhich.text) {
                    softwareinstaller.tfWhich.text = it
                }
            }

            var flutterValidate = true
            if (includeFlutter) {
                if (isFlutterExists().not()) flutterValidate = false
            }

            softwareinstaller.btnContinue.isEnabled = isPythonExists() && isCookieCutterExist() && flutterValidate

            softwareinstaller.btnContinue.addActionListener {
                dialogWrapper.close(DialogWrapper.CANCEL_EXIT_CODE)
                onAvailable()
            }

            softwareinstaller.btnInstallPython.addActionListener {
                BrowserUtil.browse("https://www.google.com")
            }
            softwareinstaller.btnInstallCookie.addActionListener {
                BrowserUtil.browse("https://www.google.com")
            }
            softwareinstaller.btnFlutterInstall.addActionListener {
                val link = when (OsCheck.operatingSystemType) {
                    OsCheck.OSType.Windows -> {
                        Link.FLUTTER_WIN().url
                    }
                    OsCheck.OSType.MacOS -> {
                        Link.FLUTTER_MAC().url
                    }
                    OsCheck.OSType.Linux -> {
                        Link.FLUTTER_MAC().url
                    }
                    else -> {
                        Link.FLUTTER_WIN().url
                    }
                }
                BrowserUtil.browse(link)
            }
            removeAllActions()
        }.show()
    }

}