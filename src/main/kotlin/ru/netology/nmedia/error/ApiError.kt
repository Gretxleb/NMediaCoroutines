package ru.netology.nmedia.error

class ApiError(val code: Int, message: String) : RuntimeException(message)