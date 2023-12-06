package br.com.contacorrente.di

import br.com.contacorrente.constants.ContaCorrenteConstants
import br.com.contacorrente.framework.network.service.IClientServiceAPI
import br.com.contacorrente.framework.network.service.ITransferenceServiceApi
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

val httpClientDI = module {

    factory(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.HTTP_CLIENT)) {
        provideOkHttpClient()
    }

    factory<Converter.Factory>(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.GSON_CONVERTER)) {
        provideGsonConverterFactory()
    }

    factory(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.USER_SERVICE_API)) {
        provideUserServiceAPI(
            retrofit = get(named(ContaCorrenteConstants.NamedHttpKoin.RETROFIT_CLIENT)),
        )
    }

    factory(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.RETROFIT_CLIENT)) {
        provideRetrofit(
            okHttpClient = get(named(ContaCorrenteConstants.NamedHttpKoin.HTTP_CLIENT)),
            converterFactory = get(named(ContaCorrenteConstants.NamedHttpKoin.GSON_CONVERTER)),
            endpoint = ContaCorrenteConstants.Endpoint.CLIENT
        )
    }
}

val httpTransferenceDI = module {

    factory(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.TRANSFER_SERVICE_API)) {
        provideTransferServiceAPI(
            retrofit = get(named(ContaCorrenteConstants.NamedHttpKoin.RETROFIT_TRANSFERENCE)),
        )
    }

    factory(qualifier = named(ContaCorrenteConstants.NamedHttpKoin.RETROFIT_TRANSFERENCE)) {
        provideRetrofit(
            okHttpClient = get(named(ContaCorrenteConstants.NamedHttpKoin.HTTP_CLIENT)),
            converterFactory = get(named(ContaCorrenteConstants.NamedHttpKoin.GSON_CONVERTER)),
            endpoint = ContaCorrenteConstants.Endpoint.TRANSFERENCE
        )
    }
}

private fun provideTransferServiceAPI(retrofit: Retrofit) =
    retrofit.create(ITransferenceServiceApi::class.java)

private fun provideUserServiceAPI(retrofit: Retrofit): IClientServiceAPI? =
    retrofit.create(IClientServiceAPI::class.java)

private fun provideRetrofit(
    okHttpClient: OkHttpClient, converterFactory: Converter.Factory,
    endpoint: String
) =
    Retrofit.Builder()
        .baseUrl(ContaCorrenteConstants.BASE_URL + endpoint)
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
