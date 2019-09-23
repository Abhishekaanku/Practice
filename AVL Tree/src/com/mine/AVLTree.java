package com.mine;

import java.util.*;

public class AVLTree<T> {
    Comparator<T> comparator;
    Node<T> root;

    AVLTree(Node<T> root, Comparator<T> comparator) {
        this.comparator=comparator;
        this.root=root;
    }

    AVLTree(Comparator<T> comparator) {
        this(null,comparator);
    }

    static class Node<T>{
        private T data;
        Node<T> left;
        Node<T> right;

        Node(T data) {
            this.data=data;
            left=right=null;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Node && data.equals(((Node)obj).getData());
        }
    }

    void inOrder(Node<T> node,List<T> result) {
        if(node!=null) {
            inOrder(node.left,result);
            result.add(node.getData());
            inOrder(node.right,result);
        }
    }

    void PreOrder(Node<T> node,List<T> result) {
        if(node!=null) {
            result.add(node.getData());
            inOrder(node.left,result);
            inOrder(node.right,result);
        }
    }

    void PostOrder(Node<T> node,List<T> result) {
        if(node!=null) {
            inOrder(node.left,result);
            inOrder(node.right,result);
            result.add(node.getData());
        }
    }

    List<T> breadthFirst(Node<T> node) {
        Queue<Node<T>> queue=new LinkedList<>();
        queue.add(node);
        List<T> traversal=new ArrayList<>();
        while(!queue.isEmpty()) {
            Node<T> temp=queue.remove();
            if(temp.left!=null)
                queue.add(temp.left);
            if(temp.right!=null)
                queue.add(temp.right);
            traversal.add(temp.getData());
        }
        return traversal;
    }

    void insert(T data) {
        insert(data,root);
    }

    void insert(T data,Node<T> node) {
        if(node==null) {
            root=new Node<>(data);
        }
        else if(comparator.compare(node.getData(),data)>0) {
            if(node.left==null) {
                node.left=new Node<>(data);
            }
            else {
                insert(data,node.left);
            }
        }
        else if(comparator.compare(node.getData(),data)<0) {
            if(node.right==null) {
                node.right=new Node<>(data);
            }
            else  {
                insert(data,node.right);
            }
        }
    }

    Node<T> search(T data) {
        return search(data,root);
    }

    Node<T> search(T data,Node<T> node) {
        if(node==null) {
            return null;
        }
        else if(comparator.compare(node.getData(),data)>0) {
            return search(data,node.left);
        }
        else if (comparator.compare(node.getData(),data)<0) {
            return search(data,node.right);
        }
        else {
            return node;
        }
    }

    Node<T> searchParent(T data,Node<T> node) {
        Node<T> child=null;
        if(comparator.compare(node.getData(),data)>0) {
            child=node.left;
        }
        else if(comparator.compare(node.getData(),data)<0) {
            child=node.right;
        }
        else {
            return null;
        }
        if(child!=null) {
            if(comparator.compare(node.getData(),data)>0) {
                return searchParent(data,child);
            }
            else if(comparator.compare(node.getData(),data)<0) {
                return searchParent(data,child);
            }
            else {
                return node;
            }
        }
        return null;
    }

    void delete(T data) {
        delete(data,root);
    }

    void delete(T data,Node<T> node) {
        if(node!=null) {
            if(comparator.compare(node.getData(),data)>0) {
                Node<T> child=node.left;
                if(child!=null && child.getData().equals(data)) {
                    if(child.left==null && child.right==null) {
                        node.left=null;
                    }
                    else if(child.right!=null) {
                        node.left=child.right;
                    }
                    else if(child.left!=null) {
                        node.left=child.left;
                    }
                    else {
                        Node<T> successor=getInOrderSuccessor(child);
                        T temp=successor.getData();
                        delete(successor.getData(),child);
                        child.setData(temp);
                    }
                }
                else {
                    delete(data,child);
                }
            }
            else if(comparator.compare(node.getData(),data)<0) {
                Node<T> child=node.right;
                if(child!=null && child.getData().equals(data)) {
                    if(child.left==null && child.right==null) {
                        node.right=null;
                    }
                    else if(child.right!=null) {
                        node.right=child.right;
                    }
                    else if(child.left!=null) {
                        node.right=child.left;
                    }
                    else {
                        Node<T> successor=getInOrderSuccessor(child);
                        T temp=successor.getData();
                        delete(successor.getData(),child);
                        child.setData(temp);
                    }
                }
                else {
                    delete(data,child);
                }
            }
            else {
                if(node.left==null) {
                    this.root=node.right;
                }
                else if(node.right==null) {
                    this.root=node.left;
                }
                else {
                    Node<T> successor=getInOrderSuccessor(node);
                    T temp=successor.getData();
                    delete(successor.getData(),node);
                    node.setData(temp);
                }
            }
        }
    }


    private Node<T> getInOrderSuccessor(Node<T> node) {
        node=node.right;
        while(node.left!=null) {
            node=node.left;
        }
        return node;
    }

    void balance() {
        List<T> traversal=new ArrayList<>();
        inOrder(root,traversal);
        root=addToTree(traversal.toArray(),0,traversal.size()-1);
    }

    Node<T> addToTree(Object[] elements,int start,int end) {
        if(end>=start) {
            int middle=(start+end)/2;
            Node<T> node=new Node<>((T)elements[middle]);
            node.left=addToTree(elements,start,middle-1);
            node.right=addToTree(elements,middle+1,end);
            return node;
        }
        return null;
    }



}
