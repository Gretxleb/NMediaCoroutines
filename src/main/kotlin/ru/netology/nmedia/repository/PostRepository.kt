package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Post

interface PostRepository {
    val data: Flow<List<Post>>

    suspend fun likeById(id: Long)

    suspend fun removeById(id: Long)

    suspend fun saveContent(content: String)
}