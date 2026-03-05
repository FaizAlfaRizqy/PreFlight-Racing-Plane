package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

// Data Model
data class ChecklistItem(
    val id: Int,
    val category: String,
    val task: String,
    val isChecked: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CekiBiantaraApp()
            }
        }
    }
}

@Composable
fun CekiBiantaraApp() {
    var currentScreen by remember { mutableStateOf("home") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentScreen) {
            "home" -> HomeScreen(onStart = { currentScreen = "checklist" })
            "checklist" -> ChecklistScreen(onBack = { currentScreen = "home" })
        }
    }
}

@Composable
fun HomeScreen(onStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Ceki Biantara",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "UAV Pre-Flight Checklist",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("MULAI PENGECEKAN", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistScreen(onBack: () -> Unit) {
    val checklist = remember {
        mutableStateListOf(
            ChecklistItem(1, "Dokumentasi", "Rencana misi & waypoint sudah diupload"),
            ChecklistItem(2, "Dokumentasi", "Firmware & parameter autopilot diverifikasi"),
            ChecklistItem(3, "Airframe", "Area terbang aman & clear obstacle"),
            ChecklistItem(4, "Airframe", "Sayap, fuselage, ekor tidak retak"),
            ChecklistItem(5, "Airframe", "Control horn & linkage tidak longgar"),
            ChecklistItem(6, "Airframe", "Propeller terpasang kuat & tidak retak"),
            ChecklistItem(7, "Airframe", "Landing gear kuat & lurus (jika ada)"),
            ChecklistItem(8, "Power & Elektronik", "Baterai penuh, voltase sesuai"),
            ChecklistItem(9, "Power & Elektronik", "Konektor power & ESC tidak longgar"),
            ChecklistItem(10, "Power & Elektronik", "ESC & motor berfungsi normal"),
            ChecklistItem(11, "Power & Elektronik", "Servo bergerak lancar"),
            ChecklistItem(12, "Autopilot & Sensor", "IMU & gyro terkalibrasi"),
            ChecklistItem(13, "Autopilot & Sensor", "Compass kalibrasi sukses"),
            ChecklistItem(14, "Autopilot & Sensor", "GPS lock > 8 satelit"),
            ChecklistItem(15, "Autopilot & Sensor", "Airspeed sensor berfungsi (jika ada)"),
            ChecklistItem(16, "Autopilot & Sensor", "Mode flight sudah dicek (Manual, Auto, RTL)"),
            ChecklistItem(17, "Radio & Ground Station", "Remote control bind & range test ok"),
            ChecklistItem(18, "Radio & Ground Station", "Failsafe RTH aktif"),
            ChecklistItem(19, "Radio & Ground Station", "Telemetry link stabil"),
            ChecklistItem(20, "Radio & Ground Station", "Antena ground station & pesawat ok"),
            ChecklistItem(21, "Kontrol Permukaan", "Aileron bergerak sesuai input"),
            ChecklistItem(22, "Kontrol Permukaan", "Elevator bergerak sesuai input"),
            ChecklistItem(23, "Kontrol Permukaan", "Rudder bergerak sesuai input"),
            ChecklistItem(24, "Kontrol Permukaan", "Throttle responsif"),
            ChecklistItem(25, "Final Pre-Launch", "Mode awal sesuai (Manual/Stabilize/Auto)"),
            ChecklistItem(26, "Final Pre-Launch", "Waypoint & ketinggian dicek"),
            ChecklistItem(27, "Final Pre-Launch", "Area takeoff & jalur aman"),
            ChecklistItem(28, "Final Pre-Launch", "Timer misi dicatat"),
            ChecklistItem(29, "Final Pre-Launch", "Pilot & crew siap")
        )
    }

    var showSuccessDialog by remember { mutableStateOf(false) }
    val allChecked = checklist.all { it.isChecked }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(48.dp)) },
            title = { Text(text = "Pengecekan Selesai", fontWeight = FontWeight.Bold) },
            text = { Text("Seluruh sistem UAV telah diverifikasi. Pesawat siap untuk lepas landas secara aman.", textAlign = TextAlign.Center) },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onBack() // Kembali ke halaman utama
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pre-Flight Checklist", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Batal")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                val grouped = checklist.groupBy { it.category }
                grouped.forEach { (category, items) ->
                    item {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(items) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = { checked ->
                                    val index = checklist.indexOfFirst { it.id == item.id }
                                    if (index != -1) {
                                        checklist[index] = item.copy(isChecked = checked)
                                    }
                                }
                            )
                            Text(
                                text = item.task,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = { showSuccessDialog = true },
                    enabled = allChecked,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (allChecked) Color(0xFF2E7D32) else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        if (allChecked) "SIAP TERBANG!" else "Lengkapi Checklist",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
