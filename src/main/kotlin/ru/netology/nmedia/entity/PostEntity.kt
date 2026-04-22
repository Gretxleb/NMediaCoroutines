package ru.netology.nmedia.entity

import ru.netology.nmedia.dto.Post

data class PostEntity(
    val id: Long,
    val authorId: Long,
    val content: String,
    val published: Long,
    val likedByMe: Boolean,
    val likes: Int = 0
) {
    fun toDto() = Post(id, authorId, content, published, likedByMe, likes)

    companion object {
        fun fromDto(dto: Post) = PostEntity(dto.id, dto.authorId, dto.content, dto.published, dto.likedByMe, dto.likes)
    }
}
