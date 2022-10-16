package com.base.jwt.service

import com.base.jwt.TokenClaim
import com.base.jwt.TokenConfig

interface TokenService {
    fun generateToken(
        config: TokenConfig,
        vararg claims: TokenClaim
    ): String
}