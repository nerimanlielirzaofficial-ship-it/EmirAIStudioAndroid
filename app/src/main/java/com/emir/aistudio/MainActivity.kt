package com.emir.aistudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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

private val BG = Color(0xFF080B14)
private val PANEL = Color(0xFF141C2B)
private val PURPLE = Color(0xFF9365FF)
private val CYAN = Color(0xFF35D9E8)
private val WHITE = Color(0xFFF5F5FF)
private val MUTED = Color(0xFF929CB2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BuilderScreen()
            }
        }
    }
}

@Composable
fun BuilderScreen() {
    var fileName by remember { mutableStateOf("Henüz şematik seçilmedi") }
    var status by remember { mutableStateOf("Şematik bekleniyor") }
    var running by remember { mutableStateOf(false) }
    var progress by remember { mutableIntStateOf(0) }

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            fileName = uri.lastPathSegment ?: uri.toString()
            status = "Dosya seçildi. Format kontrolü bekleniyor."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    "EMIR BUILDER",
                    color = WHITE,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    "AI CONSTRUCTION SYSTEM",
                    color = CYAN,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text("◆ ONLINE", color = CYAN, fontSize = 12.sp)
        }

        Spacer(Modifier.height(28.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = PANEL),
            shape = RoundedCornerShape(22.dp)
        ) {
            Column(Modifier.padding(18.dp)) {
                Text("OTOMATİK FARM İNŞAATI", color = WHITE,
                    fontSize = 21.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(7.dp))
                Text(
                    "Şematiğini yükle. Malzemelerini planla. İnşaatını yönet.",
                    color = MUTED, fontSize = 13.sp
                )
                Spacer(Modifier.height(20.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(
                            Brush.horizontalGradient(listOf(PURPLE, CYAN)),
                            RoundedCornerShape(10.dp)
                        )
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        SectionTitle("01  ŞEMATİK DOSYASI")

        Card(
            colors = CardDefaults.cardColors(containerColor = PANEL),
            shape = RoundedCornerShape(17.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Dosyan", color = MUTED, fontSize = 12.sp)
                Spacer(Modifier.height(7.dp))
                Text(fileName, color = WHITE, fontSize = 13.sp)
                Spacer(Modifier.height(14.dp))
                Button(
                    onClick = {
                        filePicker.launch(
                            arrayOf(
                                "*/*"
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PURPLE
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("ŞEMATİK DOSYASI SEÇ")
                }
                Text(
                    "Desteklenecek formatlar: .schem, .schematic, .mcstructure",
                    color = MUTED, fontSize = 10.sp
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        SectionTitle("02  SUNUCU BAĞLANTISI")

        InfoCard("Sunucu", "DonutSMP")
        InfoCard("Platform", "Minecraft Bedrock")
        InfoCard("Cihaz", "Android")

        Spacer(Modifier.height(20.dp))

        SectionTitle("03  İNŞAAT DURUMU")

        Card(
            colors = CardDefaults.cardColors(containerColor = PANEL),
            shape = RoundedCornerShape(17.dp)
        ) {
            Column(Modifier.padding(17.dp)) {
                Text("İlerleme", color = MUTED, fontSize = 12.sp)
                Spacer(Modifier.height(7.dp))
                Text("$progress%", color = CYAN,
                    fontSize = 34.sp, fontWeight = FontWeight.ExtraBold)
                LinearProgressIndicator(
                    progress = { progress / 100f },
                    modifier = Modifier.fillMaxWidth(),
                    color = CYAN,
                    trackColor = Color(0xFF30394D)
                )
                Spacer(Modifier.height(14.dp))
                Text(status, color = WHITE, fontSize = 13.sp)
                Spacer(Modifier.height(8.dp))
                Text("Blok yerleştirme: Henüz başlamadı",
                    color = MUTED, fontSize = 11.sp)
            }
        }

        Spacer(Modifier.height(20.dp))

        SectionTitle("04  KONTROL MERKEZİ")

        Button(
            onClick = {
                status = if (fileName == "Henüz şematik seçilmedi") {
                    "Önce bir şematik dosyası seç."
                } else {
                    "Dosya seçildi. Şematik analiz motoru henüz hazırlanıyor."
                }
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PURPLE
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("ŞEMATİĞİ ANALİZ ET", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = {
                    running = false
                    status = "İnşaat duraklatıldı."
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("DURDUR", color = WHITE)
            }

            OutlinedButton(
                onClick = {
                    status = "İnşaat motoru henüz hazır değil."
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("DEVAM ET", color = CYAN)
            }
        }

        Spacer(Modifier.height(25.dp))

        HorizontalDivider(color = Color(0xFF273147))

        Spacer(Modifier.height(15.dp))

        Text("EMIR BUILDER AI", color = WHITE,
            fontWeight = FontWeight.Bold)
        Text(
            "Akıllı inşaat • Şematik • Blok planlama",
            color = MUTED, fontSize = 11.sp
        )
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        color = CYAN,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(10.dp))
}

@Composable
fun InfoCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = PANEL),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = MUTED, fontSize = 13.sp)
            Text(value, color = WHITE, fontSize = 13.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}
