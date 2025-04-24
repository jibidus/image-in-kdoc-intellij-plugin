package org.jibidus.intellij.customdebuggerrenderer

import com.intellij.debugger.engine.FullValueEvaluatorProvider
import com.intellij.debugger.engine.evaluation.EvaluationContextImpl
import com.intellij.debugger.impl.DebuggerUtilsImpl
import com.intellij.debugger.ui.impl.watch.ValueDescriptorImpl
import com.intellij.debugger.ui.tree.render.CompoundRendererProvider
import com.intellij.debugger.ui.tree.render.CustomPopupFullValueEvaluator
import com.intellij.diff.fragments.LineFragmentImpl
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.LoggerRt
import com.intellij.rt.debugger.ImageSerializer
import com.sun.jdi.ObjectReference
import com.sun.jdi.StringReference
import com.sun.jdi.Value
import org.intellij.images.editor.impl.ImageEditorManagerImpl.createImageEditorUI
import org.jetbrains.annotations.Nls
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment
import java.awt.Transparency.TRANSLUCENT
import java.nio.charset.StandardCharsets
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

// Cf ImageObjectRenderer
class CustomRenderer : CompoundRendererProvider() {

    override fun getName() = "Custom name"

    override fun getClassName() = "org.jibidus.Sample"

    override fun isEnabled() = true

//    override fun getIsApplicableChecker(): Function<Type, CompletableFuture<Boolean>> {
//        return Function { t ->
//            CompletableFuture.completedFuture(
//                DebuggerUtils.instanceOf(t, className)
//            )
//        }
//    }

    override fun getFullValueEvaluatorProvider(): FullValueEvaluatorProvider {
        return FullValueEvaluatorProvider { evaluationContext: EvaluationContextImpl, valueDescriptor: ValueDescriptorImpl ->
            object : CustomPopupFullValueEvaluator<String>(
                "\\u2026 Show Sample",
                evaluationContext
            ) {
                override fun getData(): String? {
                    val obj = valueDescriptor.value as? ObjectReference ?: return null
                    LOG.info("##### obj.javaClass=${obj.javaClass}")
                    LOG.info("##### obj.referenceType()=${obj.referenceType()}")
//                    for (method in obj.referenceType().methods()) {
//                        LOG.info("#### method name '${method.name()}' with signature '${method.signature()}'")
//                    }
                    val data = DebuggerUtilsImpl.invokeObjectMethod(
                        getEvaluationContext().createEvaluationContext(obj),
                        obj,
                        "getData",
                        "()Ljava/lang/String;",
                        emptyList()
                    ) as StringReference
                    LOG.info("##### data=${data}")
                    return data.value()
                }

                override fun createComponent(data: String): JComponent {
//                    val image = getLocalGraphicsEnvironment()
//                        .defaultScreenDevice.defaultConfiguration
//                        .createCompatibleImage(200, 200, TRANSLUCENT)
//                    val g = image.createGraphics()
//                    g.drawRect(30, 30, 100, 100)
//                    g.dispose()
//                    return createImageEditorUI(image)

                    return Canvas()
                }
            }


//            object : IconPopupEvaluator(
//                "\\u2026 Show image",
////                JavaDebuggerBundle.message("message.node.show.image"),
//                evaluationContext
//            ) {
//                override fun getData(): Icon? {
//                    return getIcon(
//                        getEvaluationContext(),
//                        valueDescriptor.value,
//                        "imageToBytes"
//                    )
//                }
//            }
        }
    }

    abstract
    class IconPopupEvaluator(linkText: String, evaluationContext: EvaluationContextImpl) :
        CustomPopupFullValueEvaluator<Icon>(linkText, evaluationContext) {
        override fun createComponent(data: Icon): JComponent {
            val w = data.iconWidth
            val h = data.iconHeight
            val image = getLocalGraphicsEnvironment()
                .defaultScreenDevice.defaultConfiguration
                .createCompatibleImage(w, h, TRANSLUCENT)
            val g = image.createGraphics()
            data.paintIcon(null, g, 0, 0)
            g.dispose()
            return createImageEditorUI(image)
        }
    }

    companion object {
        val LOG: Logger = Logger.getInstance(CustomRenderer::class.java)

        fun getIcon(evaluationContext: EvaluationContextImpl, obj: Value, methodName: String): ImageIcon? {
            try {
                val data = getImageBytes(evaluationContext, obj, methodName)
                return ImageIcon(data)
            } catch (e: Exception) {
                LOG.info("Exception while getting image data", e)
            }
            return null
        }

        private fun getImageBytes(
            evaluationContext: EvaluationContextImpl,
            obj: Value,
            methodName: String
        ): ByteArray {
            val copyContext = evaluationContext.createEvaluationContext(obj)
            val bytes =
                DebuggerUtilsImpl.invokeHelperMethod(
                    copyContext,
                    ImageSerializer::class.java,
                    methodName,
                    mutableListOf<Value>(obj)
                ) as StringReference
            return bytes.value().toByteArray(StandardCharsets.ISO_8859_1)
        }
    }
//    override fun getIconRenderer() = ValueIconRenderer { _, _, _ -> AlertIcon() }


}