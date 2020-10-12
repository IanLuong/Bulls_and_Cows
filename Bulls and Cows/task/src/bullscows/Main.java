package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length:");
        String length = scanner.nextLine();
        if(!isValidLength(length)) {
            System.out.println("Error");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        String range = scanner.nextLine();
        if(!isValidRange(range)) {
            System.out.println("Error");
            return;
        }

        if(Integer.parseInt(length) > Integer.parseInt(range)) {
            System.out.println("Error");
        } else {
            String code = getUniqueCode(Integer.parseInt(length), Integer.parseInt(range));
            System.out.println("Okay, let's start a game!");
            playGame(code, scanner);
        }
    }

    public static boolean isValidLength(String length) {
        try {
            int lengthVal = Integer.parseInt(length);
            return lengthVal <= 36 && lengthVal >= 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidRange(String range) {
        try {
            int rangeVal = Integer.parseInt(range);
            return rangeVal <= 36 && rangeVal >= 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static String getUniqueCode(int length, int range) {
        Random random = new Random();
        int letterRange = range - 11;
        System.out.println(letterRange);
        while (true) {
            boolean hasUniqueVals = true;
            StringBuilder code = new StringBuilder();

            while (code.length() != length) {
                if(random.nextBoolean()) {
                    code.append(random.nextInt(10));
                } else if(letterRange > 0 && random.nextBoolean()) {
                    code.append((char) (random.nextInt(letterRange) + 97));
                }
            }

            System.out.println(code.toString());

            for (int i = 0; i < length - 1; i++) {
                for (int j = i + 1; j < length; j++) {
                    if (code.charAt(i) == code.charAt(j)) {
                        hasUniqueVals = false;
                        break;
                    }
                }
                if (!hasUniqueVals) {
                    break;
                }
            }

            if(hasUniqueVals) {

                System.out.println("The secret is prepared: ");
                for(int i = 0; i < length; i++) {System.out.print("*");}
                System.out.printf(" (%d-%s).", 0, range > 10 ? String.valueOf((char) (97 + letterRange)) : range - 1);
                return code.toString();
            }
        }
    }


    public static void playGame(String answer, Scanner scanner) {
        boolean found = false;
        int counter = 1;
        while (!found) {
            int bulls = 0;
            int cows = 0;

            System.out.println("Turn " + counter + ":");
            String guess = scanner.next();

            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == guess.charAt(i)) {
                    bulls++;
                    continue;
                }
                for (int j = 0; j < answer.length(); j++) {
                    if (answer.charAt(i) == guess.charAt(j) && i != j) {
                        cows++;
                        break;
                    }
                }
            }
            if (bulls == answer.length()) {
                System.out.printf("Grade: %d bulls \n", answer.length());
                System.out.println("Congratulations! You guessed the secret code.");
                found = true;
            } else if (bulls == 0 && cows == 0) {
                System.out.println("Grade: None");
                counter++;
            } else {
                System.out.printf("Grade: %d bull(s) and %d cow(s)\n", bulls, cows);
                counter++;
            }

        }
    }
}
