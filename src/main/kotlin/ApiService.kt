import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/posts")
    suspend fun getPosts(): List<Post>

    @GET("api/posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Long): List<Comment>

    @GET("api/authors/{id}")
    suspend fun getAuthor(@Path("id") id: Long): Author
}
