package ru.cft.focusstart.ryazantsev.view;

import javax.swing.*;
import java.awt.*;

class IconManager {
    private static final String ICONS_PATH = "task3/src/main/resources/icons/";

    static ImageIcon getIcon(String filename, int size) {
        return new ImageIcon(new ImageIcon(ICONS_PATH + filename).getImage().getScaledInstance(size,
                size, Image.SCALE_DEFAULT));
    }

    static ImageIcon getIcon(String filename) {
        return new ImageIcon(ICONS_PATH + filename);
    }
}
