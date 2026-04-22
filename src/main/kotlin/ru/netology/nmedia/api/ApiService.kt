package ru.netology.nmedia.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.Comment
import ru.netology.nmedia.dto.Author

interface ApiService {
    @GET("api/posts")
    suspend fun getPosts(): List<Post>

    @GET("api/posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Long): List<Comment>

    @GET("api/authors/{id}")
    suspend fun getAuthor(@Path("id") id: Long): Author

    @POST("api/posts/{id}/likes")
    suspend fun likeById(@Path("id") id: Long): Response<Post>

    @DELETE("api/posts/{id}/likes")
    suspend fun dislikeById(@Path("id") id: Long): Response<Post>

    @DELETE("api/posts/{id}")
    suspend fun removeById(@Path("id") id: Long): Response<Unit>

    @POST("api/posts")
    suspend fun save(@Body post: Post): Response<Post>
}
