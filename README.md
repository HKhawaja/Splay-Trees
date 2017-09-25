# Splay-Trees

An implementation of a Splay BST in Java. The nodes are intervals of integer values, and the Data Structure supports the standard BST functions (add, remove, contains, size) as well as a nextExcluded* function, all in O(log n) amortized time, except for size, which is O(1).

*nextExcluded(x) must return the smallest integer greater than or equal to x that is not currently in the set.
