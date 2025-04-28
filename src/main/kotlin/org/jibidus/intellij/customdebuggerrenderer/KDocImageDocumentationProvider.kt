package org.jibidus.intellij.customdebuggerrenderer

import com.intellij.idea.LoggerFactory
import com.intellij.model.Pointer
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.io.toNioPath
import com.intellij.platform.backend.documentation.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.createSmartPointer
import okio.Path.Companion.toPath
import org.jetbrains.kotlin.KtPsiSourceElement
import org.jetbrains.kotlin.idea.k2.codeinsight.quickDoc.KotlinDocumentationTargetProvider
import org.jetbrains.kotlin.idea.k2.codeinsight.quickDoc.KotlinInlineDocumentationProvider
import org.jetbrains.kotlin.idea.k2.codeinsight.quickDoc.KotlinPsiDocumentationTargetProvider
import org.jetbrains.kotlin.psi.KtFile
import java.nio.file.Path
import java.nio.file.Paths


private val logger = Logger.getInstance(KotlinKDocImagePsiDocumentationTargetProvider::class.java)

/**
 * Ca fonctionne uniquement sur du code non compilé.
 * TODO :
 * * Extension KotlinKDocImageInlineDocumentationProvider enregistrée 2 fois !!?? -> last semble nécessaire pour les sources de libs (à vérifier… supprimer "first" ?)
 * * Essayer de faire planter le plugin
 *    - Que se passe-t-il lorsque la dépendance est issue d'un autre module ?
 *    - Que se passe-t-il lorsque la dépendance est externe ?
 *    - Que se passe-t-il avec du code java ?
 * * Référencer une image dans resources ?
 */

private val kdocImageRegex =
    "!<a href=['\"](?<url>.*?)['\"]>((?<width>\\d+)?x(?<height>\\d+)?)?(?<alt>.*?)</a>".toRegex()

/**
 * Fixes [this issue](https://youtrack.jetbrains.com/issue/KTIJ-13687/KDoc-support-inline-images).
 *
 * @see README.md
 */
private fun renderKdocImages(kotlinDoc: String, currentPath: Path): String {
//    logger.warn(kotlinDoc)
    val replace = kotlinDoc.replace(kdocImageRegex) {
        var url = it.groups["url"]?.value ?: return@replace it.value
        val width = it.groups["width"]?.value?.toIntOrNull()
        val height = it.groups["height"]?.value?.toIntOrNull()
        val alt = it.groups["alt"]?.value

        if (currentPath != null && !(url.startsWith("http"))) {
            url = "file://" + currentPath.parent.resolve(url).toString()
//            logger.warn("New url!! $url")
        }

        val buildString = buildString {
            append("""<img src="$url" """)
            if (alt != null) append("alt=\"$alt\" ")
            if (width != null) append("width=\"$width\" ")
            if (height != null) append("height=\"$height\" ")
            append(">")
        }
        buildString
    }
    return replace
}

internal class KotlinKDocImagePsiDocumentationTargetProvider : PsiDocumentationTargetProvider {
    private val delegate = KotlinPsiDocumentationTargetProvider()

    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocumentationTarget? =
        delegate.documentationTarget(element, originalElement)?.let {
            KotlinKDocImageDocumentationTarget(element, originalElement, it)
        }
}

// Required for "Toggle renderer view" (avec KDocImageInlineDocumentation)
@Suppress("UnstableApiUsage")
internal class KotlinKDocImageInlineDocumentationProvider : InlineDocumentationProvider {
    private val delegate = KotlinInlineDocumentationProvider()
    override fun inlineDocumentationItems(file: PsiFile?): Collection<InlineDocumentation> =
        delegate.inlineDocumentationItems(file).map(::KDocImageInlineDocumentation)

    override fun findInlineDocumentation(file: PsiFile, textRange: TextRange): InlineDocumentation? =
        delegate.findInlineDocumentation(file, textRange)?.let(::KDocImageInlineDocumentation)
}

@Suppress("UnstableApiUsage")
private class KDocImageInlineDocumentation(val delegate: InlineDocumentation) : InlineDocumentation by delegate {

    // Toggle renderer view
    override fun renderText(): String? {
        val filePath =
            (delegate.ownerTarget!!.navigatable as PsiElement).containingFile.virtualFile.path.let(Paths::get)
        logger.warn("(KDocImageInlineDocumentation) path=" + filePath.toString())
        return delegate.renderText()?.let { renderKdocImages(it, filePath) }
    }
}

private class KotlinKDocImageDocumentationTarget(
    val element: PsiElement,
    val originalElement: PsiElement?,
    val delegate: DocumentationTarget
) : DocumentationTarget by delegate {

    // Javadoc au survol de la déclaration de la méthode + usage au survol d'un appel de méthode
    override fun computeDocumentation(): DocumentationResult? {
        val documentationResult = delegate.computeDocumentation()
        val filePath = element.containingFile.virtualFile.path.let(Paths::get)
        @Suppress("UnstableApiUsage")
        val html =
            (documentationResult as? DocumentationData)?.html?.let { renderKdocImages(it, filePath) } ?: return null
        return DocumentationResult.documentation(html)
    }

    // TODO: Does this can be replaced by delegate.createPointer()? Il semblerait que non (ajouté pour supporter une version d'IntelliJ), à vérifier.
    // Required
    override fun createPointer(): Pointer<out DocumentationTarget> {
        val elementPtr = element.createSmartPointer()
        val originalElementPtr = originalElement?.createSmartPointer()
        val delegatePtr = delegate.createPointer()
        return Pointer {
            val element = elementPtr.dereference() ?: return@Pointer null
            val delegate = delegatePtr.dereference() ?: return@Pointer null
            KotlinKDocImageDocumentationTarget(element, originalElementPtr?.dereference(), delegate)
        }
    }
}
