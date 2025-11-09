
package com.example.readerz

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import com.example.readerz.epub.EpubViewer
import com.example.readerz.ppt.PptxViewer
import com.example.readerz.text.TextEditor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var uri by remember { mutableStateOf<Uri?>(null) }
    val pick = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { u -> uri = u }

    Scaffold(topBar = { SmallTopAppBar(title = { Text("Reader'z") }) }) { padding ->
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { pick.launch(arrayOf("*/*")) }) { Text("Open file") }
            }
            Spacer(modifier = Modifier.height(12.dp))
            uri?.let { u ->
                val resolver = LocalContext.current.contentResolver
                val name = DocumentFile.fromSingleUri(LocalContext.current, u)?.name ?: ""
                when {
                    name.endsWith(".epub", ignoreCase = true) -> EpubViewer(uri = u)
                    name.endsWith(".pptx", ignoreCase = true) || resolver.getType(u)?.contains("presentation") == true -> PptxViewer(uri = u)
                    else -> TextEditor(uri = u)
                }
            } ?: Text("No file opened")
        }
    }
}
