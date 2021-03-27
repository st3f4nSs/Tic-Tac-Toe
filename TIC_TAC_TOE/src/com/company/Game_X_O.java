package com.company;

import java.util.Arrays;
import java.util.Scanner;


class Game {

    static String[] board;
    static String turn;
    static String bot;
    static String player;

    static String checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> board[0] + board[1] + board[2];
                case 1 -> board[3] + board[4] + board[5];
                case 2 -> board[6] + board[7] + board[8];
                case 3 -> board[0] + board[3] + board[6];
                case 4 -> board[1] + board[4] + board[7];
                case 5 -> board[2] + board[5] + board[8];
                case 6 -> board[0] + board[4] + board[8];
                case 7 -> board[2] + board[4] + board[6];
                default -> null;
            };

            if (line.equals("XXX")) {
                return "X";
            } else if (line.equals("OOO")) {
                return "O";
            }
        }

        for (int a = 0; a < 9; a++) {
            if (Arrays.asList(board).contains(
                    String.valueOf(a + 1))) {
                break;
            } else if (a == 8) {
                return "draw";
            }
        }

        return null;
    }

    static void printBoard() {
        System.out.println("|---|---|---|");
        System.out.println("| " + board[0] + " | "
                + board[1] + " | " + board[2]
                + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[3] + " | "
                + board[4] + " | " + board[5]
                + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[6] + " | "
                + board[7] + " | " + board[8]
                + " |");
        System.out.println("|---|---|---|");
    }

    public static char[][] stringToMatrix(String[] s) {
        int stringCnt, i, j;
        char[][] result = new char[3][3];

        stringCnt = 0;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++) {
                if (s[stringCnt].equalsIgnoreCase("X"))
                    result[i][j] = 'x';
                else if (s[stringCnt].equalsIgnoreCase("O"))
                    result[i][j] = 'o';
                else
                    result[i][j] = '_';
                stringCnt++;
            }

        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        board = new String[9];
        turn = "X";
        String winner = null;

        System.out.println("\n");
        System.out.println("|----------------------Tic-Tac-Toe----------------------|");

        System.out.print("Select X or O: ");

        while (true) {
            String input = in.next();
            if (!(input.equalsIgnoreCase("X") || input.equalsIgnoreCase("O"))) {
                System.out.println("Invalid input. Re-enter X or O: ");
            } else {
                if (input.equalsIgnoreCase("X")) {
                    bot = "O";
                    player = "X";
                } else {
                    bot = "X";
                    player = "O";
                }
                break;
            }
        }

        System.out.println();
        for (int a = 0; a < 9; a++) {
            board[a] = String.valueOf(a + 1);
        }

        if (turn.equalsIgnoreCase(player))
            printBoard();


        while (winner == null) {
            int numInput;
            if (turn.equalsIgnoreCase(player)) {
                System.out.print(player + " turn: ");

                numInput = in.nextInt();
                if (!(numInput > 0 && numInput <= 9)) {
                    System.out.println("Invalid input. Enter slot number:");
                    continue;
                }
                System.out.println();
            } else {
                char[][] minimaxBoard = stringToMatrix(board);
                if(bot.equalsIgnoreCase("X"))
                    numInput = Minimax.getInput(minimaxBoard, Minimax.findBestMove(minimaxBoard, 'x', 'o'));
                else
                    numInput = Minimax.getInput(minimaxBoard, Minimax.findBestMove(minimaxBoard, 'o', 'x'));
            }

            if (board[numInput - 1].equals(String.valueOf(numInput))) {
                board[numInput - 1] = turn;

                if (turn.equals("X")) {
                    turn = "O";
                } else {
                    turn = "X";
                }

                if(turn.equalsIgnoreCase(player))
                    printBoard();
                winner = checkWinner();
            } else {
                System.out.println("Slot already taken. Enter slot number:");
            }
        }

        if (winner.equalsIgnoreCase("draw")) {
            System.out.println("It's a draw.");
        } else {
            System.out.println(winner + "'s have won.");
        }
        in.close();
    }
}
