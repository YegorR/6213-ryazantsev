package ru.cft.focusstart.ryazantsev.figure;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class FigureImpl implements Figure {


    private Map<String, Object> descriptionLines = new LinkedHashMap<>();

    @Override
    public final String getDescription() {
        StringBuilder description = new StringBuilder();
        for (Map.Entry<String, Object> entry : descriptionLines.entrySet()) {
            description.append(entry.getKey());
            description.append(": ");
            description.append(entry.getValue());
            description.append("\n");
        }
        return description.toString();
    }

    protected final void addToDescription(String valueDescription, Object value) {
        descriptionLines.put(valueDescription, value);
    }
}
