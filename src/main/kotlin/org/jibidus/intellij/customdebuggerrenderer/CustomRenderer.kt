package org.jibidus.intellij.customdebuggerrenderer
import com.intellij.debugger.DebuggerContext
import com.intellij.debugger.engine.evaluation.EvaluationContext
import com.intellij.debugger.ui.tree.DebuggerTreeNode
import com.intellij.debugger.ui.tree.ValueDescriptor
import com.intellij.debugger.ui.tree.render.ChildrenBuilder
import com.intellij.debugger.ui.tree.render.CompoundReferenceRenderer;
import com.intellij.debugger.ui.tree.render.DescriptorLabelListener
import com.intellij.debugger.ui.tree.render.NodeRenderer
import com.intellij.debugger.ui.tree.render.Renderer
import com.intellij.psi.PsiElement
import com.sun.jdi.Value
import org.jdom.Element

// https://plugins.jetbrains.com/intellij-platform-explorer/extensions?extensions=com.intellij.debugger.nodeRenderer
class CustomRenderer: NodeRenderer {
    override fun clone(): Renderer {
        TODO("Not yet implemented")
    }

    override fun readExternal(p0: Element?) {
        TODO("Not yet implemented")
    }

    override fun writeExternal(p0: Element?) {
        TODO("Not yet implemented")
    }

    override fun getUniqueId(): String {
        TODO("Not yet implemented")
    }

    override fun buildChildren(p0: Value?, p1: ChildrenBuilder?, p2: EvaluationContext?) {
        TODO("Not yet implemented")
    }

    override fun getChildValueExpression(p0: DebuggerTreeNode?, p1: DebuggerContext?): PsiElement {
        TODO("Not yet implemented")
    }

    override fun calcLabel(p0: ValueDescriptor?, p1: EvaluationContext?, p2: DescriptorLabelListener?): String {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun setName(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setEnabled(p0: Boolean) {
        TODO("Not yet implemented")
    }
}