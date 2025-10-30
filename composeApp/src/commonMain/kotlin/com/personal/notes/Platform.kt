package com.personal.notes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform