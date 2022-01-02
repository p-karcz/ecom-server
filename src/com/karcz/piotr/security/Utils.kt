package com.karcz.piotr.security

import at.favre.lib.crypto.bcrypt.BCrypt
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies

fun hash(value: String): String = BCrypt
    .with(LongPasswordStrategies.truncate(BCrypt.Version.VERSION_2A))
    .hashToString(6, value.toCharArray())

fun compareToHash(value: String, hash: String): Boolean = BCrypt
    .verifyer(BCrypt.Version.VERSION_2A, LongPasswordStrategies.truncate(BCrypt.Version.VERSION_2A))
    .verify(value.toCharArray(), hash).verified
