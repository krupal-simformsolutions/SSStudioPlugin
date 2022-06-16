package com.simform.studioplugin.ui.android_setup

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.ui.DialogWrapper
import com.simform.studioplugin.*
import com.simform.studioplugin.ui.ProjectSetupAction
import com.simform.studioplugin.utils.customview.executeCookieCutter


class CreateAndroidProject : ProjectSetupAction() {

    companion object {
        private const val TEMPLATE_PROJECT_LINK = "https://github.com/SimformSolutionsPvtLtd/Android_Project_Setup"
        private const val COPYRIGHT_YEAR = "2022"
    }

    override fun actionPerformed(e: AnActionEvent) {
        validateInstalledPackages(false) {
            openAndroidSetup()
        }
    }

    private fun openAndroidSetup() {
        DialogBuilder().apply {
            setTitle("Create Simform Android Application")
            removeAllActions()

            val dialogX = CreateProjectUi()
            setCenterPanel(dialogX.PanelMain)
            dialogX.btnGenerate.addActionListener {
                try {
                    val appName = dialogX.tfAppName.text.requiredField("Please enter app name")
                    val repoName = dialogX.tfRepoName.text.requiredField("Please enter repo name")
                    val packageName = dialogX.tfPackageName.text.requiredField("Please enter package name")
                    val includeDb = if (dialogX.rbIncludeDb.isSelected) "y" else "n"
                    val includeTesting = if (dialogX.rbIncludeTest.isSelected) "y" else "n"
                    val saveLocation = dialogX.tfSaveLocation.text.requiredField("Please select save location")
                        .requiredFilePath("Please select valid save location")
                    executeCookieCutter(
                        TEMPLATE_PROJECT_LINK, saveLocation,
                        ("app_name" to appName),
                        ("repo_name" to repoName),
                        ("package_name" to packageName),
                        ("appcenter_key" to dialogX.tfAppCenter.text),
                        ("copyright_year_name" to "$COPYRIGHT_YEAR $appName"),
                        ("include_testing" to includeTesting),
                        ("include_room_db" to includeDb),
                        onPreExecute = {
                            dialogX.pbLoading.isVisible = true
                        }, onPostExecute = {
                            dialogX.pbLoading.isVisible = false
                            dialogWrapper.close(DialogWrapper.CANCEL_EXIT_CODE)
                            ProjectManager.getInstance().loadAndOpenProject("$saveLocation/$repoName")
                        }, logCallback = {
                            dialogX.taLogs.text = dialogX.taLogs.text + "\n" + it
                            println(it)
                        })
                } catch (e: IllegalArgumentException) {
                    dialogX.PanelMain.showAlert(e.message ?: "")
                }
            }
            dialogX.btnBrowse.openFolderChooserAction {
                dialogX.tfSaveLocation.text = it
            }
            dialogX.pbLoading.isVisible = false

            show()
        }
    }

}