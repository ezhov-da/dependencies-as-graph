# Dependencies as graph

Create graph dependencies.

Maven supported.

Input param - JSON:

```json
{
  "rootFolder": "/user/project",
  "maxDepth": "2",
  "file": {
    "nameWithoutExtension": "dependencies",
    "type": "SVG"
  }
}
```

Example:

```text
"{\"rootFolder\": \"D:/repository/test\", \"maxDepth\": \"2\", \"file\": {\"nameWithoutExtension\": \"dependencies\", \"type\":\"SVG\"}}"
```

| Name                      | Reqired     | Default      |
|---------------------------|-------------|--------------|
| rootFolder                | *           |              |
| maxDepth                  |             | 2            |
| file.nameWithoutExtension |             | dependencies |
| file.type                 | * (SVG/PNG) | SVG          |
