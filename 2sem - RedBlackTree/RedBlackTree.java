public class RedBlackTree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        int key;
        Node left, right;
        boolean color;

        Node(int key, boolean color) {
            this.key = key;
            this.color = color;
        }
    }

    private Node root;
    private int operationCount = 0;

    public boolean contains(int key) {
        operationCount++;
        return contains(root, key);
    }

    private boolean contains(Node node, int key) {
        if (node == null) {
            operationCount++;
            return false;
        }
        if (key < node.key) {
            operationCount++;
            return contains(node.left, key);
        } else if (key > node.key) {
            operationCount++;
            return contains(node.right, key);
        } else {
            operationCount++;
            return true;
        }
    }

    public void insert(int key) {
        root = insert(root, key);
        root.color = BLACK;
        operationCount += 2;
    }

    private Node insert(Node node, int key) {
        if (node == null) {
            operationCount++;
            return new Node(key, RED);
        }

        if (key < node.key) {
            operationCount++;
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            operationCount++;
            node.right = insert(node.right, key);
        } else {
            operationCount++;
            node.key = key;
        }

        if (isRed(node.right) && !isRed(node.left)) {
            operationCount++;
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            operationCount++;
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            operationCount++;
            flipColors(node);
        }

        return node;
    }

    public void remove(int key) {
        if (!contains(key)) {
            operationCount++;
            return;
        }

        if (!isRed(root.left) && !isRed(root.right)) {
            operationCount++;
            root.color = RED;
        }
        operationCount++;
        root = remove(root, key);

        if (!isEmpty()) {
            operationCount++;
            root.color = BLACK;
        }
    }

    private Node remove(Node node, int key) {
        if (key < node.key) {
            if (!isRed(node.left) && !isRed(node.left.left)) {
                operationCount++;
                node = moveRedLeft(node);
            }
            operationCount++;
            node.left = remove(node.left, key);
        } else {
            if (isRed(node.left)) {
                operationCount++;
                node = rotateRight(node);
            }
            if (key == node.key && node.right == null) {
                operationCount++;
                return null;
            }
            if (!isRed(node.right) && !isRed(node.right.left)) {
                operationCount++;
                node = moveRedRight(node);
            }
            if (key == node.key) {
                operationCount += 3;
                Node minNode = findMin(node.right);
                node.key = minNode.key;
                node.right = deleteMin(node.right);
            } else {
                operationCount++;
                node.right = remove(node.right, key);
            }
        }
        return balance(node);
    }

    private Node rotateLeft(Node node) {
        operationCount += 5;
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private Node rotateRight(Node node) {
        operationCount += 5;
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private void flipColors(Node node) {
        operationCount += 3;
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    private Node moveRedLeft(Node node) {
        operationCount++;
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
            operationCount += 3;
        }
        return node;
    }

    private Node moveRedRight(Node node) {
        operationCount++;
        flipColors(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
            operationCount += 2;
        }
        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            operationCount++;
            return null;
        }

        if (!isRed(node.left) && !isRed(node.left.left)) {
            operationCount++;
            node = moveRedLeft(node);
        }

        node.left = deleteMin(node.left);
        operationCount++;
        return balance(node);
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            operationCount++;
            node = node.left;
        }
        return node;
    }

    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        operationCount++;
        return node.color == RED;
    }

    private boolean isEmpty() {
        operationCount++;
        return root == null;
    }

    private Node balance(Node node) {
        if (isRed(node.right)) {
            operationCount++;
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            operationCount++;
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            operationCount++;
            flipColors(node);
        }
        return node;
    }

    public int getOperationCount() {
        return operationCount;
    }
    public void resetOperationCount() {
        operationCount = 0;
    }
}
