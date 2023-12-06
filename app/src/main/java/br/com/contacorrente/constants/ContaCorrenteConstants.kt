package br.com.contacorrente.constants

object ContaCorrenteConstants {
    const val BASE_URL = "http://192.168.1.118:8080/api/v1/"

    object Endpoint {
        const val CLIENT = "client/"
        const val TRANSFERENCE = "transference/"
    }

    object NamedHttpKoin {

        const val USER_SERVICE_API = "USER_SERVICE_API"
        const val TRANSFER_SERVICE_API = "TRANSFER_SERVICE_API"

        const val RETROFIT_CLIENT = "RETROFIT_CLIENT"
        const val RETROFIT_TRANSFERENCE = "RETROFIT_TRANSFERENCE"
        const val HTTP_CLIENT = "HTTP_CLIENT"
        const val GSON_CONVERTER = "GSON_CONVERTER"
    }

    object IntentExtras {
        const val USER_EMAIL = "USER_EMAIL"
    }
}