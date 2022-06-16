package com.simform.studioplugin.ui.mvvm.configure

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.simform.studioplugin.ui.mvvm.template.MVVMTemple
import org.jdom.Element

/**
 * Created by yuhaiyang on 2019/9/26.
 */
@State(name = "MVVMTemplateSetting", storages = [Storage(value = "mvvm_generator_config.xml")])
class MVVMTemplateSettings : PersistentStateComponent<Element?> {
    var activityTemplate: String? = null
        get() = if (field == null) MVVMTemple.ACTIVITY else field
    var fragmentTemplate: String? = null
        get() = if (field == null) MVVMTemple.FRAGMENT else field
    var viewModelTemplate: String? = null
        get() = if (field == null) MVVMTemple.VIEW_MODEL else field
    var layoutTemplate: String? = null
        get() = if (field == null) MVVMTemple.LAYOUT else field

    override fun getState(): Element? {
        val element = Element("MvvmTemplateSettings")
        element.setAttribute("activityTemplate", activityTemplate)
        element.setAttribute("fragmentTemplate", fragmentTemplate)
        element.setAttribute("viewModelTemplate", viewModelTemplate)
        element.setAttribute("layoutTemplate", layoutTemplate)
        return element
    }

    override fun loadState(element: Element) {
        activityTemplate = element.getAttributeValue("activityTemplate")
        fragmentTemplate = element.getAttributeValue("fragmentTemplate")
        viewModelTemplate = element.getAttributeValue("viewModelTemplate")
        layoutTemplate = element.getAttributeValue("layoutTemplate")
    }

    companion object {
        @JvmStatic
        val instance: MVVMTemplateSettings
            get() = ServiceManager.getService(MVVMTemplateSettings::class.java)
    }
}