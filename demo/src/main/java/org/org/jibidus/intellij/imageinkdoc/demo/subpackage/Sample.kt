package org.org.jibidus.intellij.imageinkdoc.demo.subpackage

/**
 * Here is how to embed an image in kdoc:
 *
 * ![Image description](image.jpeg)
 */
data class Sample(val att: Int) {
    fun getData(): String = "xx xx xx"
}

fun main() {
    val v = Sample(1)
    println(v)
}