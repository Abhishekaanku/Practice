package com.mine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TreeOp {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        AVLTree<Integer> avlTree=new AVLTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        System.out.println("1. Add an element");
        System.out.println("2. Delete an element");
        System.out.println("3. InOrder");
        System.out.println("4. Breadth First");
        System.out.println("5. Balance");
loop1:  while(true) {
            System.out.print("Enter your choice: ");
            int choice=sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter the element: ");
                    int newElement=sc.nextInt();
                    avlTree.insert(newElement);
                    break;
                case 2:
                    System.out.print("Enter the elemnt: ");
                    int element=sc.nextInt();
                    avlTree.delete(element);
                    break;
                case 3:
                    List<Integer> inOrder=new ArrayList<>();
                    avlTree.inOrder(avlTree.root,inOrder);
                    displayList(inOrder);
                    break;
                case 4:
                    displayList(avlTree.breadthFirst(avlTree.root));
                    break;
                case 5:
                    avlTree.balance();
                    break ;
                default:
                    break loop1;
            }
        }
    }

    private static void displayList(List<Integer> integerList) {
        for(Integer element:integerList) {
            System.out.print(element+" ");
        }
        System.out.println();
    }
}
