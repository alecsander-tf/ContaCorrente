package br.com.contacorrente.constants

import androidx.lifecycle.MediatorLiveData

object Singleton {
    var AppTheme = MediatorLiveData(AppThemeOptions.SYSTEM_DEFAULT)
}