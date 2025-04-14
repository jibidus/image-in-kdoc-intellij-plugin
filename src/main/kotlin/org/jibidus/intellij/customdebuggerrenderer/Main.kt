package org.jibidus.intellij.customdebuggerrenderer

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel


fun main() {
    val f = JFrame()
    f.layout = BorderLayout()
    f.add(Canvas())
    f.pack()
    f.isVisible = true
}