package com.pramodb.writenow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class ReceiverEditor extends JPanel {

    private final JTextArea textArea;

    private static final Logger LOG = LoggerFactory.getLogger(ReceiverEditor.class);

    public ReceiverEditor() {
        super(new GridBagLayout());

        textArea = new JTextArea(20, 20);
        textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;

        add(scrollPane, c);
    }

    public void addContent(int offset, String text) {
        try {
            LOG.info("Got content: {}, {}", offset, text);
            textArea.getDocument().insertString(offset, text, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}
