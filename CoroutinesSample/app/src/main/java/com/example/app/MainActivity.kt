package com.example.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.app.internal.TestClass
import com.example.app.internal.Work
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.model.Message
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.codec.binary.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Properties
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class MainActivity : AppCompatActivity() {

    private val work = Work()

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start).setOnClickListener {
            //work.startWork()

            lifecycleScope.launch(Dispatchers.Default) {

                System.set

                val SCOPES = arrayOf(GmailScopes.GMAIL_COMPOSE)

                val creds = GoogleCredentials.getApplicationDefault()

//                val credential = GoogleAccountCredential.usingOAuth2(applicationContext, listOf<String>(*SCOPES))
//                    .setBackOff(ExponentialBackOff())
//                    .setSelectedAccountName("sergeybutr@gmail.com")

                val transport = NetHttpTransport()
                val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()

                val mService = Gmail.Builder(transport, jsonFactory, )
                    .setApplicationName("App name")
                    .build()

                val message: MimeMessage? = createEmail("uolterbishep@gmail.com", "sergeybutr@gmail.com", "Test", "Test")
                val messageRedy = createMessageWithEmail(message!!)

                val result = mService.users().Messages().send("sergeybutr@gmail.com", messageRedy).execute()

                Log.i("MainActivity", "onCreate: DONE")
            }
        }

        findViewById<Button>(R.id.cancel).setOnClickListener {
            work.stopWork()
        }

        findViewById<Button>(R.id.createBtn).setOnClickListener {
            val newObject = TestClass("Hello " + counter++)
            Log.i("MainActivity", "new object is create $newObject")
        }
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param toEmailAddress   email address of the receiver
     * @param fromEmailAddress email address of the sender, the mailbox account
     * @param subject          subject of the email
     * @param bodyText         body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException - if a wrongly formatted address is encountered.
     */
    @Throws(MessagingException::class)
    fun createEmail(
        toEmailAddress: String?,
        fromEmailAddress: String?,
        subject: String?,
        bodyText: String?
    ): MimeMessage? {
        val props = Properties()
        val session: Session = Session.getDefaultInstance(props, null)
        val email = MimeMessage(session)
        email.setFrom(InternetAddress(fromEmailAddress))
        email.addRecipient(
            javax.mail.Message.RecipientType.TO,
            InternetAddress(toEmailAddress)
        )
        email.setSubject(subject)
        email.setText(bodyText)
        return email
    }

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException        - if service account credentials file not found.
     * @throws MessagingException - if a wrongly formatted address is encountered.
     */
    @Throws(MessagingException::class, IOException::class)
    fun createMessageWithEmail(emailContent: MimeMessage): Message? {
        val buffer = ByteArrayOutputStream()
        emailContent.writeTo(buffer)
        val bytes = buffer.toByteArray()
        val encodedEmail: String = Base64.encodeBase64URLSafeString(bytes)
        val message = Message()
        message.setRaw(encodedEmail)
        return message
    }


    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "onResume")
    }

    override fun onStop() {
        super.onStop()
        // Stop all coroutines
        work.cancelAll()
        Log.i("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        // This is not guarantied to be always called
        // work.cancelAll()

        Log.i("MainActivity", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity", "onRestart")
    }
}