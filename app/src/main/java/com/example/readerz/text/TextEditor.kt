
package com.example.readerz.text

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

@Composable
fun TextEditor(uri: Uri) {
    val context = LocalContext.current
    var content by remember { mutableStateOf("") }
    var loaded by remember { mutableStateOf(false) }

    LaunchedEffect(uri) {
        if (!loaded) {
            content = readText(context.contentResolver, uri)
            loaded = true
        }
    }

    OutlinedTextField(value = content, onValueChange = { content = it }, modifier = Modifier.fillMaxSize())
}

suspend fun readText(resolver: ContentResolver, uri: Uri): String = withContext(Dispatchers.IO) {
    val sb = StringBuilder()
    resolver.openInputStream(uri)?.use { input ->
        BufferedReader(InputStreamReader(input)).use { br ->
            var line = br.readLine()
            while (line != null) {
                sb.append(line).append('\n')
                line = br.readLine()
            }
        }
    }
    sb.toString()
}
