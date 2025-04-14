package org.jibidus.intellij.customdebuggerrenderer

import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JPanel

class Canvas:JPanel() {
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        g2.fillRect(0, 0, 100, 100)
    }
}