package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnknownError
import java.io.IOException

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override val data: Flow<List<Post>> =
        dao.getAll().map { it.map(PostEntity::toDto) }

    override suspend fun likeById(id: Long) {
        val post = dao.getById(id)
        val liked = !post.likedByMe
        dao.likeById(id, liked)
        try {
            val response = if (liked) {
                PostsApi.service.likeById(id)
            } else {
                PostsApi.service.dislikeById(id)
            }
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
        } catch (e: IOException) {
            dao.likeById(id, !liked)
            throw NetworkError
        } catch (e: Exception) {
            dao.likeById(id, !liked)
            throw UnknownError
        }
    }

    override suspend fun removeById(id: Long) {
        val post = dao.getById(id)
        dao.removeById(id)
        try {
            val response = PostsApi.service.removeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
        } catch (e: IOException) {
            dao.insert(PostEntity.fromDto(post.toDto()))
            throw NetworkError
        } catch (e: Exception) {
            dao.insert(PostEntity.fromDto(post.toDto()))
            throw UnknownError
        }
    }

    override suspend fun saveContent(content: String) {
        val post = Post(
            id = 0,
            content = content,
            authorId = 0,
            published = System.currentTimeMillis(),
            likedByMe = false,
            likes = 0
        )
        val entity = PostEntity.fromDto(post.copy(id = System.currentTimeMillis()))
        dao.insert(entity)
        try {
            val response = PostsApi.service.save(post)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}
