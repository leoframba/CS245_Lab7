import java.util.LinkedList;
import java.util.Queue;
/*
 * CS 245 Lab 07
 * @author Leonardo Framba
 */
public class HouseRobbing {
    /*
     * Node class for a binary tree
     */
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /*
     * The root of the binary tree
     */
    TreeNode root;

    /*
     * Default constructor set the root node to null
     */
    public HouseRobbing() {
        root = null;
    }

    /**
     * Insert an item to the tree
     *
     * @param val value to insert
     */
    public void insert(int val) {
        root = insert(val, root);
    }

    /**
     * Function override of the insert function
     *
     * @param val  value to add
     * @param node current node
     * @return root of the binary tree
     */
    private TreeNode insert(int val, TreeNode node) {
        if (node == null) {
            node = new TreeNode(val);
            return node;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            node = queue.peek();
            queue.remove();
            if (node.left == null) {
                node.left = new TreeNode(val);
                break;
            } else {
                queue.add(node.left);
            }
            if (node.right == null) {
                node.right = new TreeNode(val);
                break;
            } else {
                queue.add(node.right);
            }
        }
        return root;
    }

    /**
     * Function that prints the binary tree
     */
    public void print() {
        print(root);
    }

    /**
     * Helper Function to print the Binary tree
     *
     * @param root the root of the tree
     */
    private void print(TreeNode root) {
        if (root != null) {
            int height = height(root);
            for (int i = 1; i <= height; i++) {
                System.out.printf("Level %d: ", i);
                printCurrentLevel(root, i);
                System.out.println();
            }
        } else {
            System.err.println("Null Node");
        }
    }

    /**
     * Helper Function to find the height of the tree
     *
     * @param node node of the tree
     */
    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            int leftHeight = height(node.left);
            int rightHeight = height(node.right);
            return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
        }
    }

    /**
     * Helper function to print the current level of the binary tree
     *
     * @param node node to print
     * @param lvl  height/level of the tree
     */
    private void printCurrentLevel(TreeNode node, int lvl) {
        if (node == null) return;
        if (lvl == 1) System.out.print(node.val + " ");
        else if (lvl > 1) {
            printCurrentLevel(node.left, lvl - 1);
            printCurrentLevel(node.right, lvl - 1);
        }
    }

    /**
     * Function that determines the maximum amount of the money
     * from each node that contains different amount of money @TreeNode.val
     * if the @TreeNode.val is 0, it means the child node is null(placeholder)
     * There is no left/right child(house), as the visualized in the pdf
     *
     * @param node entrance point, root of the tree(town)
     * @return maximum amount of money
     *
     * @see Math#max(int, int) this hint is optional to use
     */
    public int rob(TreeNode node) {
        //If the tree is empty nothing can be robbed
        if(node == null) return 0;

        //Result stores the max amount of two paths.
        //Root[0] is when the root is selected
        //Root[1] is when it's not
        //Use math.max to return the greater of the two paths
        int[] result = robHelper(node);
        return Math.max(result[0], result[1]);
    }

    /**
     * Recursive helper method for rob that returns two robbery paths(root and not root) for a
     * given node
     *
     * @param node inputted node
     * @return an array containing the maximum amount of money that can be robbed via root and
     * not root path
     */

    public int[] robHelper(TreeNode node){
        //if the node is null there's no money to be robbed return 0 for both paths
        if(node == null){
            return new int[]{0, 0};
        }

        //result array always contains two values for root and not root path
        int[] result = new int[2];
        //Recursive call through tree
        int[] left = robHelper(node.left);
        int[] right = robHelper(node.right);

        /*
         * result[0] is the root path - it is equal to the value of the current node + the max
         * amount robbed from the previous nodes children
         *
         * result[1] is the not root path - it's equal to the sum of the maximum value robbed from
         * the two children of the current node
         */
        result[0] = node.val + left[1] + right[1];
        result[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return result;
    }

    public static void main(String[] args) {
        HouseRobbing robbery = new HouseRobbing();
        robbery.insert(3);
        robbery.insert(2);
        robbery.insert(3);
        robbery.insert(0);
        robbery.insert(3);
        robbery.insert(0);
        robbery.insert(1);
        robbery.print();
        System.out.println(robbery.rob(robbery.root)); // -> 7
        //modify this main as needed to test different robberies
    }
}