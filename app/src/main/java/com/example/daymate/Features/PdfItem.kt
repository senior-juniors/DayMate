package com.example.daymate.Features

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PdfItemCard(pdf: PdfFile, context: Context) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.12f)),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Main clickable area for opening
            Column(modifier = Modifier.weight(1f).clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pdf.url))
                context.startActivity(intent)
            }) {
                Text(pdf.name, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                Text("Tap to view", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
            }

            // Share
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Study Material: ${pdf.name}\nLink: ${pdf.url}")
                }
                context.startActivity(Intent.createChooser(intent, "Share via"))
            }) {
                Icon(Icons.Default.Share, "Share", tint = Color.White)
            }

            // Download
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pdf.downloadUrl))
                context.startActivity(intent)
            }) {
                Icon(Icons.Default.Download, "Download", tint = Color.White)
            }
        }
    }
}