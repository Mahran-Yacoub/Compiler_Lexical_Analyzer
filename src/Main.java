import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        File file = new File("src/pascal/programs/pascal1.txt");
        ArrayList<Token> tokens = getTokens(file);

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
    public static ArrayList<Token> getTokens(File file) {

        ArrayList<Token> tokens = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                char[] line = scanner.nextLine().toCharArray();
                for (int i = 0; i < line.length; i++) {

                    char currentChar = line[i];
                    if (currentChar == CONSTANT.SPACE) {
                        continue;
                    }

                    if (Character.isLetter(currentChar)) {
                        i = getName(tokens, line, i);
                        continue;
                    }

                    if (Character.isDigit(currentChar)) {
                        i = getNumber(tokens, line, i);
                        continue;
                    }

                    ArrayList<Character> directSymbols = getDirectSymbols("src/pascal/programs/DirectSymbols.txt");

                    if (directSymbols.contains(currentChar)) {
                        tokens.add(new Token(currentChar + ""));
                        continue;
                    }


                    ArrayList<Character> inDirectSymbols = getInDirectSymbols("src/pascal/programs/InDirectSymbols.txt");

                    if (inDirectSymbols.contains(currentChar)) {

                        String token = "";

                        if (currentChar == CONSTANT.DOT) {
                            if ((i + 1) < line.length && line[i + 1] == CONSTANT.DOT) {
                                token = "..";
                                i = i + 1;

                            } else {
                                token = ".";
                            }

                            tokens.add(new Token(token));
                            continue;
                        }



                        if (currentChar == CONSTANT.COLON) {

                            if ((i + 1) < line.length && line[i + 1] == '=') {
                                token = ":=";
                                i = i + 1;
                            } else {
                                token = ":";
                            }

                            tokens.add(new Token(token));
                            continue;
                        }


                        if (currentChar == '>') {

                            if ((i + 1) < line.length && line[i + 1] == '=') {
                                token = ">=";
                                i = i + 1;
                            } else {
                                token = ">";
                            }

                            tokens.add(new Token(token));
                            continue;

                        }


                        if (currentChar == '<') {

                            if ((i + 1) < line.length && line[i + 1] == '=') {
                                token = "<=";
                                i = i + 1;
                            } else if ((i + 1) < line.length && line[i + 1] == '>') {
                                token = "<>";
                                i = i + 1;
                            } else {
                                token = "<";
                            }

                            tokens.add(new Token(token));
                            continue;
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return tokens;
    }
    private static ArrayList<Character> getInDirectSymbols(String path) {
        return getDirectSymbols(path);
    }
    private static ArrayList<Character> getDirectSymbols(String path) {

        ArrayList<Character> characters = new ArrayList<>();
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                characters.add(line.charAt(0));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return characters ;
    }
    private static int getNumber(ArrayList<Token> tokens, char[] line, int i) {

        int j = i + 1;
        String number = line[i] + "";
        while (j < line.length) {

            if (Character.isDigit(line[j])) {
                number += line[j];
                j++;
                continue;
            }

            if (line[j] == '.') {
                if ((j + 1) < line.length && Character.isDigit(line[(j + 1)])) {
                    number += line[j];
                    j++;
                    continue;
                }
            }

            break;
        }

        tokens.add(new Token(number));
        return j - 1;
    }
    private static int getName(ArrayList<Token> tokens, char[] line, int i) {

        int j = i + 1;
        String name = line[i] + "";
        while (j < line.length && Character.isLetterOrDigit(line[j])) {

            name += line[j];
            j++;
        }

        tokens.add(new Token(name));
        return j - 1;
    }
}
