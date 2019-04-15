package ru.cft.focusstart.ryazantsev;

import java.util.Scanner;

public class MultiplicationTable {
    final static private int MIN_SIZE = 1;
    final static private int MAX_SIZE = 32;

    private static int enterSize(){
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

    private static void printTable(int size){
        int digitsNumber = (int)Math.log10(size*size) + 1;
        String formatInteger = "%" + digitsNumber + "d";
        StringBuffer separator = new StringBuffer();
        for(int i=0; i<digitsNumber;++i){
            separator.append("-");
        }
        for (int i=1; i<=size; ++i){
            for (int j=1; j<=size-1; ++j){
                System.out.printf(formatInteger + "|", i*j);
            }
            System.out.printf(formatInteger + "\n", i*size);
            for (int j=0; j<size-1; ++j){
                System.out.print(separator + "+");
            }
            System.out.println(separator);
        }
    }

    public static void main(String[] args) {
        printTable(enterSize());
    }
}