package com.dev.taormina.app;

public class Simulation {
    public static int ALIVE = 1;
    public static int DEAD = 0;

    int width;
    int height;
    int[][] board;
    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[height][width];
    }

    public void printBoard() {
        System.out.println("---");
        for (int x = 0 ; x < this.height; x++) {
            StringBuilder line = new StringBuilder("|");
            for (int y = 0; y < this.width; y++) {
                if (this.board[x][y] == DEAD){
                    line.append(".");
                } else {
                    line.append("*");
                }
            }
            line.append("|");
            System.out.println(line);
        }
        System.out.println("---\n");
    }
    public void setAlive(int x, int y) {
        if (inBounds(x, y)) {
            this.board[x][y] = ALIVE;
        }
    }

    public void setDead(int x, int y) {
        if (inBounds(x, y)) {
            this.board[x][y] = DEAD;
        }
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < this.height && y >= 0 && y < this.width;
    }

    public boolean isAlive(int x, int y) {
        if (inBounds(x, y)) {
            return this.board[x][y] == ALIVE;
        } else {
            return false;
        }
    }

    public int countAliveNeighbors(int x, int y) {
       int count = -this.board[x][y];
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (inBounds(i, j)) {
                        count += this.board[i][j];
                }
            }
        }
        return count;
    }

    public void step() {
        int [][] tempBoard = new int[this.height][this.width];

        for (int x = 0; x < this.height; x++) {
            for (int y = 0; y < this.width; y++) {
               int aliveNeighbors = countAliveNeighbors(x, y);

               if (this.board[x][y] == ALIVE) {
                   if (aliveNeighbors == 2 || aliveNeighbors == 3) {
                       tempBoard[x][y] = ALIVE;
                   } else {
                       tempBoard[x][y] = DEAD;
                   }
               } else {
                   if (aliveNeighbors == 3) {
                       tempBoard[x][y] = ALIVE;
                   }
               }
            }
        }
        this.board = tempBoard;
    }
} // Simulation
