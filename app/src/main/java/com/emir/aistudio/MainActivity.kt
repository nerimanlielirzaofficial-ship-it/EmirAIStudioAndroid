package com.emir.aistudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF090B16)
private val Panel = Color(0xFF151A2B)
private val Purple = Color(0xFF9B65FF)
private val Cyan = Color(0xFF26D9E8)
private val White = Color(0xFFF4F4FF)
private val Muted = Color(0xFF969BB5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StudioScreen()
            }
        }
    }
}

@Composable
fun StudioScreen() {
    var prompt by remember { mutableStateOf("") }
    var style by remember { mutableStateOf("3D Animasyon") }
    var duration by remember { mutableStateOf("10 saniye") }
    var message by remember { mutableStateOf("Hayalindeki videoyu tarif et!") }

    val gradient = Brush.horizontalGradient(
        listOf(Purple, Cyan)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("EMİR AI", color = White,
                    fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
                Text("STUDIO", color = Cyan,
                    fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .background(Panel, RoundedCornerShape(14.dp))
                    .border(1.dp, Purple, RoundedCornerShape(14.dp))
                    .padding(horizontal = 13.dp, vertical = 9.dp)
            ) {
                Text("✦ AI STÜDYO", color = White, fontSize = 12.sp)
            }
        }

        Spacer(Modifier.height(30.dp))

        Text("HAYALİNİ", color = White,
            fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
        Text("VİDEOYA DÖNÜŞTÜR", color = Purple,
            fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(Modifier.height(8.dp))
        Text(
            "Yapay zekâ ile kendi videolarını oluştur.",
            color = Muted, fontSize = 14.sp
        )

        Spacer(Modifier.height(26.dp))

        Text("VİDEO AÇIKLAMASI", color = White,
            fontSize = 14.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(155.dp),
            placeholder = {
                Text(
                    "Örnek: Ormanda kaybolan küçük bir çocuğun macerası...",
                    color = Muted
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = White,
                unfocusedTextColor = White,
                focusedBorderColor = Purple,
                unfocusedBorderColor = Color(0xFF343B55),
                cursorColor = Cyan
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(Modifier.height(23.dp))

        Text("ANİMASYON TARZI", color = White,
            fontWeight = FontWeight.Bold, fontSize = 14.sp)

        Spacer(Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf("3D Animasyon", "2D Animasyon").forEach { item ->
                val selected = style == item
                Button(
                    onClick = { style = item },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selected) Purple else Panel
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(item, fontSize = 12.sp)
                }
            }
        }

        Spacer(Modifier.height(23.dp))

        Text("VİDEO SÜRESİ", color = White,
            fontWeight = FontWeight.Bold, fontSize = 14.sp)

        Spacer(Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("5 saniye", "10 saniye", "20 saniye").forEach { item ->
                val selected = duration == item
                Button(
                    onClick = { duration = item },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selected) Color(0xFF31516A) else Panel
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(item, fontSize = 11.sp)
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = {
                message = if (prompt.isBlank()) {
                    "Önce video açıklaması yazmalısın."
                } else {
                    "Video isteğin hazırlandı! API bağlantısı sonraki aşamada eklenecek."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "✦  VİDEO OLUŞTUR",
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(Modifier.height(13.dp))

        Text(
            message,
            color = Cyan,
            fontSize = 13.sp
        )

        Spacer(Modifier.height(28.dp))

        HorizontalDivider(color = Color(0xFF292F45))

        Spacer(Modifier.height(18.dp))

        Text("EMİR AI STUDIO", color = White,
            fontWeight = FontWeight.Bold)
        Text("Hayal et. Üret. Paylaş.", color = Muted,
            fontSize = 12.sp)

        Spacer(Modifier.height(25.dp))
    }
}
