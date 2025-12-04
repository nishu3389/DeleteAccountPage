package app.gomechanic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import deleteaccountpage.composeapp.generated.resources.Res
import deleteaccountpage.composeapp.generated.resources.app_icon_rounded
import deleteaccountpage.composeapp.generated.resources.delete
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        var phone by remember { mutableStateOf("") }
        var phoneError by remember { mutableStateOf<String?>(null) }

        var isLoading by remember { mutableStateOf(false) }
        var showSuccessDialog by remember { mutableStateOf(false) }

        val scope = rememberCoroutineScope()

        fun validateAndSubmit() {

            // Validation
            when {
                phone.isEmpty() -> {
                    phoneError = "Please enter mobile number"
                    return
                }

                phone.length != 10 -> {
                    phoneError = "Mobile number must be 10 digits"
                    return
                }

                else -> phoneError = null
            }

            // Submit process
            isLoading = true

            scope.launch {
                delay(2000)
                isLoading = false
                showSuccessDialog = true
                phone = ""            // clear input
            }
        }

        Box(Modifier.fillMaxSize()) {

            DeleteAccountPage(
                phone = phone,
                phoneError = phoneError,
                onPhoneChange = {
                    phoneError = null
                    phone = it
                },
                onSubmit = { validateAndSubmit() }
            )

            if (isLoading) {
                Box(
                    Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF06B86C))
                }
            }

            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { showSuccessDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showSuccessDialog = false }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Success") },
                    text = { Text("Your delete account request has been submitted successfully.") }
                )
            }
        }
    }
}


@Composable
fun DeleteAccountPage(
    phone: String,
    phoneError: String?,
    onPhoneChange: (String) -> Unit,
    onSubmit: () -> Unit
) {

    Column(
        Modifier.fillMaxSize().background(Color(0xFFF1F7F6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ------------------ Header ------------------
        Box(
            Modifier.background(Color.White).fillMaxWidth().padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.app_icon_rounded),
                contentDescription = "Company Logo",
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        val scrollState = rememberScrollState()

        // ------------------ Card ------------------
        Column(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(start = 20.dp, end = 20.dp, bottom = 15.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Delete Account",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Red,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Box(
                Modifier.background(Color(0xFFF1F7F6), shape = RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(Res.drawable.delete),
                    contentDescription = "Delete Icon",
                    Modifier.padding(10.dp)
                )
            }

            Spacer(Modifier.height(15.dp))

            Text(
                text = "We are sorry to see you go.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Please enter your registered mobile number with\nGomechanic",
                fontSize = 12.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(25.dp))


            // ------------------ Phone Input (Fixed Width Alignment Wrapper) ------------------
            val inputWidth = 260.dp   // 🔥 adjust if needed

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(inputWidth)
            ) {

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        if (it.length <= 10 && it.all { ch -> ch.isDigit() }) {
                            onPhoneChange(it)
                        }
                    },
                    label = { Text("Registered Mobile Number") },
                    isError = phoneError != null,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(), // inside fixed width column
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        // Border colors
                        focusedIndicatorColor = if (phoneError != null) Color.Red else Color(0xFF00A86B),
                        unfocusedIndicatorColor = if (phoneError != null) Color.Red else Color(0xFF00A86B),

                        // Label & cursor
                        focusedLabelColor = if (phoneError != null) Color.Red else Color(0xFF00A86B),
                        cursorColor = Color(0xFF00A86B),

                        // ALWAYS WHITE background
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White
                    )
                )

                // Error Text
                if (phoneError != null) {
                    Text(
                        text = phoneError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.Start)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ------------------ Submit Button ------------------
            Button(
                onClick = { onSubmit() },
                modifier = Modifier.wrapContentWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06B86C))
            ) {
                Text(
                    text = "Submit Request",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Your account will be deleted within 48 hours after verification.",
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
