package app.gomechanic

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}

