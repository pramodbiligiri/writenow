package com.pramodb.writenow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class EditorApp extends JPanel {

    private static final Logger LOG = LoggerFactory.getLogger(EditorApp.class);

    protected JTextArea textArea;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(executorService);
            }
        });
    }

    private static void createAndShowGUI(ExecutorService executorService) {
        JFrame frame = new JFrame("Write Now");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new EditorApp(new TextTransmitter()));
        frame.pack();
        frame.setVisible(true);

        addTextConsumer("1", executorService);
        addTextConsumer("2", executorService);
    }

    private static void addTextConsumer(String consumerId, ExecutorService executorService) {
        JFrame frame2 = new JFrame("Write Now " + consumerId);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ReceiverEditor receiver1 = new ReceiverEditor();
        new TextReceiver(executorService, consumerId).addReceiver(receiver1);
        frame2.add(receiver1);
        frame2.pack();
        frame2.setVisible(true);
    }

    public EditorApp(DocumentListener documentListener) {
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

        Document document = textArea.getDocument();
        document.addDocumentListener(documentListener);
    }
}
