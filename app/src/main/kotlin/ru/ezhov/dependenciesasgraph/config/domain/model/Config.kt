package ru.ezhov.dependenciesasgraph.config.domain.model

@Suppress("DataClassPrivateConstructor")
data class Config private constructor(
    val rootFolder: String,
    val maxDepth: Int,
    val file: ConfigFile,
) {
    companion object {
        fun of(
            rootFolder: String,
            maxDepth: Int?,
            file: ConfigFile,
        ): Config {
            val maxDepthResult = maxDepth ?: 2
            return Config(rootFolder = rootFolder, maxDepth = maxDepthResult, file = file)
        }
    }
}
