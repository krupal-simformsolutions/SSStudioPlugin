package com.simform.studioplugin.ui.libraries

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.ui.jcef.JBCefBrowser
import com.simform.studioplugin.model.LibraryModel
import java.awt.CardLayout
import javax.swing.DefaultListModel
import javax.swing.JList
import javax.swing.border.EmptyBorder

class LibrariesDetail : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {


        DialogBuilder().apply {
            setTitle("Simform Libraries")
            removeAllActions()

            val libraryUi = LIbraryUi()
            val browser =
                JBCefBrowser("https://www.google.com")

            setCenterPanel(libraryUi.PanelMain)

            libraryUi.panelweb.layout = CardLayout()
            libraryUi.panelweb.add(browser.component)

            libraryUi.tabbedPane.removeAll()

            libraryUi.tabbedPane.addTab("Android", createList(LibraryModel.getAndroidLibraries()) {
                browser.loadURL(it.link)
            })
            libraryUi.tabbedPane.addTab("Flutter", createList(LibraryModel.getFlutterLibraries()) {
                browser.loadURL(it.link)
            })

        }.show()
    }

    private fun createList(component: List<LibraryModel>, callback: (LibraryModel) -> Unit): JList<LibraryModel> {
        val androidList = JList<LibraryModel>()
        androidList.border = EmptyBorder(10, 10, 10, 10)
        val listModel = DefaultListModel<LibraryModel>()
        androidList.model = listModel
        listModel.addAll(component)
        androidList.addListSelectionListener {
            callback(component[androidList.selectedIndex])
        }
        return androidList
    }
}