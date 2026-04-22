package ru.netology.nmedia.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.PostRepository

class PostViewModel(private val repository: PostRepository) {

    val data: Flow<*> = repository.data

    private val _state = MutableStateFlow(FeedModelState())
    val state: StateFlow<FeedModelState> = _state

    suspend fun likeById(id: Long) {
        try {
            _state.value = FeedModelState(loading = true)
            repository.likeById(id)
            _state.value = FeedModelState()
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }
    }

    suspend fun removeById(id: Long) {
        try {
            _state.value = FeedModelState(loading = true)
            repository.removeById(id)
            _state.value = FeedModelState()
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }
    }

    suspend fun save(post: String) {
        try {
            _state.value = FeedModelState(loading = true)
            repository.saveContent(post)
            _state.value = FeedModelState()
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }
    }
}
