package br.com.contacorrente.framework.mapper

import br.com.contacorrente.constants.AppThemeOptions

interface IIntToAppThemeOptionsMapper {
    fun map(int: Int): AppThemeOptions
}

class IntToAppThemeOptionsMapper : IIntToAppThemeOptionsMapper {
    override fun map(int: Int): AppThemeOptions {
        return AppThemeOptions.values().find {
            it.intValue == int
        } ?: AppThemeOptions.SYSTEM_DEFAULT
    }
}