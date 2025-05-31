package org.jibidus.intellij.imageinkdoc

import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import org.junit.Test
import java.nio.file.Paths

class KDocImageDocumentationProviderTest {

    @Test
    fun `renderKdocImages() with empty kdoc`() {
        expect(renderKdocImages("", Paths.get(""))).toEqual("")
    }
}