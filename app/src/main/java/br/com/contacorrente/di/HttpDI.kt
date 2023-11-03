package br.com.contacorrente.di

import br.com.contacorrente.constants.ContaCorrenteConstants
import br.com.contacorrente.framework.network.service.IUserServiceAPI
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val httpDI = module {

    factory(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.HTTP_CLIENT)) {
        provideOkHttpClient()
    }

    factory<Converter.Factory>(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.GSON_CONVERTER)) {
        provideGsonConverterFactory()
    }

    factory(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.USER_SERVICE_API)) {
        provideUserServiceAPI(
            retrofit = get(named(ContaCorrenteConstants.NamedHttpKoin.RETROFIT)),
        )
    }

    factory(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.RETROFIT)) {
        provideRetrofit(
            okHttpClient = get(named(ContaCorrenteConstants.NamedHttpKoin.HTTP_CLIENT)),
            converterFactory = get(named(ContaCorrenteConstants.NamedHttpKoin.GSON_CONVERTER))
        )
    }
}

private fun provideUserServiceAPI(retrofit: Retrofit) = retrofit.create(IUserServiceAPI::class.java)

private fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory) =
    Retrofit.Builder()
        .baseUrl(ContaCorrenteConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()


private fun provideOkHttpClient() = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor {
        Timber.tag("OkHttp").d(it)
    }.setLevel(HttpLoggingInterceptor.Level.BODY))
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .build()

private fun provideGsonConverterFactory() = GsonConverterFactory.create(GsonBuilder().create())
