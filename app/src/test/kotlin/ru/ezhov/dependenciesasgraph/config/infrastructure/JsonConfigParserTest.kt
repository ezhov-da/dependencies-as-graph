package ru.ezhov.dependenciesasgraph.config.infrastructure

import org.junit.Test
import ru.ezhov.dependenciesasgraph.config.domain.model.ConfigFileType
import kotlin.test.assertTrue

internal class JsonConfigParserTest {
    @Test
    fun `parse test`() {
        val config = JsonConfigParser().parse(
            """
                {
                  "rootFolder": "/user/project",
                  "maxDepth": "2",
                  "file": {
                    "nameWithoutExtension": "dependencies",
                    "type": "SVG"
                  }
                }
            """.trimIndent()
        )

        assertTrue { config.rootFolder == "/user/project" }
        assertTrue { config.maxDepth == 2 }
        assertTrue { config.file.nameWithoutExtension == "dependencies" }
        assertTrue { config.file.type == ConfigFileType.SVG }
    }

    @Test
    fun `parse test 2`() {
        val config = JsonConfigParser().parse(
            "{\"rootFolder\": \"D:/repository/rocket-action\", \"maxDepth\": \"2\", " +
                "\"file\": {\"nameWithoutExtension\": \"dependencies\", \"type\":\"SVG\"}}"
        )

        assertTrue { config.rootFolder == "D:/repository/rocket-action" }
        assertTrue { config.maxDepth == 2 }
        assertTrue { config.file.nameWithoutExtension == "dependencies" }
        assertTrue { config.file.type == ConfigFileType.SVG }
    }
}
