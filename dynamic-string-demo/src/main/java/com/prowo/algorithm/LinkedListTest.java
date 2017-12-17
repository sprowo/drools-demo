package com.prowo.algorithm;

/**
 * @author prowo
 * @date 2017/12/17
 */
public class LinkedListTest {

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(3);
        Node node3 = new Node(5);
        Node node4 = new Node(7);
        Node node5 = new Node(9);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

//        System.err.println(reverseList(node1));
//        System.err.println(getLength(node1));
//        System.err.println(getMiddleNode(node1));
//        printFromTail(node1);
        System.err.println(getBwNode(node1, 5).value);

    }

    public static Node getBwNode(Node head, int k) {
        int len = getLength(head);

        if (len < k) {
            return null;
        }

        Node next = head;
        for (int i = 0; i < k; i++) {
            next = next.next;
        }

        while (next != null) {
            head = head.next;
            next = next.next;
        }

        return head;
    }

    public static void printFromTail(Node head) {
        if (head == null) {
            return;
        }
        printFromTail(head.next);
        System.err.println(head.value);
    }

    public static Node getMiddleNode(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node next = head;

        while (next != null && next.next != null) {
            head = head.next;
            next = next.next.next;
        }

        return head;
    }

    public static int getLength(Node head) {
        int len = 0;

        while (head != null) {
            head = head.next;
            len++;
        }

        return len;
    }

    public static Node reverseList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node pre = null;
        Node next = null;

        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }


    private static class Node {
        int value;
        Node next;

        public Node(int n) {
            this.value = n;
            this.next = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

}
