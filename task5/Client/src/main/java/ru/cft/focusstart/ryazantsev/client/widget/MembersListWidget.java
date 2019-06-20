package ru.cft.focusstart.ryazantsev.client.widget;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MembersListWidget {
    private JScrollPane scrollPane;
    private JPanel panel;
    private Map<String, JLabel> members = new HashMap<>();

    public MembersListWidget() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(150, 300));
        scrollPane.setVerticalScrollBar(new JScrollBar());
    }

    public JComponent getWidget() {
        return scrollPane;
    }

    public void addMember(String name) {
        if (members.containsKey(name)) {
            return;
        }
        JLabel memberLabel = new JLabel("<html><span style=\"" +
                "color: blue; font-weight: bold;font-family: sans-serif; \">" + HTMLPrinter.escapeText(name) +
                "</span>");
        memberLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        members.put(name, memberLabel);
        panel.add(memberLabel);
    }

    public void removeMember(String name) {
        if (!members.containsKey(name)) {
            return;
        }
        panel.remove(members.get(name));
        members.remove(name);
        panel.validate();
    }
}
