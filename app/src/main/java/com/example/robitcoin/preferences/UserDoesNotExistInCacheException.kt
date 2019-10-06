package com.nike.adapt.util.preferences

data class UserDoesNotExistInCacheException(override val message: String = "User does not exist in shared preference cache") : Exception(message)