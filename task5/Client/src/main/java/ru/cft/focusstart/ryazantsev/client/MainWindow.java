package ru.cft.focusstart.ryazantsev.client;

import ru.cft.focusstart.ryazantsev.client.net.NetManager;
import ru.cft.focusstart.ryazantsev.client.widget.*;
import ru.cft.focusstart.ryazantsev.common.Message;

import static ru.cft.focusstart.ryazantsev.common.Message.MessageType.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

class MainWindow {
    private JFrame frame;
    private OutputWidget outputWidget;
    private InputWidget inputWidget;
    private MembersListWidget membersListWidget;
    private JButton button;
    private ChatMenuBar menu;
    private NetManager netManager;

    private LoginDialog loginDialog;
    private LoginDialog.LoginData loginData;

    private State state;

    private void changeState(State state) {
        this.state = state;
        state.run();
    }

    MainWindow() {
        createGUI();
        createNetManager();
        changeState(new LoginDialogState());
    }

    private void createGUI() {
        frame = new JFrame();
        frame.setTitle("Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
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
            netManager.sendMessage(message);
        });
        c.gridx = 1;
        c.gridy = 1;
        frame.add(button, c);

        frame.pack();
        frame.setVisible(true);
    }

    private void createNetManager() {
        netManager = new NetManager(this::receiveConnection, this::receiveMessageStatus);
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
    }

    private void createMenu() {
        menu = new ChatMenuBar();
        frame.setJMenuBar(menu.getMenuBar());
        menu.addExitListener(e -> System.exit(0));
        menu.addInChatListener(e -> clickInChatMenu());
        menu.addOutChatListener(e -> clickOutChatMenu());
    }

    private void clickInChatMenu() {
        changeState(new LoginDialogState());
    }

    private void clickOutChatMenu() {
        changeState(new DoingNothingState());
    }

    private void receiveConnection(boolean isConnected) {
        state.receiveConnection(isConnected);
    }

    private void receiveMessage(Message message) {
        state.receiveMessage(message);
    }

    private void receiveMessageStatus(boolean successful) {
        if (!successful) {
            closeConnectionWithError();
        }
    }

    private void closeConnectionWithError() {
        state.handleConnectionError();
    }


    private interface State {
        void run();

        void handleConnectionError();

        void receiveMessage(Message message);

        void receiveConnection(boolean isConnected);
    }


    private class LoginDialogState implements State {
        private boolean closedByUser = false;

        @Override
        public void run() {
            createLoginDialog();
            button.setEnabled(false);
            menu.switchMode(true);
        }

        @Override
        public void handleConnectionError() {
            netManager.close();
            loginDialog.writeErrorMessage("Произошла ошибка соединения");
        }

        @Override
        public void receiveMessage(Message message) {
            Message.MessageType messageType = message.getMessageType();
            switch (messageType) {
                case SUCCESSFUL_CONNECT:
                    closedByUser = false;
                    loginDialog.destroy();
                    changeState(new OnlineState());
                    break;
                case BAD_NAME:
                    loginDialog.writeErrorMessage("Выберите другое имя");
                    loginDialog.setEnabledButton(true);
                    break;
            }
        }

        @Override
        public void receiveConnection(boolean isConnected) {
            if (!isConnected) {
                loginDialog.writeErrorMessage("Не удалось подключиться к серверу");
                loginDialog.setEnabledButton(true);
            } else {
                Message message = new Message(Message.MessageType.NEW_MEMBER);
                message.setName(loginData.getName());
                netManager.sendMessage(message);
            }
        }

        private boolean isClosedByUser() {
            return closedByUser;
        }

        private void createLoginDialog() {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    if (loginData == null) {
                        loginDialog = new LoginDialog(frame, LoginDialogState.this::login);
                    } else {
                        loginDialog = new LoginDialog(frame, LoginDialogState.this::login, loginData);
                    }
                    loginDialog.addCloseListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            if (isClosedByUser()) {
                                changeState(new DoingNothingState());
                            }
                        }
                    });
                    loginDialog.setVisible();
                    return null;
                }
            }.execute();
        }

        private void login(LoginDialog.LoginData loginData) {
            MainWindow.this.loginData = loginData;
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
            netManager.connect(host, port);
        }
    }


    private class DoingNothingState implements State {
        @Override
        public void run() {
            netManager.close();
            menu.switchMode(true);
            button.setEnabled(false);
        }

        @Override
        public void handleConnectionError() {
            JOptionPane.showMessageDialog(frame, "Произошла ошибка соединения", "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
        }

        @Override
        public void receiveConnection(boolean isConnected) {
        }

        @Override
        public void receiveMessage(Message message) {
        }
    }


    private class TryingConnectState implements State {
        private String host = loginData.getHost();
        private int port = loginData.getPort();

        @Override
        public void run() {
            button.setEnabled(false);
            menu.switchMode(true);
            netManager.close();
            tryConnect();
        }

        @Override
        public void handleConnectionError() {
        }

        @Override
        public void receiveConnection(boolean isConnected) {
            if (isConnected) {
                Message message = new Message(Message.MessageType.NEW_MEMBER);
                message.setName(loginData.getName());
                netManager.sendMessage(message);
            } else {
                tryConnect();
            }
        }

        @Override
        public void receiveMessage(Message message) {
            Message.MessageType messageType = message.getMessageType();
            switch (messageType) {
                case SUCCESSFUL_CONNECT:
                    changeState(new OnlineState());
                    break;
                case BAD_NAME:
                    changeState(new LoginDialogState());
                    state.receiveMessage(message);
                    break;
            }
        }

        private void tryConnect() {
            netManager.connect(host, port);
        }
    }


    private class OnlineState implements State {
        @Override
        public void run() {
            menu.switchMode(false);
            button.setEnabled(true);
            membersListWidget.clear();
            outputWidget.clear();
        }

        @Override
        public void handleConnectionError() {
            JOptionPane.showMessageDialog(frame, "Произошла ошибка соединения", "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
            changeState(new DoingNothingState());
        }

        @Override
        public void receiveConnection(boolean isConnected) {
        }

        @Override
        public void receiveMessage(Message message) {
            Message.MessageType messageType = message.getMessageType();
            switch (messageType) {
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
                case OLD_MEMBER:
                    membersListWidget.addMember(message.getName());
                    break;
                case SERVER_OUT:
                    outputWidget.printMessage(message);
                    changeState(new TryingConnectState());
            }
        }
    }
}
