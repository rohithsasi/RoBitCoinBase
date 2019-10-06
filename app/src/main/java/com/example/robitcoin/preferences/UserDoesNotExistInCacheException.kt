package com.example.robitcoin.preferences

data class UserDoesNotExistInCacheException(override val message: String = "User does not exist in shared preference cache") : Exception(message)