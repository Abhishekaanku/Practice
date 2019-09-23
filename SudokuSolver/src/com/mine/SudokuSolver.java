package com.mine;

import java.util.Scanner;

public class SudokuSolver {
    private SudokuBoard sudokuBoard;

    public static void main(String[] args) {
	// write your code here
        System.out.println("Enter your sudoku cell one by one");

        SudokuSolver sudokuSolver=new SudokuSolver();

        sudokuSolver.getData();

        System.out.println("\tINPUT SUDOKU");
        sudokuSolver.displayBoard();
        long time1=System.currentTimeMillis();
        sudokuSolver.solve(0,0);
        long time2=System.currentTimeMillis();

        System.out.println("_____________________________\t");
        System.out.println("\n  AI SOLUTION TO SUDOKU");
        System.out.println("_____________________________\t\n");
        sudokuSolver.displayBoard();
        System.out.println("\nTime taken: "+(time2-time1)+" ms.");
    }

    private void getData() {
        int[][] data=new int[9][9];
        Scanner sc=new Scanner(System.in);
        for(int i=0;i<9;++i) {
            System.out.print("Data for row "+i+": ");
            for(int j=0;j<9;++j) {
                data[i][j]=sc.nextInt();
            }
        }
        sudokuBoard=new SudokuBoard(data);
    }


    private boolean solve(int row,int column) {
        if(row>8) {
            return true;
        }

        int[][] board=sudokuBoard.board;

        int nextRow=row,nextColumn=column;
        if(column>=8) {
            nextRow+=1;
            nextColumn=0;
        }
        else {
            nextColumn+=1;
        }

        if(board[row][column]==0) {
            int i;
            for(i=1;i<10;++i) {
                if(isFit(i,row,column)) {
                    board[row][column]=i;
                    boolean isSolved=solve(nextRow,nextColumn);
                    if(isSolved) {
                        return true;
                    }
                }
            }
            board[row][column]=0;
            return false;
        }
        else {
            return solve(nextRow,nextColumn);
        }
    }

    private boolean isFit(int testValue,int row,int column) {
        int [][]board=sudokuBoard.board;
        int i,j;
        for(i=0;i<9;++i) {
            if(board[row][i]==testValue) {
                return false;
            }
            if(board[i][column]==testValue) {
                return false;
            }
        }

        int cellStartColIndex=3*(column/3);
        int cellStartRowIndex=3*(row/3);
        i=0;
        while(i<3) {
            j=0;
            while(j<3) {
                if(board[cellStartRowIndex+i][cellStartColIndex+j]==testValue) {
                    return false;
                }
                j++;
            }
            i++;
        }
        return true;
    }

    private void displayBoard() {
        int[][] board=sudokuBoard.board;
        for(int i=0;i<9;++i) {
            if(i%3==0) {
//                System.out.println("_____________________________");
                System.out.println("-----------------------------");
            }
            for(int j=0;j<9;++j) {
                if(j%3==0) {
                    System.out.print(" | ");
                }
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }
}
