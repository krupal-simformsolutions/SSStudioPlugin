package com.simform.studioplugin.ui.mvvm.configure

import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent

/**
 * Created by yuhaiyang on 2019/9/26.
 */
class MVVMTemplateConfigure : SearchableConfigurable {
    private val settings: MVVMTemplateSettings = MVVMTemplateSettings.instance
    private var form: MVVMForm? = null

    override fun getId(): String {
        return MVVMTemplateConfigure::class.java.name
    }

    override fun getDisplayName(): String {
        return "Android MVVM Generator"
    }

    override fun reset() {
        form!!.activityTemplate.text = settings.activityTemplate
        form!!.fragmentTemplate.text = settings.fragmentTemplate
        form!!.viewModelTemplate.text = settings.viewModelTemplate
        form!!.layoutTemplate.text = settings.layoutTemplate
    }

    override fun createComponent(): JComponent? {
        if (form == null) {
            form = MVVMForm()
        }
        return form!!.mainPanel
    }

    override fun isModified(): Boolean {
        return settings.activityTemplate != form!!.activityTemplate.text ||
                settings.fragmentTemplate != form!!.fragmentTemplate.text ||
                settings.viewModelTemplate != form!!.viewModelTemplate.text ||
                settings.layoutTemplate != form!!.layoutTemplate.text
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        settings.activityTemplate = form!!.activityTemplate.text
        settings.fragmentTemplate = form!!.fragmentTemplate.text
        settings.viewModelTemplate = form!!.viewModelTemplate.text
        settings.layoutTemplate = form!!.layoutTemplate.text
    }
}