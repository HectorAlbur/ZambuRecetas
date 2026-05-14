package com.example.zamburecetas.core.repositories

import com.example.zamburecetas.core.ResponseService
import com.example.zamburecetas.onboarding.personal.model.UserProfile

interface UserService {
    suspend fun saveUserInfo(userProfile: UserProfile): ResponseService<Unit>
}