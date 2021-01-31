package com.vsa.filmoteca.domain.usecase

import com.vsa.filmoteca.data.source.repository.AppConfigRepository
import javax.inject.Inject

class InitAppConfigUseCase @Inject constructor(private val appConfigRepository: AppConfigRepository) {

    fun init() {
        appConfigRepository.initAppConfig()
    }

}