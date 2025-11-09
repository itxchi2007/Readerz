
package com.example.readerz.ppt

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xslf.usermodel.XSLFShape
import org.apache.poi.xslf.usermodel.XSLFPictureShape
import java.io.InputStream

@Composable
fun PptxViewer(uri: Uri) {
    val context = LocalContext.current
    var slides by remember { mutableStateOf<List<SlideContent>>(emptyList()) }

    LaunchedEffect(uri) {
        slides = withContext(Dispatchers.IO) {
            val list = mutableListOf<SlideContent>()
            context.contentResolver.openInputStream(uri)?.use { input: InputStream ->
                val ppt = XMLSlideShow(input)
                for (slide in ppt.slides) {
                    val texts = mutableListOf<String>()
                    val images = mutableListOf<ByteArray>()
                    for (shape in slide.shapes) {
                        try {
                            if (shape is XSLFPictureShape) {
                                val data = shape.pictureData.data
                                images.add(data)
                            } else {
                                val s = shape.toString()
                                if (s.isNotBlank()) texts.add(s)
                            }
                        } catch (e: Exception) { /* ignore */ }
                    }
                    list.add(SlideContent(texts.joinToString("\n"), images))
                }
            }
            list
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(8.dp)) {
        for ((index, slide) in slides.withIndex()) {
            Text("Slide ${index + 1}", modifier = Modifier.padding(vertical = 8.dp))
            Text(slide.text, modifier = Modifier.fillMaxWidth())
            for (img in slide.images) {
                // display image from bytes using Coil's AsyncImage via data URI
                val base64 = android.util.Base64.encodeToString(img, android.util.Base64.DEFAULT)
                val dataUri = "data:image/png;base64," + base64
                AsyncImage(model = dataUri, contentDescription = null, modifier = Modifier.fillMaxWidth().height(240.dp))
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

data class SlideContent(val text: String, val images: List<ByteArray>)

