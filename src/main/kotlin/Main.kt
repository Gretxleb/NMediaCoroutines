package ru.netology.nmedia

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.PostWithAuthor
import ru.netology.nmedia.dto.CommentWithAuthor

fun main() = runBlocking {
    val api = PostsApi.service
    val time = measureTimeMillis {
        val posts = api.getPosts()
        val result = posts.map { post ->
            async {
                val authorDeferred = async { api.getAuthor(post.authorId) }
                val commentsDeferred = async {
                    val comments = api.getComments(post.id)
                    comments.map { comment ->
                        async {
                            val author = api.getAuthor(comment.authorId)
                            CommentWithAuthor(comment, author)
                        }
                    }.awaitAll()
                }
                PostWithAuthor(post, authorDeferred.await(), commentsDeferred.await())
            }
        }.awaitAll()

        result.forEach { println(it) }
    }
    println("Time: $time ms")
}
