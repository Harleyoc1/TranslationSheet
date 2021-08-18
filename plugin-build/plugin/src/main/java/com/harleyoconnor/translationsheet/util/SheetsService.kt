package com.harleyoconnor.translationsheet.util

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import org.gradle.api.file.Directory
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Files

private const val PORT = 8888
private val SCOPES = listOf(SheetsScopes.SPREADSHEETS_READONLY)

fun authorise(credentialFile: File, tokensDirectory: Directory, httpTransport: NetHttpTransport): Credential {
    val credentialsStream = Files.newInputStream(credentialFile.toPath())
    val clientSecrets: GoogleClientSecrets = GoogleClientSecrets.load(
        JacksonFactory.getDefaultInstance(),
        InputStreamReader(credentialsStream)
    )

    val flow: GoogleAuthorizationCodeFlow = GoogleAuthorizationCodeFlow.Builder(
        httpTransport,
        JacksonFactory.getDefaultInstance(),
        clientSecrets,
        SCOPES
    )
        .setDataStoreFactory(FileDataStoreFactory(tokensDirectory.asFile))
        .setAccessType("offline")
        .build()
    val receiver: LocalServerReceiver = LocalServerReceiver.Builder().setPort(PORT).build()
    return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
}

fun getSheetsService(credentialFile: File, tokensDirectory: Directory): Sheets {
    val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credentials = authorise(credentialFile, tokensDirectory, httpTransport)
    return Sheets.Builder(httpTransport, JacksonFactory.getDefaultInstance(), credentials)
        .setApplicationName("TranslationSheet")
        .build()
}
