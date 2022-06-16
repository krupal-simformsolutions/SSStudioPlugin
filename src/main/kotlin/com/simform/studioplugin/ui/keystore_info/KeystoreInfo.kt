package com.simform.studioplugin.ui.keystore_info

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogBuilder
import com.simform.studioplugin.KeyStoreInoUi
import com.simform.studioplugin.copyToClipboard
import com.simform.studioplugin.requiredField
import com.simform.studioplugin.requiredFilePath
import com.simform.studioplugin.utils.customview.ToastMessage
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import java.security.MessageDigest
import java.security.cert.X509Certificate
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.filechooser.FileNameExtensionFilter


class KeystoreInfo : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        DialogBuilder().apply {
            setTitle("Keystore Information")
            val dialogX = KeyStoreInoUi()
            resizable(false)

            dialogX.btnBrowse.addActionListener {
                val jFileChooser = JFileChooser()
                jFileChooser.currentDirectory = File(System.getProperty("user.home"))
                jFileChooser.dialogTitle = "Choose Destination"
                jFileChooser.addChoosableFileFilter(FileNameExtensionFilter("*.jks", "jks"))
                jFileChooser.fileFilter = FileNameExtensionFilter("*.jks", "jks")
                jFileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
                jFileChooser.choosableFileFilters
                val valueX = jFileChooser.showOpenDialog(dialogX.mainpanel)
                if (valueX == JFileChooser.APPROVE_OPTION) {
                    dialogX.tfKeyStore.text = jFileChooser.selectedFile.absolutePath
                }
            }

            dialogX.btnGenerate.addActionListener {
                try {
                    val keystore = dialogX.tfKeyStore.text.requiredField("Please choose your keystore file")
                        .requiredFilePath("Please choose valid keystore file")
                    val keypass = dialogX.tfKeyPass.text.requiredField("Please enter key pass")
                    val keyAlias = dialogX.tfAlias.text.requiredField("Please enter alias")

                    dialogX.tfMd5.text = getInfo(keystore, keypass, keyAlias, "MD5")
                    dialogX.tfSha1.text = getInfo(keystore, keypass, keyAlias, "SHA-1")
                    dialogX.tfSha256.text = getInfo(keystore, keypass, keyAlias, "SHA-256")

                } catch (e: java.lang.IllegalArgumentException) {
                    dialogX.mainpanel.showAlert(e.message ?: "")
                } catch (e: Exception) {
                    dialogX.mainpanel.showAlert("Something went wrong")
                }

            }

            dialogX.btnMd5Copy.addActionListener {
                dialogX.tfMd5.copyToClipboard()
                showCopiedMessage(dialogX)
            }
            dialogX.btnSha1Copy.addActionListener {
                dialogX.tfSha1.copyToClipboard()
                showCopiedMessage(dialogX)
            }
            dialogX.btnSha256Copy.addActionListener {
                dialogX.tfSha256.copyToClipboard()
                showCopiedMessage(dialogX)
            }

            removeAllActions()
            setCenterPanel(dialogX.mainpanel)
        }.show()
    }

    private fun showCopiedMessage(dialogX: KeyStoreInoUi) {
        ToastMessage(dialogX.mainpanel, "Copied to clipboard")
    }

    private fun JPanel.showAlert(message: String) {
        JOptionPane.showMessageDialog(this, message)
    }

    @kotlin.jvm.Throws(Exception::class)
    fun getInfo(filename: String, keyPassword: String, keyAlias: String, algo: String = "MD5"): String {
        var fingerPrint = ""

        val `is` = FileInputStream(filename)
        val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
        keystore.load(`is`, keyPassword.toCharArray())

        /* Gets the requested finger print of the certificate. */
        val cert = keystore.getCertificate(keyAlias) as X509Certificate
        val encCertInfo = cert.encoded
        val md = MessageDigest.getInstance(algo)
        val digest = md.digest(encCertInfo)

        /* Converts a byte array to hex string */
        val buf = StringBuffer()
        val len = digest.size
        for (i in 0 until len) {
            /* Converts a byte to hex digit and writes to the supplied buffer */
            val hexChars =
                charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
            val high = digest[i].toInt() and 0xf0 shr 4
            val low = digest[i].toInt() and 0x0f
            buf.append(hexChars[high])
            buf.append(hexChars[low])
            if (i < len - 1) {
                buf.append(":")
            }
        }
        fingerPrint = buf.toString()

        return fingerPrint
    }


}