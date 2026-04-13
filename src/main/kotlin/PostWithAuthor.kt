data class CommentWithAuthor(
    val comment: Comment,
    val author: Author
)


data class PostWithAuthor(
    val post: Post,
    val author: Author,
    val comments: List<CommentWithAuthor>
)
