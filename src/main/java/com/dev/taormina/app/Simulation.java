package com.dev.taormina.app;

public class Simulation {
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
                if (this.board[x][y] == 0 ){
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
        this.board[x][y] = 1;
    }

    public void setDead(int x, int y) {
        this.board[x][y] = 0;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < this.height && y >= 0 && y < this.width;
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

               if (this.board[x][y] == 1) {
                   if (aliveNeighbors == 2 || aliveNeighbors == 3) {
                       tempBoard[x][y] = 1;
                   } else {
                       tempBoard[x][y] = 0;
                   }
               } else {
                   if (aliveNeighbors == 3) {
                       tempBoard[x][y] = 1;
                   }
               }
            }
        }
        this.board = tempBoard;
    }

    public static void main(String[] args) {
        Simulation sim = new Simulation(5, 5);
        sim.setAlive(1,1);
        sim.setAlive(1,2);
        sim.setAlive(1,3);
        sim.printBoard();
        sim.step();
        sim.printBoard();
        sim.step();
        sim.printBoard();
        sim.step();
        sim.printBoard();
        //System.out.println(sim.countAliveNeighbors(1,1));
    }
}
