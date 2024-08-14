
/**
* Created by Z.L. and L.F. on 5/23/2022.
* Remastered by Z.L. on 5/23/2024.
* Credits to Wordle by Josh Wardle.
*/

import java.util.*;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;

public class Wordle extends JPanel {

    private static int[] results = new int[5];
    private static int[][] resultsLog = new int[6][5];
    private static String[] guesses = new String[6];
    private static int guessA = -1;
    private static Scanner scan = new Scanner(System.in);
    private static Font font = new Font("Sans-Serif", Font.PLAIN, 75);
    private static Font font2 = new Font("Sans-Serif", Font.PLAIN, 25);
    private static int plays = 0;
    private static int wins = 0;
    private static double winPercent = 0.0;
    private static double winPercentRound = 0.0;
    private static int streak = 0;

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        createBoard(frame);
        String[] words = { "BONUS", "MAGMA", "SHARD", "DRAWN", "DEALT", "LEAVE", "RIGHT", "CHANT", "LIKED",
                "MIXER", "TROPE", "ACTOR", "THROW", "CATCH", "FLYER", "MAKER", "LAUGH", "WINDY",
                "WAIVE", "STUDY", "PALSY", "CRASS", "AFOOT", "JAZZY", "HOBBY", "PASTY", "SLOSH",
                "PASTA", "BARGE", "SNARL", "CUTIE", "CRATE", "PURSE", "SUPER", "ABUSE", "ADULT",
                "AGENT", "ANGER", "APPLE", "BASES", "BEACH", "BIRTH", "BLOCK", "BRAIN", "CAUSE",
                "CHEST", "CHILD", "CLASS", "CLOCK", "DANCE", "DRAFT", "DRAMA", "DREAM", "DRIVE",
                "EARTH", "ENEMY", "ENTRY", "ERROR", "EVENT", "FIELD", "FIGHT", "FINAL", "FAITH",
                "FLOOR", "GRASS", "GREEN", "GROUP", "GUIDE", "GRANT", "HEART", "HORSE", "HOTEL",
                "HOUSE", "IMAGE", "INDEX", "INPUT", "ISSUE", "JUDGE", "KNIFE", "KINGS", "LIGHT",
                "LIMIT", "LUNCH", "LEVEL", "LAYER", "MAJOR", "MARCH", "MOTOR", "MUSIC", "MONTH",
                "NIGHT", "NOISE", "NORTH", "NOVEL", "NURSE", "OFFER", "ORDER", "OTHER", "OWNER",
                "PANEL", "PEACE", "PHASE", "PILOT", "PLANT", "QUEEN", "RADIO", "RANGE", "RIVER",
                "ROUND", "RUGBY", "SCALE", "SCOPE", "SCENE", "SHAPE", "SCORE", "STEAL", "STATE",
                "STEAM", "SHOCK", "SHIFT", "TRUST", "TITLE", "THEME", "TOTAL", "TOWER", "UNCLE",
                "UNION", "UNITY", "VALUE", "VIDEO", "VISIT", "VOICE", "WAIST", "WATCH", "WATER",
                "WHILE", "WHOLE", "YOUTH" };
        String answer = words[(int) (Math.random() * words.length)];
        int holder = 0;

        for (holder = 0; holder < 6; holder++) {

            play(frame, answer);

            if (win()) {

                plays++;
                System.out.println("Congratulations on your victory!");
                wins++;
                streak++;
                winPercent = (double) wins / plays * 100;
                winPercentRound = Math.round(winPercent * 100.0) / 100.0;
                System.out.println("Type AGAIN to play again!");
                String word = scan.nextLine();

                if (word.equalsIgnoreCase("AGAIN")) {

                    holder = -1;
                    guessA = -1;
                    answer = words[(int) (Math.random() * words.length)];

                    for (int k = 0; k < 5; k++) {
                        results[k] = 0;
                    }

                    for (int r = 0; r < 6; r++) {
                        for (int c = 0; c < 5; c++) {
                            resultsLog[r][c] = 0;
                        }
                    }

                    createBoard(frame);

                } else {
                    holder = 10;
                }

            } else if (holder == 5) {

                System.out.println("The word was: " + answer);
                streak = 0;
                plays++;
                winPercent = (double) wins / plays * 100;
                winPercentRound = Math.round(winPercent * 100.0) / 100.0;
                System.out.println("Type AGAIN to play again!");
                String word = scan.nextLine();

                if (word.equalsIgnoreCase("AGAIN")) {

                    holder = -1;
                    guessA = -1;
                    answer = words[(int) (Math.random() * words.length)];

                    for (int k = 0; k < 5; k++) {
                        results[k] = 0;
                    }

                    for (int r = 0; r < 6; r++) {
                        for (int c = 0; c < 5; c++) {
                            resultsLog[r][c] = 0;

                        }
                    }

                    createBoard(frame);

                }
            }
        }
    }

    public static void createBoard(JFrame board) {

        board.getContentPane().add(new Wordle());
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setSize(770, 750);
        board.setVisible(true);

    }

    public void paint(Graphics g) {

        g.setFont(font);
        Color apparentlyTheBetterGreen = new Color(106, 170, 100);
        Color apparentlyTheBetterYellow = new Color(201, 180, 88);

        g.setColor(Color.black);
        g.drawString("WORDLE", 100, 100);

        g.setFont(font2);
        g.setColor(Color.white);
        g.fillRect(500, 270, 200, 98);
        g.setColor(Color.black);
        g.drawString("Played: " + plays, 505, 300);
        g.drawString("Win %: " + winPercentRound, 505, 325);
        g.drawString("Current Streak: " + streak, 505, 350);

        g.setFont(font);
        g.setColor(Color.white);

        for (int r = guessA + 1; r < 6; r++) {

            for (int c = 0; c < 5; c++) {

                g.fillRect(c * 85 + 60, r * 85 + 150, 75, 75);

            }

        }

        if (guessA != -1) {

            for (int r = 0; r <= guessA; r++) {

                for (int c = 0; c < 5; c++) {

                    if (resultsLog[r][c] == 0) {

                        Color lightGray = new Color(120, 124, 126);
                        g.setColor(lightGray);

                    } else if (resultsLog[r][c] == 1) {

                        g.setColor(apparentlyTheBetterYellow);

                    } else {

                        g.setColor(apparentlyTheBetterGreen);

                    }

                    g.fillRect(c * 85 + 60, r * 85 + 150, 75, 75);
                    g.setColor(Color.white);

                    if (!guesses[r].substring(c, c + 1).equalsIgnoreCase("w")
                            && !guesses[r].substring(c, c + 1).equalsIgnoreCase("i")
                            && !guesses[r].substring(c, c + 1).equalsIgnoreCase("t")
                            && !guesses[r].substring(c, c + 1).equalsIgnoreCase("o")) {
                        g.drawString(guesses[r].substring(c, c + 1), c * 85 + 72, r * 85 + 214);
                    } else if (guesses[r].substring(c, c + 1).equalsIgnoreCase("w")) {
                        g.drawString(guesses[r].substring(c, c + 1), c * 85 + 60, r * 85 + 214);
                    } else if (guesses[r].substring(c, c + 1).equalsIgnoreCase("i")) {
                        g.drawString(guesses[r].substring(c, c + 1), c * 85 + 86, r * 85 + 214);
                    } else if (guesses[r].substring(c, c + 1).equalsIgnoreCase("o")) {
                        g.drawString(guesses[r].substring(c, c + 1), c * 86 + 68, r * 85 + 214);
                    } else {
                        g.drawString(guesses[r].substring(c, c + 1), c * 88 + 72, r * 85 + 214);
                    }

                }

            }

        }

        g.setColor(Color.black);

        for (int r = guessA + 1; r < 6; r++) {

            for (int c = 0; c < 5; c++) {

                g.drawRect(c * 85 + 60, r * 85 + 150, 75, 75);

            }

        }

    }

    public static void play(JFrame frame, String answer) {

        String guess = scan.nextLine().toUpperCase();

        while (!(checkValidity(guess)) || !validWord(guess)) {

            System.out.println("You must enter a valid guess");
            guess = scan.nextLine().toUpperCase();

        }

        results = checkForLetters(guess, answer);
        guessA++;
        guesses[guessA] = guess;
        resultsLog[guessA] = results;
        createBoard(frame);

    }

    public static boolean checkValidity(String guess) {

        if (guess.length() == 5) {
            return true;
        }
        return false;

    }

    public static int[] checkForLetters(String guess, String answer) {

        int[] result = new int[5];
        String[] guessArr = new String[5];
        String[] answerArr = new String[5];

        for (int i = 0; i < 5; i++) {

            guessArr[i] = guess.substring(i, i + 1);
            answerArr[i] = answer.substring(i, i + 1);

        }

        for (int i = 0; i < 5; i++) {

            boolean green = false;

            for (int k = 0; k < 5; k++) {

                if (!green) {

                    if (guessArr[i].equalsIgnoreCase(answerArr[i])) {

                        green = true;
                        result[i] = 2;

                    } else if (guessArr[i].equalsIgnoreCase(answerArr[k])) {

                        result[i] = 1;

                    }
                }

            }

        }

        for (int i = 0; i < guess.length(); i++) {

            String letter = guessArr[i];
            int numInGuess = numTimesInGuess(letter, guess);
            int numInAnswer = numTimesInAnswer(letter, answer);

            if (numInGuess > numInAnswer) {

                for (int k = 0; k < guess.length(); k++) {

                    if (guessArr[k].equals(letter)) {

                        if (numInAnswer > 0) {

                            numInAnswer--;

                        } else {

                            if (result[k] != 2) {

                                result[k] = 0;

                            } else {

                                int limit = numTimesInAnswer(letter, answer) - 1;
                                int extra = 0;

                                for (int j = k - 1; j >= limit; j--) {

                                    if (guessArr[j].equals(letter)) {

                                        if (result[j] == 1) {

                                            result[j] = 0;

                                        } else {

                                            extra++;

                                        }

                                    }

                                }

                                if (numGreens(letter, result, guess) == limit + 1) {

                                    for (int j = 0; j < extra; j++) {

                                        if (guessArr[j].equals(letter)) {

                                            if (result[j] == 1) {

                                                result[j] = 0;

                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

        return result;

    }

    public static int numGreens(String letter, int[] arr, String guess) {

        int count = 0;

        for (int i = 0; i < guess.length(); i++) {

            if (guess.substring(i, i + 1).equals(letter)) {

                if (arr[i] == 2) {

                    count++;

                }

            }

        }

        return count;

    }

    public static int numTimesInAnswer(String guessLetter, String answer) {

        int count = 0;

        for (int i = 0; i < answer.length(); i++) {

            if (answer.substring(i, i + 1).equalsIgnoreCase(guessLetter)) {

                count++;

            }

        }

        return count;

    }

    public static int numTimesInGuess(String guessLetter, String guess) {

        int count = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (guess.substring(i, i + 1).equalsIgnoreCase(guessLetter)) {
                count++;
            }
        }

        return count;

    }

    public static boolean win() {

        for (int item : results) {
            if (item != 2) {
                return false;
            }
        }

        return true;
    }

    public static boolean validWord(String guess) {

        for (int i = 0; i < guess.length(); i++) {

            String letter = guess.substring(i, i + 1);

            if (!((letter.compareTo("d") >= -3 && letter.compareTo("d") <= 22)
                    || (letter.compareTo("A") >= 0 && letter.compareTo("A") <= 25))) {
                return false;
            }

        }

        return true;

    }
}