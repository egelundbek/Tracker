package com.example.tracker.composables

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun SubmitButton(
    onSubmit: () -> Boolean,
    successMessage: String,
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Button(
        onClick = {
            if (onSubmit()) {
                Toast.makeText(context, successMessage, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        },
        modifier = modifier
    ) {
        Text("Submit")
    }
}
