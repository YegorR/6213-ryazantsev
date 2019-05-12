package ru.cft.focusstart.ryazantsev.view;


import ru.cft.focusstart.ryazantsev.util.IntCouple;
import ru.cft.focusstart.ryazantsev.util.ViewCellValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.BiConsumer;

public class Field {
    private JPanel gamePanel;
    private CellButton[][] buttons;

    public Field(IntCouple fieldSize, BiConsumer<IntCouple, Boolean> controllerMethod) {
        gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        gamePanel.setLayout(new GridLayout(fieldSize.getX(), fieldSize.getY()));
        createButtons(fieldSize, controllerMethod);
    }

    public JPanel getFieldPanel() {
        return gamePanel;
    }

    public void updateCell(IntCouple cell, ViewCellValue state) {
        int x = cell.getX();
        int y = cell.getY();
        buttons[x][y].setState(state);
        switch (state) {
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case ZERO:
                buttons[x][y].setEnabled(false);
                buttons[x][y].setEnabledRight(false);
                buttons[x][y].setEnabledSimpleClick(true);
                break;
            case UNTOUCHED:
                buttons[x][y].setEnabled(true);
                buttons[x][y].setEnabledRight(true);
                break;
            case FLAG:
                buttons[x][y].setEnabled(false);
                buttons[x][y].setEnabledRight(true);
        }
    }

    public void disableAll() {
        for (CellButton[] buttons : buttons) {
            for (CellButton button : buttons) {
                button.setEnabled(false);
                button.setEnabledRight(false);
                button.setEnabledSimpleClick(false);
            }
        }
    }

    private void createButtons(IntCouple fieldSize, BiConsumer<IntCouple, Boolean> controllerMethod) {
        int x = fieldSize.getX();
        int y = fieldSize.getY();
        buttons = new CellButton[x][y];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                buttons[i][j] = new CellButton(controllerMethod, new IntCouple(i, j));
                gamePanel.add(buttons[i][j]);
            }
        }
    }
}

class CellButton extends JButton {
    private static final int BUTTON_SIZE = 20;

    static private Icon mineIcon = IconManager.getIcon("mine.png", BUTTON_SIZE);
    static private Icon untouchedIcon = IconManager.getIcon("untouched.png", BUTTON_SIZE);
    static private Icon flagIcon = IconManager.getIcon("flag.png", BUTTON_SIZE);
    static private Icon touchedIcon = IconManager.getIcon("touched.png", BUTTON_SIZE);
    static private Icon num1Icon = IconManager.getIcon("num1.png", BUTTON_SIZE);
    static private Icon num2Icon = IconManager.getIcon("num2.png", BUTTON_SIZE);
    static private Icon num3Icon = IconManager.getIcon("num3.png", BUTTON_SIZE);
    static private Icon num4Icon = IconManager.getIcon("num4.png", BUTTON_SIZE);
    static private Icon num5Icon = IconManager.getIcon("num5.png", BUTTON_SIZE);
    static private Icon num6Icon = IconManager.getIcon("num6.png", BUTTON_SIZE);
    static private Icon num7Icon = IconManager.getIcon("num7.png", BUTTON_SIZE);
    static private Icon num8Icon = IconManager.getIcon("num8.png", BUTTON_SIZE);
    static private Icon noMineIcon = IconManager.getIcon("nomine.png", BUTTON_SIZE);

    static private Dimension sizeDimension = new Dimension(BUTTON_SIZE, BUTTON_SIZE);
    private ViewCellValue state = ViewCellValue.UNTOUCHED;
    private boolean enabledRight = true;
    private boolean enabledSimpleClick = true;

    CellButton(BiConsumer<IntCouple, Boolean> controllerMethod, IntCouple cell) {
        super();
        this.addActionListener(e -> controllerMethod.accept(cell, true));
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if ((e.getButton() == MouseEvent.BUTTON3) && enabledRight) {
                    controllerMethod.accept(cell, false);
                } else if ((e.getButton() == MouseEvent.BUTTON1) && (!isEnabled()) && (enabledSimpleClick)) {
                    controllerMethod.accept(cell, true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        setPreferredSize(sizeDimension);
        changeIcon();
    }

    void setState(ViewCellValue state) {
        this.state = state;
        changeIcon();
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        setDisabledIcon(icon);
    }

    void setEnabledRight(boolean b) {
        enabledRight = b;
    }

    void setEnabledSimpleClick(boolean b) {
        enabledSimpleClick = b;
    }

    private void changeIcon() {
        Icon icon;
        switch (state) {
            case ZERO:
                icon = touchedIcon;
                break;
            case UNTOUCHED:
                icon = untouchedIcon;
                break;
            case ONE:
                icon = num1Icon;
                break;
            case TWO:
                icon = num2Icon;
                break;
            case THREE:
                icon = num3Icon;
                break;
            case FOUR:
                icon = num4Icon;
                break;
            case FIVE:
                icon = num5Icon;
                break;
            case SIX:
                icon = num6Icon;
                break;
            case SEVEN:
                icon = num7Icon;
                break;
            case EIGHT:
                icon = num8Icon;
                break;
            case FLAG:
                icon = flagIcon;
                break;
            case MINE:
                icon = mineIcon;
                break;
            case NOMINE:
                icon = noMineIcon;
                break;
            default:
                return;
        }
        setIcon(icon);
    }
}