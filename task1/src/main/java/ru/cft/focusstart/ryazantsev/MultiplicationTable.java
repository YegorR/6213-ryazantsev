package ru.cft.focusstart.ryazantsev;

import java.util.Scanner;

public class MultiplicationTable {
    final static private int MIN_SIZE = 1;
    final static private int MAX_SIZE = 32;

    private static int enterSize() {
        Scanner scanner = new Scanner(System.in);
        int size;
        while (true) {
            System.out.println("Enter a table size:");
            try {
                String line = scanner.next();
                size = Integer.parseUnsignedInt(line);
                if ((size < MIN_SIZE) || (size > MAX_SIZE)) {
                    System.out.println("This size is not supported!");
                    continue;
                }
                return size;
            } catch (NumberFormatException ex) {
                System.out.println("You entered not a valid number!");
            }
        }
    }

    private static String getRowSeparator(int digitsNumber, int size) {
        StringBuilder cellSeparator = new StringBuilder();
        for (int i = 0; i < digitsNumber; ++i) {
            cellSeparator.append("-");
        }
        StringBuilder rowSeparator = new StringBuilder();
        for (int i = 0; i < size - 1; ++i) {
            rowSeparator.append(cellSeparator);
            rowSeparator.append("+");
        }
        rowSeparator.append(cellSeparator);
        return rowSeparator.toString();
    }

    private static void printTable(int size) {
        int digitsNumber = (int) Math.log10(size * size) + 1;
        String formatInteger = "%" + digitsNumber + "d";
        String rowSeparator = getRowSeparator(digitsNumber, size);
        for (int i = 1; i <= size; ++i) {
            for (int j = 1; j <= size - 1; ++j) {
                System.out.printf(formatInteger + "|", i * j);
            }
            System.out.printf(formatInteger + "\n", i * size);
            System.out.println(rowSeparator);
        }
    }

    public static void main(String[] args) {
        printTable(enterSize());
    }
}