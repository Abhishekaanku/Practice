package com.mine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static com.mine.Tree.Node;

public class PredictiveSearch {

    public static void main(String[] args) {
        // write your code here
        Tree wordTree=new Tree();

        wordTree.setRoot(new Node('0',false));

        PredictiveSearch predictiveSearch=new PredictiveSearch();

        Scanner sc=new Scanner(System.in);
        System.out.print("Enter FileName: ");
        try {
            String fileName=sc.nextLine();
            predictiveSearch.populateData(wordTree,fileName);

            String searchPattern;
            System.out.print("Enter your searchPattern: ");
            while(!(searchPattern=sc.nextLine()).equals("0")) {
                List<String> matchingPatterns=predictiveSearch.obtainMatchingWord(wordTree,searchPattern);
                predictiveSearch.displayList(matchingPatterns);
                System.out.print("Enter your searchPattern: ");
            }
        } catch (IOException e) {
            System.out.println("FIle not found");
        }
    }

    private void populateData(Tree tree,String fileName) throws IOException {
        FileReader fileReader=new FileReader(new File(fileName));
        BufferedReader buf=new BufferedReader(fileReader);
        String word;
        int count=0;
        while((word=buf.readLine())!=null) {
            addToTree(tree,word);
            count++;
        }
        System.out.println("Finished populating "+count+" words");
    }

    private void addToTree(Tree tree,String word) {
        Node cur=tree.getRoot();
        Node prev=null;
        for(char c:word.toCharArray()) {
            prev=cur;
            int index=getIndex(c);
            cur=cur.getChild()[index];
            if(cur==null) {
                cur=new Node(c,false);
                prev.getChild()[index]=cur;
            }
        }
        cur.setIsEnd(true);
    }

    private List<String> obtainMatchingWord(Tree tree,String searchPattern) {
        Node cur=tree.getRoot();
        for(char c:searchPattern.toCharArray()) {
            cur=cur.getChild()[getIndex(c)];
            if(cur==null) {
                return null;
            }
        }
        List<String> matchingPatterns=new ArrayList<>();
        iterateBreadthFirst(cur,searchPattern,matchingPatterns);
        return matchingPatterns;
    }

    private void iterateDepthFirst(Node node, String searchPattern, List<String> matchingPatterns) {
        for(Node child:node.getChild()) {
            if(child!=null) {
                String newPattern=searchPattern+child.getData();
                iterateDepthFirst(child,newPattern,matchingPatterns);
            }
        }
        if(node.isEnd()) {
            matchingPatterns.add(searchPattern);
        }
    }

    private void iterateBreadthFirst(Node node, String searchPattern, List<String> matchingPatterns) {
        Queue<Node> nodeQueue=new LinkedList<>();
        Queue<String> wordQueue=new LinkedList<>();
        nodeQueue.add(node);
        wordQueue.add(searchPattern);
        while (!nodeQueue.isEmpty()) {
            Node cur=nodeQueue.remove();
            String matchWord=wordQueue.remove();
            if(cur.isEnd()) {
                matchingPatterns.add(matchWord);
            }
            for(Node child:cur.getChild()) {
                if(child!=null) {
                    String newPattern=matchWord+child.getData();
                    nodeQueue.add(child);
                    wordQueue.add(newPattern);
                }
            }
        }
    }

    private int getIndex(char c) {
        int index=c-'a';
        if(index>25 || index<0)
            return 26;
        return index;
    }

    private  void displayList(List<String> wordList) {
        if(wordList==null) {
            System.out.println("No words found!");
        }
        else  {
            for(String word:wordList) {
                System.out.println(word);
            }
        }
    }
}
