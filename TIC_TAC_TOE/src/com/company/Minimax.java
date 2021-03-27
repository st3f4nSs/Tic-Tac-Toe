package com.company;

class Minimax {
    public static class Move {
        int row, col;
    }

    public static Boolean isMovesLeft(char board[][]) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    public static int evaluate(char b[][], char player, char opponent) {
        // Rows
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2]) {
                if (b[row][0] == player)
                    return +10;
                else if (b[row][0] == opponent)
                    return -10;
            }
        }

        // Columns
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col]) {
                if (b[0][col] == player)
                    return +10;

                else if (b[0][col] == opponent)
                    return -10;
            }
        }

        // Diagonals
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player)
                return +10;
            else if (b[0][0] == opponent)
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player)
                return +10;
            else if (b[0][2] == opponent)
                return -10;
        }

        return 0;
    }


    public static int minimax(char board[][], int depth, Boolean isMax, char player, char opponent) {
        int score = evaluate(board, player, opponent);

        if (score == 10)
            return score - depth;

        if (score == -10)
            return score + depth;

        if (!isMovesLeft(board))
            return 0;

        if (isMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = player;
                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax, player, opponent));
                        board[i][j] = '_';
                    }
                }
            }
            return best;

        } else {
            int best = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = opponent;

                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax, player, opponent));

                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    public static Move findBestMove(char[][] board, char player, char opponent) {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = player;
                    int moveVal = minimax(board, 0, false, player, opponent);
                    board[i][j] = '_';
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    public static int getInput(char[][] board, Move move) {
        int cnt = 1, i, j;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++) {
                if (i == move.row && j == move.col)
                    return cnt;
                cnt++;
            }
        return -1;
    }
}
