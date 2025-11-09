
# Reader'z (Extended v2)

This version adds:
- EPUB viewer implemented using epublib-core (lightweight)
- Improved PPTX rendering: extracts slide text and embedded images and displays them in Compose

Notes:
- EPUB uses epublib-core to read chapters and render HTML content into a WebView.
- PPTX rendering extracts text from text shapes and embedded images. It does not perform full visual fidelity rendering (AWT-based rendering unavailable on Android).
- Continue to use pdfbox-android and Apache POI for PDF and DOCX/PPTX parsing respectively.
