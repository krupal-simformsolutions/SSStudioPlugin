package com.simform.studioplugin.ui.mvvm

import com.simform.studioplugin.ui.mvvm.configure.MVVMTemplateSettings.Companion.instance
import java.io.IOException

/**
 * Created by yuhaiyang on 2019/10/31.
 */
class MVVMActivityAction : MVVMGeneratorAction() {
    @Throws(IOException::class)
    override fun create(pathString: String, targetName: String) {
        val packageName = getPackageName(pathString)
        val setting = instance
        createActivity(setting, pathString, packageName, targetName)
        createViewModel(setting, pathString, packageName, targetName)
        createActivityLayout(setting, getLayoutPath(pathString), packageName, targetName)
    }
}