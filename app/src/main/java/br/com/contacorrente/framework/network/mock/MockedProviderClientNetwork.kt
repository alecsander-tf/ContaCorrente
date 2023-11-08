package br.com.contacorrente.framework.network

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.model.Status
import br.com.contacorrente.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody

class MockedProviderClientNetwork : IProviderClientNetwork {
    override fun login(
        email: MultipartBody.Part,
        password: MultipartBody.Part
    ): Flow<CustomState<Status>> {
        return flowOf(CustomState.Success(Status(true, "")))
    }

    override fun transfer(
        from: MultipartBody.Part,
        to: MultipartBody.Part,
        value: MultipartBody.Part
    ): Flow<CustomState<Status>> {
        return flowOf(
            CustomState.Success(
                Status(
                    true,
                    ""
                )
            )
        )
    }

    override fun getUser(userEmail: String): Flow<CustomState<User>> {
        return flowOf(
            CustomState.Success(
                User(
                    "1",
                    "Fulano",
                    "fulano@gmail.com",
                    "",
                    "",
                    "9000"
                )
            )
        )
    }
}