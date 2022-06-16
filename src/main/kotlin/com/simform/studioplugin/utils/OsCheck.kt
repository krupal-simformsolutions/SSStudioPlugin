package com.simform.studioplugin.utils

object OsCheck {
    val operatingSystemType: OSType
        get() {
            val detectedOS: OSType
            val OS = System.getProperty("os.name", "generic").lowercase()
            detectedOS = if (OS.indexOf("mac") >= 0 || OS.indexOf("darwin") >= 0) {
                OSType.MacOS
            } else if (OS.indexOf("win") >= 0) {
                OSType.Windows
            } else if (OS.indexOf("nux") >= 0) {
                OSType.Linux
            } else {
                OSType.Other
            }
            return detectedOS
        }

    /**
     * types of Operating Systems
     */
    enum class OSType {
        Windows, MacOS, Linux, Other
    }
}