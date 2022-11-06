package ru.ezhov.dependenciesasgraph.config.domain

import ru.ezhov.dependenciesasgraph.config.domain.model.Config

interface ConfigParser {
    fun parse(source: String): Config
}
