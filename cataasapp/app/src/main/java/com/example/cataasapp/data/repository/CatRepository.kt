package com.example.cataasapp.data.repository

import com.example.cataasapp.data.model.Operation
import java.util.UUID

class CatRepository {
    // List of all operations from the image
    fun getOperations(): List<Operation> = listOf(
        Operation(1, "Random Cat", "Just a random cat", "/cat"),
        Operation(2, "Cat by Tag", "Random cat with a specific tag", "/cat/{tag}", needsTag = true),
        Operation(3, "Random GIF Cat", "A random cat GIF", "/cat/gif"),
        Operation(4, "Cat Says", "Random cat saying some text", "/cat/says/{text}", needsText = true),
        Operation(5, "Tag + Says", "Cat with tag saying text", "/cat/{tag}/says/{text}", needsTag = true, needsText = true),
        Operation(6, "Custom Cat Says", "Cat saying text with custom font size/color", "/cat/says/{text}?", needsText = true, needsFontParams = true)
    )

    // Build the final URL based on operation and user input
    fun buildUrl(operation: Operation, tag: String = "", text: String = "", fontSize: String = "50", fontColor: String = "red"): String {
        val base = "https://cataas.com"
        var url = base + operation.endpoint


        if (operation.needsTag) {
            val safeTag = tag.ifBlank { "cute" } // default tag if empty
            url = url.replace("{tag}", safeTag)
        }
        if (operation.needsText) {
            val safeText = if (text.isBlank()) "Hello" else text
            url = url.replace("{text}", safeText)
        }
        // Remove any remaining curly braces (if placeholders not replaced)
        url = url.replace("{tag}", "cute").replace("{text}", "Hello")


        val queryParams = mutableListOf<String>()

        if (operation.needsFontParams) {
            queryParams.add("fontSize=$fontSize")
            queryParams.add("fontColor=$fontColor")
        }

        //randomises the url to stop it caching
        queryParams.add("_=${UUID.randomUUID()}")

        // Build the final URL with all query parameters
        return if (queryParams.isNotEmpty()) {
            val separator = if (url.contains("?")) "&" else "?"
            url + separator + queryParams.joinToString("&")
        } else {
            url
        }
    }
}