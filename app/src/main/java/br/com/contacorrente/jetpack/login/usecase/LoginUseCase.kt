package br.com.contacorrente.jetpack.login.usecase

import br.com.contacorrente.base.CustomState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.internal.wait
import java.lang.Thread.sleep

internal interface ILoginUseCase {
    fun execute(email: String, password: String): Flow<CustomState<Boolean>>
}

class LoginUseCase : ILoginUseCase {
    override fun execute(email: String, password: String): Flow<CustomState<Boolean>> {
        return flow {
            emit(CustomState.Loading)

            sleep(2000)

            emit(CustomState.Success(true))
        }.catch {
            emit(CustomState.Error("Error"))
        }
    }
}