package com.simform.studioplugin.ui.mvvm

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.ui.Messages
import com.simform.studioplugin.ui.mvvm.configure.MVVMTemplateSettings
import com.simform.studioplugin.ui.mvvm.template.MVVMTemple
import com.simform.studioplugin.utils.StringUtils
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * Created by yuhaiyang on 2019/9/26.
 */
open class MVVMGeneratorAction : AnAction() {
    private var project: Project? = null
    override fun actionPerformed(e: AnActionEvent) {
        project = e.getData(PlatformDataKeys.PROJECT)
        val path = getCurrentPath(e)
        val moduleName = inputModuleName()
        try {
            create(path, moduleName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        Messages.showMessageDialog("文件已经生成完毕", "提示", Messages.getInformationIcon())
        refreshProject(e)
    }

    @Throws(IOException::class)
    protected open fun create(pathString: String, targetName: String) {
    }

    @Throws(IOException::class)
    fun createActivity(setting: MVVMTemplateSettings, pathString: String, packageName: String?, targetName: String?) {
        val fileName = StringUtils.plusString(targetName, MVVMTemple.ACTIVITY_SUFFIX, ".kt")
        var content = setting.activityTemplate
        content = content!!.replace("\\$\\{PACKAGE_NAME}".toRegex(), packageName!!)
        content = content.replace("\\$\\{TARGET_NAME}".toRegex(), targetName!!)
        content = content.replace("\\$\\{TARGET_NAME_LINE}".toRegex(), StringUtils.humpToLine(targetName))
        content = StringUtils.format(content)
        createFile(pathString, fileName, content)
    }

    @Throws(IOException::class)
    fun createFragment(setting: MVVMTemplateSettings, pathString: String, packageName: String?, targetName: String?) {
        val fileName = StringUtils.plusString(targetName, MVVMTemple.FRAGMENT_SUFFIX, ".kt")
        var content = setting.fragmentTemplate
        content = content!!.replace("\\$\\{PACKAGE_NAME}".toRegex(), packageName!!)
        content = content.replace("\\$\\{TARGET_NAME}".toRegex(), targetName!!)
        content = content.replace("\\$\\{TARGET_NAME_LINE}".toRegex(), StringUtils.humpToLine(targetName))
        content = StringUtils.format(content)
        createFile(pathString, fileName, content)
    }

    @Throws(IOException::class)
    fun createViewModel(setting: MVVMTemplateSettings, pathString: String, packageName: String?, targetName: String?) {
        val fileName = StringUtils.plusString(targetName, MVVMTemple.VIEW_MODEL_SUFFIX, ".kt")
        var content = setting.viewModelTemplate!!.replace("\\$\\{PACKAGE_NAME}".toRegex(), packageName!!)
        content = content.replace("\\$\\{TARGET_NAME}".toRegex(), targetName!!)
        content = StringUtils.format(content)
        createFile(pathString, fileName, content)
    }

    @Throws(IOException::class)
    fun createActivityLayout(
        setting: MVVMTemplateSettings,
        pathString: String,
        packageName: String?,
        targetName: String?
    ) {
        val fileName = StringUtils.plusString("a", StringUtils.humpToLine(targetName), ".xml")
        var content = setting.layoutTemplate!!.replace("\\$\\{PACKAGE_NAME}".toRegex(), packageName!!)
        content = content.replace("\\$\\{TARGET_NAME}".toRegex(), targetName!!)
        content = StringUtils.format(content)
        createFile(pathString, fileName, content)
    }

    @Throws(IOException::class)
    fun createFragemtnLayout(
        setting: MVVMTemplateSettings,
        pathString: String,
        packageName: String?,
        targetName: String?
    ) {
        val fileName = StringUtils.plusString("f", StringUtils.humpToLine(targetName), ".xml")
        var content = setting.layoutTemplate!!.replace("\\$\\{PACKAGE_NAME}".toRegex(), packageName!!)
        content = content.replace("\\$\\{TARGET_NAME}".toRegex(), targetName!!)
        content = StringUtils.format(content)
        createFile(pathString, fileName, content)
    }

    @Throws(IOException::class)
    private fun createFile(pathString: String, fileName: String, content: String?) {
        var path = File(pathString)
        if (path.isFile) {
            path = path.parentFile
        }
        val file = File(path, fileName)
        if (!path.exists()) {
            path.mkdirs()
        }
        if (file.exists()) {
            Messages.showMessageDialog("文件已经存在", "提示", Messages.getInformationIcon())
            return
        }
        file.createNewFile()
        val writer = BufferedWriter(FileWriter(file))
        writer.write(content)
        writer.flush()
        writer.close()
    }

    private fun refreshProject(e: AnActionEvent) {
        val project = e.project
        if (project == null) {
            Messages.showInfoMessage("获取Project失败，工程无法刷新", "提示")
            return
        }
        val file = project.guessProjectDir()
        if (file == null) {
            Messages.showInfoMessage("获取VirtualFile失败，工程无法刷新", "提示")
            return
        }
        file.refresh(false, true)
    }

    private fun inputModuleName(): String {
        return Messages.showInputDialog(
            project,
            "请输入模块名称。",
            "输入模块名称",
            Messages.getQuestionIcon()
        )!!
    }

    private fun getCurrentPath(e: AnActionEvent): String {
        val currentFile = PlatformDataKeys.VIRTUAL_FILE.getData(e.dataContext)
        return currentFile?.path!!
    }

    protected fun getPackageName(path: String): String {
        val strings = path.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val packageName = StringBuilder()
        var packageBegin = false
        for (i in strings.indices) {
            val string = strings[i]
            if (string == "com" || string == "org" || string == "cn" || string == "pw") {
                packageBegin = true
            }
            if (packageBegin) {
                packageName.append(string)
                if (i != strings.size - 1) {
                    packageName.append(".")
                }
            }
        }
        return packageName.toString()
    }

    protected fun getLayoutPath(path: String): String {
        val index = path.indexOf("/java/")
        return path.substring(0, index) + "/res/layout/"
    }
}