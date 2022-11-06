package ru.ezhov.dependenciesasgraph

import net.sourceforge.plantuml.FileFormat
import net.sourceforge.plantuml.FileFormatOption
import net.sourceforge.plantuml.SourceStringReader
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import ru.ezhov.dependenciesasgraph.config.infrastructure.JsonConfigParser
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.util.stream.Collectors
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import kotlin.io.path.name

class A

fun main(args: Array<String>) {
    printVersion()

    val config = try {
        println("config '${args[0]}'")
        JsonConfigParser().parse(args[0])
    } catch (ex: Exception) {
        println("Error config")
        ex.printStackTrace()
        return
    }

    val rootFolder = File(config.rootFolder)
    val poms = pomXmls(rootFolder = rootFolder, maxDepth = config.maxDepth)
    poms.forEach { pom ->
        val mapDependencies = dependenciesMap(pom)
        writePlantUmlDiagram(
            file = File(pom.parentFile, "${config.file.nameWithoutExtension}.${config.file.type.name.lowercase()}"),
            dependencies = mapDependencies,
        )
    }
}

private fun printVersion() {
    val version = String(A::class.java.getResourceAsStream("/version").readAllBytes())
    println("version '$version'")
}

private fun pomXmls(rootFolder: File, maxDepth: Int): List<File> =
    Files.walk(rootFolder.toPath(), maxDepth)
        .filter { it.name.endsWith("pom.xml") }
        .map { it.toFile() }
        .collect(Collectors.toList())

private fun dependenciesMap(pomXml: File): Map<String, List<String>> {
    val builderFactory = DocumentBuilderFactory.newInstance()
    val builder: DocumentBuilder = builderFactory.newDocumentBuilder()

    val map = mutableMapOf<String, List<String>>()

    val xmlDocument: Document = builder.parse(pomXml)
    val xPath = XPathFactory.newInstance().newXPath()

    val artifactIdPath = "//project/artifactId[text()]"
    val artifactIdText = xPath.compile(artifactIdPath).evaluate(xmlDocument, XPathConstants.STRING) as? String

    val dependencyPath = "//dependencies/dependency/artifactId"
    val dependencyArtifactIdText = xPath.compile(dependencyPath).evaluate(xmlDocument, XPathConstants.NODESET) as? NodeList

    val dependencies = buildList<String>() {
        dependencyArtifactIdText?.let { dep ->
            (0 until dep.length).forEach { index ->
                add(dep.item(index).textContent)
            }
        }
    }

    artifactIdText?.let { name ->
        if (name.isNotEmpty()) {
            map[name] = dependencies
        }
    }

    return map.toMap()
}

private fun writePlantUmlDiagram(file: File, dependencies: Map<String, List<String>>) {
    val dep = dependencies
        .map { (k, v) ->
            v.joinToString(separator = "\n") {
                "\"$k\" ..> \"$it\""
            }
        }
        .joinToString(separator = "\n")

    val source =
        """
            @startuml
            $dep
            @enduml
        """.trimIndent()

    val reader = SourceStringReader(source)
    when (file.name.substringAfterLast(delimiter = ".")) {
        "svg" -> {
            val os = ByteArrayOutputStream()
            reader.generateImage(os, FileFormatOption(FileFormat.SVG))
            os.close()
            val svg = String(os.toByteArray(), Charset.forName("UTF-8"))
            file.writeText(svg, Charset.forName("UTF-8"))

            println("file '$file' written")
        }

        "png" -> {
            file
                .outputStream()
                .use {
                    reader.outputImage(it)

                    println("file '$file' written")
                }
        }
    }
}
