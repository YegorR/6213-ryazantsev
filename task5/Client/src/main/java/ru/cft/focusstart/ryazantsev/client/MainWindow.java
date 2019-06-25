package ru.cft.focusstart.ryazantsev.client;

import ru.cft.focusstart.ryazantsev.client.widget.*;
import ru.cft.focusstart.ryazantsev.common.Message;

import static ru.cft.focusstart.ryazantsev.common.Message.MessageType.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow {
    private JFrame frame;
    private OutputWidget outputWidget;
    private InputWidget inputWidget;
    private MembersListWidget membersListWidget;
    private JButton button;
    private ChatMenuBar menu;
    private NetManager netManager;

    private LoginDialog loginDialog;
    private LoginDialog.LoginData loginData;

    public MainWindow() {
        frame = new JFrame();
        frame.setTitle("Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        createGUI();
        netManager = new NetManager();
        frame.pack();
        frame.setVisible(true);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                boolean isInterrupted = false;
                while (!isInterrupted) {
                    try {
                        if (netManager.isReady()) {
                            receiveMessage(netManager.getMessage());
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        isInterrupted = true;
                    } catch (IOException ex) {
                        closeConnectionWithError();
                    }
                }
                return null;
            }
        }.execute();

        createLoginDialog();
    }

    private void createLoginDialog() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                if (loginData == null) {
                    loginDialog = new LoginDialog(frame, MainWindow.this::login);
                } else {
                    loginDialog = new LoginDialog(frame, MainWindow.this::login, loginData);
                }
                loginDialog.setVisible();
                return null;
            }
        }.execute();
    }

    private void createGUI() {
        createMenu();
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        outputWidget = new OutputWidget();
        c.gridx = 0;
        c.gridy = 0;
        frame.add(outputWidget.getWidget(), c);

        membersListWidget = new MembersListWidget();
        c.gridx = 1;
        c.gridy = 0;
        frame.add(membersListWidget.getWidget(), c);

        inputWidget = new InputWidget();
        c.gridx = 0;
        c.gridy = 1;
        frame.add(inputWidget.getWidget(), c);

        button = new JButton("Отправить");
        button.addActionListener(e -> {
            Message message = new Message(MESSAGE);
            message.setText(inputWidget.readText());
            message.setName(loginData.getName());
            netManager.sendMessage(message, this::receiveMessageStatus);
        });
        button.setEnabled(false);
        c.gridx = 1;
        c.gridy = 1;
        frame.add(button, c);
    }

    private void createMenu() {
        menu = new ChatMenuBar();
        frame.setJMenuBar(menu.getMenuBar());
        menu.addExitListener(e -> System.exit(0));
        menu.addInChatListener(e -> createLoginDialog());
        menu.addOutChatListener(e -> {
            netManager.close();
            button.setEnabled(false);
            menu.enableOutChat(false);
            menu.enableInChat(true);
        });
        menu.enableOutChat(false);
    }

    private void login(LoginDialog.LoginData loginData) {
        this.loginData = loginData;
        String host = loginData.getHost();
        int port = loginData.getPort();
        String name = loginData.getName();

        if (host.isEmpty()) {
            loginDialog.writeErrorMessage("Введите адрес хоста");
            return;
        }
        if (name.isEmpty()) {
            loginDialog.writeErrorMessage("Введите ник");
            return;
        }
        if (port == 0) {
            loginDialog.writeErrorMessage("Введите корректный порт");
            return;
        }
        loginDialog.setEnabledButton(false);
        netManager.connect(host, port, this::receiveConnection);
    }

    private void receiveConnection(boolean isConnected) {
        if (!isConnected) {
            loginDialog.writeErrorMessage("Не удалось подключиться к серверу");
            loginDialog.setEnabledButton(true);
        } else {
            Message message = new Message(Message.MessageType.NEW_MEMBER);
            message.setName(loginData.getName());
            netManager.sendMessage(message, this::receiveMessageStatus);
        }
    }

    private void receiveMessage(Message message) {
        Message.MessageType messageType = message.getMessageType();
        switch (messageType) {
            case SUCCESSFUL_CONNECT:
                if (loginDialog.isVisible()) {
                    loginDialog.destroy();
                    menu.enableInChat(false);
                    menu.enableOutChat(true);
                    button.setEnabled(true);
                    membersListWidget.clear();
                    outputWidget.clear();
                } else {
                    netManager.sendMessage(new Message(ERROR), this::receiveMessageStatus);
                }
                break;
            case MESSAGE:
                outputWidget.printMessage(message);
                break;
            case NEW_MEMBER:
                outputWidget.printMessage(message);
                membersListWidget.addMember(message.getName());
                break;
            case GONE_MEMBER:
                outputWidget.printMessage(message);
                membersListWidget.removeMember(message.getName());
                break;
            case BAD_NAME:
                if (!loginDialog.isVisible()) {
                    netManager.sendMessage(new Message(ERROR), this::receiveMessageStatus);
                } else {
                    loginDialog.writeErrorMessage("Выберите другое имя");
                    loginDialog.setEnabledButton(true);
                }
                break;
            case OLD_MEMBER:
                membersListWidget.addMember(message.getName());
                break;
            case SERVER_OUT:
                menu.enableInChat(true);
                menu.enableOutChat(false);
                outputWidget.printMessage(message);
                button.setEnabled(false);
                netManager.close();
            case ERROR:
                break;
        }
    }

    private void receiveMessageStatus(boolean successful) {
        if (!successful) {
            closeConnectionWithError();
        }
    }

    private void closeConnectionWithError() {
        netManager.close();
        if (loginDialog.isVisible()) {
            loginDialog.writeErrorMessage("Произошла ошибка соединения");
        } else {
            JOptionPane.showMessageDialog(frame, "Произошла ошибка соединения", "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
        }

    }
}
