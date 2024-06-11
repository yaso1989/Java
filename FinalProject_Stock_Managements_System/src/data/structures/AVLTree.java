package data.structures;

import base.types.DealerInt;

public class AVLTree {
    private AVLNode root;

    public AVLTree() {
        this.root = null;
    }

    // Insert a node into the AVL tree
    //The insert method should take both key and value as parameters
    public void insert(String key, Object value) {
        root = insertNode(root, key, value);
    }

    // Search for a node in the AVL tree
    public Object search(String key) {
        return searchNode(root, key);
    }

    private Object searchNode(AVLNode node, String key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node.value;
        else if (cmp < 0) return searchNode(node.left, key);
        else return searchNode(node.right, key);
    }

    // Method to insert a node into the AVL tree
    private AVLNode insertNode(AVLNode node, String key, Object value) {
        if (node == null)
            return new AVLNode(key, value);

        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = insertNode(node.left, key, value);
        else if (cmp > 0)
            node.right = insertNode(node.right, key, value);
        else // Key already exists, update the value
            node.value = value;

        // Update height
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Perform rotation if needed
        return balance(node);
    }

    // Method to balance the AVL tree after the rotation
    private AVLNode balance(AVLNode node) {
        if (node == null)
            return node;

        int balance = getBalance(node);

        // Left heavy
        if (balance > 1) {
            if (height(node.left.left) >= height(node.left.right))
                return rightRotate(node);
            else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        // Right heavy
        else if (balance < -1) {
            if (height(node.right.right) >= height(node.right.left))
                return leftRotate(node);
            else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    // Right rotation
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotation
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Get balance factor of a node
    private int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    // Get height of a node
    private int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

//This method typically handles scenarios such as checking if the tree is empty and then calls the private recursive deletion method (deleteNode)
// passing the root node and the key to be deleted.
    public void delete(String key) {
        root = deleteNode(root, key);
    }

    private AVLNode deleteNode(AVLNode node, String key) {
        if (node == null)
            return node;

        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = deleteNode(node.left, key);
        else if (cmp > 0)
            node.right = deleteNode(node.right, key);
        else {
            // Node with only one child or no child
            if (node.left == null || node.right == null) {
                AVLNode temp = null;
                if (temp == node.left)
                    temp = node.right;
                else
                    temp = node.left;

                // No child case
                if (temp == null) {
                    temp = node;
                    node = null;
                } else // One child case
                    node = temp; // Copy the contents of the non-empty child

                temp = null;
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                AVLNode temp = minValueNode(node.right);

                // Copy the inorder successor's data to this node
                node.key = temp.key;
                node.value = temp.value;

                // Delete the inorder successor
                node.right = deleteNode(node.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (node == null)
            return node;

        // Update height of the current node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Rebalanced the tree
        return balance(node);
    }

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;

        // Loop down to find the leftmost leaf
        while (current.left != null)
            current = current.left;

        return current;
    }



    // Private helper method to search for a customer or supplier by name or code
    private Object search(AVLNode node, String key) {
        if (node == null)
            return null;

        Object result = null;
        if (node.key.equals(key)) {
            result = node.value;
        }

        Object leftResult = search(node.left, key);
        Object rightResult = search(node.right, key);

        // If the result is already found in the left subtree, return it
        if (leftResult != null)
            return leftResult;

        // If the result is already found in the right subtree, return it
        if (rightResult != null)
            return rightResult;

        // If the result is found in the current node or in neither subtree, return it
        return result;
    }


    public Object searchByName(String key) {

        return searchNodeByName(root, key);
    }
    private Object searchNodeByName(AVLNode node, Object targetValue) {
        if (node == null) {
            return null;
        }
        if (!(node.value instanceof DealerInt)){
            return null;
        }
        // Check if the attribute value of the object matches the target value
        // Replace "getAttributeValue()" with the method to access the desired attribute
        Object nodeValue = ((DealerInt)node.value).getName();
        //System.out.println(((base.types.DealerInt)node.value).getName());
        if (nodeValue.equals(targetValue)) {
            //System.out.println(node.value.toString());
            return node.value;
        }
        // Recursively search in the left and right subtrees
        Object foundLeft = searchNodeByName(node.left,  targetValue);
        Object foundRight = searchNodeByName(node.right,  targetValue);
        return  foundLeft != null ? foundLeft : foundRight;
    }




}
