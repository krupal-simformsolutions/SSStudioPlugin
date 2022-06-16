package com.simform.studioplugin.ui.firebase_tester

import com.google.gson.Gson
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.DialogBuilder
import com.simform.studioplugin.FirebaseTestUi
import com.simform.studioplugin.model.FirebaseResponse
import com.simform.studioplugin.requiredField
import com.simform.studioplugin.showAlert
import net.minidev.json.JSONObject
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils


class FirebaseTest : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        DialogBuilder().apply {
            setTitle("Firbase push notification")
            val dialogX = FirebaseTestUi()
            dialogX.pbLoading.isVisible = false
            dialogX.btnSendNotification.addActionListener {
                try {
                    val deviceToken = dialogX.tfDeviceToken.text.requiredField("Please enter device token")
                    val serverKey = dialogX.tfServerKey.text.requiredField("Please enter fcm server key")
                    val body = dialogX.tfBody.text.requiredField("Please enter message body")
                    val title = dialogX.tfTitle.text.requiredField("Please enter message title")

                    ApplicationManager.getApplication().executeOnPooledThread {
                        dialogX.pbLoading.isVisible = true
                        val response = sendNotification(
                            serverKey,
                            deviceToken,
                            title,
                            body
                        )
                        dialogX.pbLoading.isVisible = false

                        val responseString=EntityUtils.toString(response.entity, "UTF-8")
                        val x = Gson().fromJson(responseString, FirebaseResponse::class.java)

                        println(responseString)

                        if (x.success==1) {
                            dialogX.mainPanel.showAlert("Sent successfully")
                        } else {
                            dialogX.mainPanel.showAlert("Failure to sent")
                        }
                    }

                } catch (e: java.lang.IllegalArgumentException) {
                    dialogX.mainPanel.showAlert(e.message ?: "")
                }
            }
            removeAllActions()
            setCenterPanel(dialogX.mainPanel)
            resizable(false)
        }.show()
    }

    private fun sendNotification(serverKey: String, deviceToken: String, title: String, body: String): HttpResponse {
        val client: org.apache.http.client.HttpClient = HttpClientBuilder.create().build()
        val post = HttpPost("https://fcm.googleapis.com/fcm/send")
        post.setHeader("Content-type", "application/json")
        post.setHeader("Authorization", "key=$serverKey")

        val message = JSONObject()
        message["to"] = deviceToken
        message["priority"] = "high"

        val notification = JSONObject()
        notification["title"] = title
        notification["body"] = body

        message["notification"] = notification

        post.entity = StringEntity(message.toString(), "UTF-8")
        return client.execute(post)
    }

}