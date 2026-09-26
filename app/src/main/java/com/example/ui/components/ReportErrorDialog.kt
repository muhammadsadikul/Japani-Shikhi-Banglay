package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBg
import com.example.ui.theme.JapanRed
import com.example.ui.theme.JapanRedLight
import com.example.ui.theme.LightBg
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900

@Composable
fun ReportErrorButton(
    itemName: String,
    onReportSubmitted: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    TextButton(
        onClick = { showDialog = true },
        modifier = modifier.testTag("report_error_button")
    ) {
        Icon(
            imageVector = Icons.Filled.BugReport,
            contentDescription = "ভুল রিপোর্ট করুন",
            tint = Slate500,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "ভুল আছে?",
            fontSize = 12.sp,
            color = Slate500,
            fontWeight = FontWeight.Medium
        )
    }

    if (showDialog) {
        ReportErrorDialog(
            itemName = itemName,
            onDismiss = { showDialog = false },
            onSubmit = { message ->
                showDialog = false
                onReportSubmitted(message)
            }
        )
    }
}

@Composable
fun ReportErrorDialog(
    itemName: String,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var selectedReason by remember { mutableStateOf("বানান বা ফুরিগানা ভুল") }
    var userNote by remember { mutableStateOf("") }
    val reasons = listOf(
        "বানান বা ফুরিগানা ভুল",
        "বাংলা অর্থ বা ব্যাখ্যায় সমস্যা",
        "ইংরেজি অনুবাদে ত্রুটি",
        "উচ্চারণ সমস্যা",
        "অন্যান্য"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.BugReport,
                    contentDescription = null,
                    tint = JapanRed,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ভুল রিপোর্ট করুন",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Slate900
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "আইটেম: $itemName",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = JapanRed
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "সমস্যার ধরন নির্বাচন করুন:",
                    fontSize = 13.sp,
                    color = Slate700
                )
                Spacer(modifier = Modifier.height(6.dp))

                reasons.forEach { reason ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    ) {
                        RadioButton(
                            selected = selectedReason == reason,
                            onClick = { selectedReason = reason },
                            colors = RadioButtonDefaults.colors(selectedColor = JapanRed)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = reason,
                            fontSize = 13.sp,
                            color = Slate900
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = userNote,
                    onValueChange = { userNote = it },
                    placeholder = { Text("অতিরিক্ত মন্তব্য (ঐচ্ছিক)", fontSize = 12.sp, color = Slate500) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = JapanRed,
                        unfocusedBorderColor = BorderSubtle
                    ),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val summary = "[$selectedReason] $itemName: ${if (userNote.isNotBlank()) userNote else "কোনো নোট নেই"}"
                    onSubmit(summary)
                },
                colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("রিপোর্ট পাঠান", color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("বাতিল", color = Slate700)
            }
        },
        containerColor = CardBg,
        shape = RoundedCornerShape(18.dp)
    )
}
