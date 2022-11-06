package ru.ezhov.dependenciesasgraph.config.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.ezhov.dependenciesasgraph.config.domain.ConfigParser
import ru.ezhov.dependenciesasgraph.config.domain.model.Config
import ru.ezhov.dependenciesasgraph.config.domain.model.ConfigFile
import ru.ezhov.dependenciesasgraph.config.domain.model.ConfigFileType

class JsonConfigParser : ConfigParser {
    override fun parse(source: String): Config {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(source, ConfigJson::class.java).toDomain()
    }
}

private fun ConfigJson.toDomain() = Config.of(
    rootFolder = this.rootFolder,
    maxDepth = this.maxDepth,
    file = ConfigFile.of(
        nameWithoutExtension = this.file?.nameWithoutExtension,
        type = when (this.file?.type) {
            null -> null
            ConfigFileTypeJson.PNG -> ConfigFileType.PNG
            ConfigFileTypeJson.SVG -> ConfigFileType.SVG
        }
    )
)
