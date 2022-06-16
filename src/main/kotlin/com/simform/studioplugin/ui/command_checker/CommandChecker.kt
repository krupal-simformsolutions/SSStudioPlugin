package com.simform.studioplugin.ui.command_checker

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogBuilder
import com.simform.studioplugin.utils.customview.softwareExistMessage

class CommandChecker : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        DialogBuilder().apply {
            val commandcheckerui = commandcheckerui()
            setCenterPanel(commandcheckerui.mainpanel)

            setTitle("Command checker")
            commandcheckerui.btnExecute.addActionListener {
                softwareExistMessage(commandcheckerui.tfCommand.text) {
                    commandcheckerui.taLog.text = commandcheckerui.taLog.text + "\n" + it
                }
            }
            removeAllActions()
        }.show()
    }
}