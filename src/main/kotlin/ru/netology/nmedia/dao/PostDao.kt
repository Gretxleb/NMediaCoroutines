package ru.netology.nmedia.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.netology.nmedia.entity.PostEntity

class PostDao {
    private val posts = MutableStateFlow<List<PostEntity>>(emptyList())

    fun getAll(): Flow<List<PostEntity>> = posts.asStateFlow()

    suspend fun getById(id: Long): PostEntity {
        return posts.value.first { it.id == id }
    }

    suspend fun likeById(id: Long, liked: Boolean) {
        posts.value = posts.value.map {
            if (it.id == id) it.copy(likedByMe = liked) else it
        }
    }

    suspend fun removeById(id: Long) {
        posts.value = posts.value.filter { it.id != id }
    }

    suspend fun insert(post: PostEntity) {
        val updated = posts.value.filter { it.id != post.id } + listOf(post)
        posts.value = updated
    }
}
