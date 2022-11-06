package ru.ezhov.dependenciesasgraph.config.infrastructure

data class ConfigJson(
    val rootFolder: String,
    val maxDepth: Int?,
    val file: ConfigFileJson?,
)
