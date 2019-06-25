package ru.cft.focusstart.ryazantsev.client;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class LoginDialog {
    private JDialog dialog;
    private JTextField hostField;
    private JTextField portField;
    private JTextField nameField;
    private JLabel errorLabel;
    private JButton connectButton;

    public LoginDialog(JFrame frame, Consumer<LoginData> connectButtonListener) {
        dialog = new JDialog(frame, true);
        dialog.setTitle("Подключение");
        //dialog.setResizable(false);   //Вызывает баг, при котором всё окно покрывается чёрным

        createGUI(connectButtonListener);
        dialog.pack();
    }

    public LoginDialog(JFrame frame, Consumer<LoginData> connectButtonListener, LoginData loginData) {
        this(frame, connectButtonListener);
        hostField.setText(loginData.host);
        portField.setText(String.valueOf(loginData.port));
        nameField.setText(loginData.name);
    }

    private void createGUI(Consumer<LoginData> connectButtonListener) {
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 8;
        c.ipady = 8;

        JLabel hostLabel = new JLabel("Хост");
        c.gridx = 0;
        c.gridy = 0;
        dialog.add(hostLabel, c);

        hostField = new JTextField("localhost", 20);
        c.gridx = 1;
        c.gridy = 0;
        dialog.add(hostField, c);

        JLabel portLabel = new JLabel("Порт");
        c.gridx = 2;
        c.gridy = 0;
        dialog.add(portLabel, c);

        portField = new JTextField("1234", 5);
        c.gridx = 3;
        c.gridy = 0;
        dialog.add(portField, c);

        JLabel nameLabel = new JLabel("Ник");
        c.gridx = 0;
        c.gridy = 1;
        dialog.add(nameLabel, c);

        nameField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        dialog.add(nameField, c);

        connectButton = new JButton("Подключиться");
        connectButton.addActionListener(e -> connectButtonListener.accept(new LoginData()));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;
        dialog.add(connectButton, c);

        errorLabel = new JLabel();
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        dialog.add(errorLabel, c);
    }

    public void destroy() {
        dialog.dispose();
    }

    public void writeErrorMessage(String message) {
        errorLabel.setText("<html><span style=\"" +
                "color: red;\">" + message + "</span>");
    }

    public void setVisible() {
        dialog.setVisible(true);
    }

    public boolean isVisible() {
        return dialog.isVisible();
    }

    public void setEnabledButton(boolean enable) {
        connectButton.setEnabled(enable);
    }

    public class LoginData {
        private String host;
        private int port;
        private String name;

        private LoginData() {
            this.host = hostField.getText().trim();
            this.name = nameField.getText().trim();
            try {
                this.port = Integer.parseUnsignedInt(portField.getText());
            } catch (NumberFormatException ex) {
                this.port = 0;
            }
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getName() {
            return name;
        }
    }
}
