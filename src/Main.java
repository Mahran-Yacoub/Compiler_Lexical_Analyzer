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

                        if(currentChar == '('){

                            if ((i + 1) < line.length && line[i + 1] == '*') {
                                CommentType commentEnd = getCommentEnd(i+2 , "(*" , line , scanner);
                                if(commentEnd == null){
                                    break;
                                }
                                i = commentEnd.getIndex();
                                line = commentEnd.getLine();
                            } else {
                                token = "(";
                                tokens.add(new Token(token));
                            }
                            continue;
                        }


                        if(currentChar == '{'){
                            CommentType commentEnd = getCommentEnd(i+1,"{", line, scanner);
                            if(commentEnd == null){
                                break;
                            }
                            i = commentEnd.getIndex();
                            line = commentEnd.getLine();
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
    private static CommentType getCommentEnd(int j, String typeOfComment, char[] line, Scanner scanner) {

        if(typeOfComment.equals("{")){

            for(int z = j ; z<line.length ; z++){

                if(line[z] == '}'){
                    return new CommentType(line,z) ;
                }
            }

            while (scanner.hasNextLine()){
                j = 0 ;
                char [] newLine = scanner.nextLine().toCharArray();
                for(int z = j ; z<newLine.length ; z++){

                    if(newLine[z] == '}'){
                        return new CommentType(newLine,z) ;
                    }
                }
            }


        }else if(typeOfComment.equals("(*")){

            for(int z = j ; z<line.length-1 ; z++){

                if(line[z] == '*' && line[z+1] == ')'){
                    return new CommentType(line,z+1) ;
                }
            }

            while (scanner.hasNextLine()){

                j = 0 ;
                char [] newLine = scanner.nextLine().toCharArray();
                for(int z = j ; z<newLine.length-1 ; z++){

                    if(newLine[z] == '*' && newLine[z+1] == ')'){
                        return new CommentType(newLine,z+1) ;
                    }
                }
            }
        }

        return null ;
    }
    private static ArrayList<Character> getInDirectSymbols(String path) {
        return getDirectSymbols(path);
    }
    private static ArrayList<Character> getDirectSymbols(String filePath) {

        ArrayList<Character> characters = new ArrayList<>();
        File file = new File(filePath);
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
