package br.com.contacorrente.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseServiceNetwork : CoroutineScope {
    private val businessJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + businessJob

    fun <T> stateApi(function: suspend () -> T): Flow<CustomState<T>> = callbackFlow {
        trySend(CustomState.Loading)
        trySend(CustomState.Success(function.invoke()))
    }.catch {
        Timber.e("StateApiError - $it")
        it.message?.let { message ->
            emit(CustomState.Error(message))
        }
    }

    fun <T> Call<T>.customEnqueue(): Flow<CustomState<T>> {

        val channel = Channel<CustomState<T>>()

        CoroutineScope(coroutineContext).launch {

            channel.send(CustomState.Loading)

            this@customEnqueue.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful && response.body() != null) {
                        channel.trySend(CustomState.Success(response.body()!!))
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            channel.trySend(CustomState.ApiError(errorBody.string()))
                        } else {
                            channel.trySend(CustomState.Error("Internal Server Error"))
                        }
                    }
                    channel.close()
                }

                override fun onFailure(call: Call<T>, throwable: Throwable) {
                    Timber.e(throwable)
                    channel.trySend(CustomState.Error("Internal Server Error"))
                    channel.close()
                }
            })
        }
        return channel.consumeAsFlow()
    }
}

private fun convertStatusErrorKt() {

}


/*    Timber.e("StateApiError - $it")
    it.message?.let { message ->
        channel.trySend(CustomState.Error(message))
    }
    channel.close()*/