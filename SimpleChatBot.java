/**
 * Java. Chatter: simple chatbot
 *
 * @author Syzdykov Ruslan
 * @version 0.1.3
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*; // for StyledDocument
import bot.*;

class SimpleChatBot extends JFrame implements ActionListener {

    final String TITLE_OF_PROGRAM = "Chatter: simple chatbot";
    final int START_LOCATION = 200;
    final int WINDOW_WIDTH = 350;
    final int WINDOW_HEIGHT = 450;

    JTextPane dialogue; // area for dialog
    JCheckBox ai;       // enable/disable AI
    JTextField message; // field for entering messages
    SimpleBot sbot;     // chat-bot class (in bot package)
    SimpleAttributeSet botStyle; // style bot text

    public static void main(String[] args) {
        new SimpleChatBot();
    }

    /**
     * Constructor:
     * Creating a window and all the necessary elements on it
     */
    SimpleChatBot() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);
        // area for dialog
        dialogue = new JTextPane();
        dialogue.setEditable(false);
        dialogue.setContentType("text/html");
        JScrollPane scrollBar = new JScrollPane(dialogue);
        // style for bot messages
        botStyle = new SimpleAttributeSet();
        StyleConstants.setItalic(botStyle, true);
        StyleConstants.setForeground(botStyle, Color.blue);
        //StyleConstants.setAlignment(botStyle, StyleConstants.ALIGN_RIGHT);
        // panel for checkbox, message field and button
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
        ai = new JCheckBox("AI");
        ai.doClick();
        message = new JTextField();
        message.addActionListener(this);
        JButton enter = new JButton("Enter");
        enter.addActionListener(this);
        // adding all elements to the window
        bp.add(ai);
        bp.add(message);
        bp.add(enter);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);
        setVisible(true);
        sbot = new SimpleBot(); // creating bot object
    }

    /**
     * Listener of events from message field and enter button
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (message.getText().trim().length() > 0) {
            try {
                StyledDocument doc = dialogue.getStyledDocument();
                doc.insertString(doc.getLength(), message.getText() + "\n",
                    new SimpleAttributeSet());
                doc.insertString(doc.getLength(),
                    TITLE_OF_PROGRAM.substring(0, 9) +
                    sbot.sayInReturn(message.getText(), ai.isSelected()) + "\n",
                    botStyle);
            } catch(Exception e) { }
        }
        message.setText("");
        message.requestFocusInWindow();
    }
}
