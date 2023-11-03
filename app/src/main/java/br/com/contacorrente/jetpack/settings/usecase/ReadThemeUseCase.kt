package br.com.contacorrente.jetpack.settings.usecase

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.constants.AppThemeOptions
import br.com.contacorrente.framework.mapper.IIntToAppThemeOptionsMapper
import br.com.contacorrente.infrastructure.repository.IThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IReadThemeUseCase {
    fun execute(): Flow<CustomState<AppThemeOptions>>
}

class ReadThemeUseCase(
    private val intToAppThemeOptionsMapper: IIntToAppThemeOptionsMapper,
    private val themeRepository: IThemeRepository
) : IReadThemeUseCase {
    override fun execute(): Flow<CustomState<AppThemeOptions>> {
        return themeRepository.readTheme().map {
            CustomState.Success(
                intToAppThemeOptionsMapper.map(it)
            )
        }
    }
}