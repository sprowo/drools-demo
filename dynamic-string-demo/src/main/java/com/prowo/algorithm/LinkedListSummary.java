package com.prowo.algorithm;

/**
 * @author prowo
 * @date 2017/12/9
 */
public class LinkedListSummary {

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

        Node node6 = new Node(2);
        Node node7 = new Node(4);
        Node node8 = new Node(5);
        Node node9 = new Node(8);
        Node node10 = new Node(10);

        node6.next = node7;
        node7.next = node8;
        node8.next = node9;
        node9.next = node10;

//        System.err.println(getListLength(node1));
//        System.err.println(reverseList(node1));
//        System.err.println(reverseListRec(node1));
//        System.err.println(getMiddleNode(node1).value);
//        System.err.println(getBwNode(node1, 2).value);
//        printListFromTailToHead(node1);
//        System.err.println(hasCycle(node1));
//        System.err.println(mergeSortedList(node1, node6));
//        System.err.println(mergeSortedListRec(node1, node6));
        System.err.println(isIntersect(node1, node6).value);
    }

    /**
     * 进入环的第一个节点
     * @param head
     * @return
     */
    public static Node getFirstNodeInCycle(Node head) {
        if (head == null) {
            return null;
        }

        Node fast = head;
        Node slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }

        if (fast == null || fast.next == null) {
            return null;
        }

        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    /**
     * 两个链表相交的第一个节点
     *
     * @param head1
     * @param head2
     * @return
     */
    public static Node isIntersect(Node head1, Node head2) {
        Node target = null;

        if (head1 == null || head2 == null) {
            return target;
        }

        int len1 = getListLength(head1);
        int len2 = getListLength(head2);

        if (len1 < len2) {
            for (int i = 0; i < len2 - len1; i++) {
                head2 = head2.next;
            }
        } else {
            for (int i = 0; i < len1 - len2; i++) {
                head1 = head1.next;
            }
        }

        while (head1 != null && head2 != null) {
            if (head1.value == head2.value) {
                target = head1;
                break;
            } else {
                head1 = head1.next;
                head2 = head2.next;
            }
        }

        return target;
    }

    /**
     * 递归合并排序链表
     *
     * @param head1
     * @param head2
     * @return
     */
    public static Node mergeSortedListRec(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        if (head1.value < head2.value) {
            head1.next = mergeSortedListRec(head1.next, head2);
            return head1;
        } else {
            head2.next = mergeSortedListRec(head1, head2.next);
            return head2;
        }
    }

    /**
     * 合并有序链表
     *
     * @param head1
     * @param head2
     * @return
     */
    public static Node mergeSortedList(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        Node mergeHead;
        if (head1.value < head2.value) {
            mergeHead = head1;
            head1 = head1.next;
        } else {
            mergeHead = head2;
            head2 = head2.next;
        }

        Node target = mergeHead;
        target.next = null;

        while (head1 != null && head2 != null) {
            if (head1.value < head2.value) {
                target.next = head1;
                head1 = head1.next;
            } else {
                target.next = head2;
                head2 = head2.next;
            }
            target = target.next;
            target.next = null;
        }

        if (head1 != null) {
            target.next = head1;
        }
        if (head2 != null) {
            target.next = head2;
        }

        return mergeHead;
    }

    /**
     * 判断链表是否有环
     *
     * @param head
     * @return
     */
    public static boolean hasCycle(Node head) {
        Node p1 = head;
        Node p2 = head;

        while (p1 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1 == p2) {
                return true;
            }
        }

        return false;
    }

    /**
     * 从尾到头打印链表
     *
     * @param head
     */
    public static void printListFromTailToHead(Node head) {
        if (head == null) {
            return;
        }
        printListFromTailToHead(head.next);
        System.err.print(head.value + " ");
    }

    /**
     * 获取倒数第k个节点
     *
     * @param head
     * @return
     */
    public static Node getBwNode(Node head, int k) {
        if (head == null) {
            return head;
        }

        int len = getListLength(head);
        if (len < k) {
            return null;
        }

        Node target = head;
        Node next = head;

        for (int i = 0; i < k; i++) {
            next = next.next;
        }

        while (next != null) {
            target = target.next;
            next = next.next;
        }

        return target;
    }

    /**
     * 获取中间节点
     *
     * @param head
     * @return
     */
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

    /**
     * 计算链表的长度
     *
     * @param head
     * @return
     */
    public static int getListLength(Node head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }

    /**
     * 反转
     *
     * @param head
     * @return
     */
    private static Node reverseList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node pre = null;
        Node next;

        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }

    /**
     * 反转-递归
     *
     * @param head
     * @return
     */
    public static Node reverseListRec(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node reHead = reverseListRec(head.next);
        head.next.next = head;
        head.next = null;
        return reHead;
    }

    public static class Node {
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
