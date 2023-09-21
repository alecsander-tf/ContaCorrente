package br.com.contacorrente

import br.com.contacorrente.jetpack.login.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ContaCorrenteModule {

    @Provides
    fun provideLoginUseCase(): LoginUseCase = LoginUseCase()

}