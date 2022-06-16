package com.simform.studioplugin.ui.flutter_setup

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.ui.DialogWrapper
import com.simform.studioplugin.*
import com.simform.studioplugin.ui.ProjectSetupAction
import com.simform.studioplugin.utils.customview.executeCookieCutter
import com.simform.studioplugin.utils.customview.getFlutterSdkPath

class CreateFlutterProject : ProjectSetupAction() {

    companion object {
        private const val TEMPLATE_PROJECT_LINK = "https://github.com/SimformSolutionsPvtLtd/flutter_project_template"
    }

    override fun actionPerformed(e: AnActionEvent) {
        validateInstalledPackages(includeFlutter = true) {
            openFlutterSetup()
        }
    }

    private fun openFlutterSetup() {
        DialogBuilder().apply {
            setTitle("Create Simform Application")
            val dialogX = CreateFlutterProjectUi()
            removeAllActions()
            setCenterPanel(dialogX.PanelMain)

            dialogX.btnBrowse.openFolderChooserAction {
                dialogX.tfSaveLocation.text = it
            }

            dialogX.btnBrowseFlutter.openFolderChooserAction {
                dialogX.tfFlutterSdk.text = it
            }

            dialogX.btnGenerate.addActionListener {
                try {
                    val appName = dialogX.tfAppName.text.requiredField("Please enter app name")
                    val repoName = dialogX.tfRepoName.text.requiredField("Please enter repo name")
                    val saveLocation =
                        dialogX.tfSaveLocation.text.requiredField("Please select project save location")
                            .requiredFilePath("Please enter valid save location")
                    val packageName = dialogX.tfPackageName.text.requiredField("Please enter bundle identifier")
                    val sentryKey = dialogX.tfSentry.text
                    val flutterSdkPath = dialogX.tfFlutterSdk.text.requiredField("Please select flutter sdk path")
                        .requiredFilePath("Please select valid flutter sdk path")
                    val repoLink = "NA"

                    executeCookieCutter(
                        TEMPLATE_PROJECT_LINK,
                        saveLocation,
                        ("app_name" to appName),
                        ("repo_name" to repoName),
                        ("bundle_identifier" to packageName),
                        ("sentry_dsn_key" to sentryKey),
                        ("include_portraitUp_orientation" to if (dialogX.cbPortraitUp.isSelected) "y" else "n"),
                        ("include_portraitDown_orientation" to if (dialogX.cbPortraitDown.isSelected) "y" else "n"),
                        ("include_landscapeLeft_orientation" to if (dialogX.cbLandscapeLeft.isSelected) "y" else "n"),
                        ("include_landscapeRight_orientation" to if (dialogX.cbLandscapeRight.isSelected) "y" else "n"),
                        ("minimum_android_sdk_version" to dialogX.spMinAndroid.value.toString()),
                        ("minimum_iOS_sdk_version" to dialogX.spMinAndroid.value.toString()),
                        ("minimum_iOS_sdk_version" to "all"),
                        ("flutter_sdk_path" to flutterSdkPath),
                        ("repo_link" to repoLink), onPreExecute = {
                            dialogX.pbLoading.isVisible = true
                        }, onPostExecute = {
                            dialogX.pbLoading.isVisible = false
                            dialogWrapper.close(DialogWrapper.CANCEL_EXIT_CODE)
                            ProjectManager.getInstance().loadAndOpenProject("$saveLocation/$repoName")
                        }, logCallback = {
                            println(it)
                        })
                } catch (e: IllegalArgumentException) {
                    dialogX.PanelMain.showAlert(e.message ?: "")
                }
            }
            dialogX.tfFlutterSdk.text = getFlutterSdkPath() ?: ""
            dialogX.pbLoading.isVisible = false
        }.show()
    }

}