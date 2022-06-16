package com.simform.studioplugin.utils.customview

import java.awt.Color
import java.awt.GridBagLayout
import java.awt.geom.RoundRectangle2D
import javax.swing.*

class ToastMessage(caller: JComponent?, toastString: String?) : JDialog() {
    private val milliseconds = 1500

    init {

        isUndecorated = true
        isAlwaysOnTop = true
        focusableWindowState = false
        layout = GridBagLayout()
        val panel = JPanel()
        panel.border = BorderFactory.createEmptyBorder(5, 10, 5, 10)
        panel.background = Color(160, 160, 160)
        val toastLabel = JLabel(toastString)
        toastLabel.foreground = Color.WHITE
        panel.add(toastLabel)
        add(panel)
        pack()
        val window = SwingUtilities.getWindowAncestor(caller)
        val xcoord = window.locationOnScreen.x + window.width / 2 - width / 2
        val ycoord = window.locationOnScreen.y + (window.height.toDouble() * 0.75).toInt() - height / 2
        setLocation(xcoord, ycoord)
        shape = RoundRectangle2D.Double(0.0, 0.0, width.toDouble(), height.toDouble(), 30.0, 30.0)
        isVisible = true
        object : Thread() {
            override fun run() {
                try {
                    spamProtect = true
                    sleep(milliseconds.toLong())
                    dispose()
                    spamProtect = false
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    companion object {
        private const val serialVersionUID = 1L
        private var spamProtect = false
    }
}