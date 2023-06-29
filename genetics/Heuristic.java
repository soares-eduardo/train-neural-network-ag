package genetics;

public class Heuristic {

    public Heuristic() {
    }

    public int evaluateMove(int[][] board, int player, boolean occupiedPosition) {
        int score = 0;

        if (hasWon(board, player)) {
            score += +100; // High positive score for immediate win
        } else if (hasWon(board, getOpponent(player))) {
            score += -60; // High negative score for immediate loss
        } else if (isTie(board)) {
            score += 0; // Neutral score for tie
        }

        // Evaluate blocking moves
        score += evaluateBlockingMoves(board, player);

        // Evaluate creating opportunities
        score += evaluateOpportunityMoves(board, player);

        // Evaluate positional advantage
        score += evaluatePositionalAdvantage(board, player);

        // Evaluate if the neural network chose an occupied position
        score += occupiedPosition ? -30 : 0;

        // Evaluate how many rounds the neural network played
        score += evaluateRounds(countRounds(board));

        return score;
    }

    private int countRounds(int[][] board) {
        int count = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    private int evaluateRounds(int count) {
        switch (count) {
            case 1:
                return 10;
            case 2:
                return 20;
            case 3:
                return 30;
            default:
                return 40;
        }
    }

    private int evaluateBlockingMoves(int[][] board, int player) {
        int score = 0;
        int opponent = getOpponent(player);

        // Check rows for potential winning moves for the opponent
        for (int i = 0; i < 3; i++) {
            if (hasPotentialWin(board[i], opponent)) {
                score += 10;
            }
        }

        // Check columns for potential winning moves for the opponent
        for (int j = 0; j < 3; j++) {
            int[] column = { board[0][j], board[1][j], board[2][j] };
            if (hasPotentialWin(column, opponent)) {
                score += 10;
            }
        }

        // Check diagonals for potential winning moves for the opponent
        int[] diagonal1 = { board[0][0], board[1][1], board[2][2] };
        int[] diagonal2 = { board[0][2], board[1][1], board[2][0] };
        if (hasPotentialWin(diagonal1, opponent)) {
            score += 10;
        }
        if (hasPotentialWin(diagonal2, opponent)) {
            score += 10;
        }

        return score;
    }

    private int evaluateOpportunityMoves(int[][] board, int player) {
        int score = 0;

        // Evaluate potential winning moves for the player
        score += countPotentialWins(board, player) * 5;

        return score;
    }

    private int evaluatePositionalAdvantage(int[][] board, int player) {
        int score = 0;

        // Assign higher scores to strategic positions
        if (board[1][1] == player) {
            score += 3; // Center position
        }
        if (board[0][0] == player || board[0][2] == player ||
                board[2][0] == player || board[2][2] == player) {
            score += 2; // Corner positions
        }

        return score;
    }

    private boolean hasPotentialWin(int[] line, int player) {
        int playerCount = 0;
        int emptyCount = 0;

        for (int cell : line) {
            if (cell == player) {
                playerCount++;
            } else if (cell == -1) {
                emptyCount++;
            }
        }

        return playerCount == 2 && emptyCount == 1;
    }

    private int countPotentialWins(int[][] board, int player) {
        int count = 0;

        // Count potential winning moves in rows
        for (int i = 0; i < 3; i++) {
            if (hasPotentialWin(board[i], player)) {
                count++;
            }
        }

        // Count potential winning moves in columns
        for (int j = 0; j < 3; j++) {
            int[] column = { board[0][j], board[1][j], board[2][j] };
            if (hasPotentialWin(column, player)) {
                count++;
            }
        }

        // Count potential winning moves in diagonals
        int[] diagonal1 = { board[0][0], board[1][1], board[2][2] };
        int[] diagonal2 = { board[0][2], board[1][1], board[2][0] };
        if (hasPotentialWin(diagonal1, player)) {
            count++;
        }
        if (hasPotentialWin(diagonal2, player)) {
            count++;
        }

        return count;
    }

    private boolean hasWon(int[][] board, int player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    private boolean isTie(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == -1) {
                    return false; // There are still empty cells
                }
            }
        }
        return true; // All cells are filled (tie)
    }

    private int getOpponent(int player) {
        return (player == 1) ? 0 : 1;
    }
}
