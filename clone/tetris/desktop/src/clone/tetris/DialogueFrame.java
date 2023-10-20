package clone.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogueFrame extends JFrame implements ActionListener {
    private final JButton processButton;
    private boolean isStringProcessed;
    private final JTextField field;
    public DialogueFrame() {
        setSize(400, 70);
        setTitle("Test");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        ImageIcon image = new ImageIcon("images/logo.jpeg");
        setIconImage(image.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        field = new JTextField();
        field.setPreferredSize(new Dimension(250, 50));

        processButton = new JButton("Remove numbers");
        processButton.setPreferredSize(new Dimension(150, 50));
        processButton.addActionListener(this);

        isStringProcessed = false;

        panel.add(processButton, BorderLayout.EAST);
        panel.add(field, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == processButton) {
            if (isStringProcessed) {
                DesktopLauncher.launchTetris();
            } else {
                field.setText(removeNumbers(field.getText()));
                processButton.setText("Launch Tetris");
                isStringProcessed = true;
            }
        }
    }

    private static String removeNumbers(String text) {
        StringBuilder output = new StringBuilder();
        for (char sym: text.toCharArray()) {
            if (!Character.isDigit(sym)) {
                output.append(sym);
            }
        }
        return output.toString();
    }
}
