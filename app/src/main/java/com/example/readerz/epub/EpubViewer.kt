
package com.example.readerz.epub

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import nl.siegmann.epublib.domain.Book
import nl.siegmann.epublib.epub.EpubReader
import java.io.InputStream

@Composable
fun EpubViewer(uri: Uri) {
    // Reads EPUB via epublib and displays first spine resource in a WebView.
    val context = LocalContext.current
    var html by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uri) {
        context.contentResolver.openInputStream(uri)?.use { input: InputStream ->
            val book: Book = EpubReader().readEpub(input)
            val spine = book.spine.spineReferences
            if (spine.isNotEmpty()) {
                val res = spine[0].resource
                val data = String(res.data)
                html = data
            } else {
                html = "<html><body><p>Empty EPUB.</p></body></html>"
            }
        }
    }

    html?.let { content ->
        AndroidView(factory = { ctx ->
            WebView(ctx).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = false
                loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
            }
        }, modifier = Modifier.fillMaxSize())
    }
}
