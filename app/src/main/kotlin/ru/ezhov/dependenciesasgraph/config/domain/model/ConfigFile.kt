package ru.ezhov.dependenciesasgraph.config.domain.model

@Suppress("DataClassPrivateConstructor")
data class ConfigFile private constructor(
    val nameWithoutExtension: String = "dependencies",
    val type: ConfigFileType = ConfigFileType.SVG,
) {
    companion object {
        fun of(
            nameWithoutExtension: String?,
            type: ConfigFileType?,
        ): ConfigFile {
            val nameWithoutExtensionResult = nameWithoutExtension ?: "dependencies"
            val typeResult = type ?: ConfigFileType.SVG

            return ConfigFile(nameWithoutExtension = nameWithoutExtensionResult, type = typeResult)
        }
    }
}
