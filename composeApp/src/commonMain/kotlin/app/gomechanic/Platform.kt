package app.gomechanic

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform