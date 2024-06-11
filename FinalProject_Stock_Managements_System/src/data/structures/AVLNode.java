package data.structures;

//This class will serve as the building block for constructing the AVL tree to efficiently manage and search
class AVLNode {
    String key; //The key--- Code for products, name for customers and suppliers.//The identifier used for searching and ordering within the AVL tree.
    Object value; // The value ---base.types.Product, base.types.Customer, or base.types.Supplier object//The associated object stored in the AVL tree.
    AVLNode left;

    // references to the left and right child nodes of the current node in the AVL tree.
    AVLNode right;
    int height;//It is used to maintain the balance of the tree during insertion and deletion operations.

    public AVLNode(String key, Object value) {
        this.key = key;
        this.value = value;
        this.height = 1;//level 1 in the tree.--node with no children
    }



}
