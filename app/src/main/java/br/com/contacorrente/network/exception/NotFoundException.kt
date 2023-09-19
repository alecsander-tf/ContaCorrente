package br.com.contacorrente.network.exception

import java.io.IOException

data class NotFoundException(override val message: String?) : IOException()