package ru.ezhov.dependenciesasgraph.config.infrastructure

data class ConfigFileJson(
    val nameWithoutExtension: String,
    val type: ConfigFileTypeJson,
)
