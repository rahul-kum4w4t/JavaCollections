package in.zero.link.graph;


import java.util.*;
import java.util.stream.Collectors;

import in.zero.array.Queue;
import in.zero.array.Stack;

public class UndirectedGraph<T> {

    public static void main(String[] args) {
        UndirectedGraph<Integer> g = new UndirectedGraph<>();
//        g.add("a", "D");
//        g.add("D", "e");
//        g.add("K", "D");
//        g.add("b", "a");
//        g.add("a", "K");
        g.add(1, 2);
        g.add(1, 3);
        g.add(2, 4);
        g.add(2, 3);
        g.add(2, 5);
        g.add(3, 4);
        g.add(3, 5);
        g.add(4, 5);
        g.add(4, 6);
        g.add(4, 7);
        g.add(8, 10);
        g.add(9, 10);

        System.out.println(g);
        g.depthFirstTraversal(4).forEach(System.out::println);
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        g.breadthFirstTraversal(4).forEach(System.out::println);
    }

    private final Map<T, List<T>> nodes;

    public UndirectedGraph() {
        nodes = new HashMap<>();
    }

    public void add(T src, T dest) {
        if (src != null && dest != null) {
            nodes.computeIfAbsent(src, k -> new ArrayList<>());
            nodes.computeIfAbsent(dest, k -> new ArrayList<>());
            nodes.get(src).add(dest);
            nodes.get(dest).add(src);
        } else {
            throw new IllegalArgumentException("source and destination nodes must not be null");
        }
    }

    public String toString() {
        return nodes.entrySet().stream().map(entry -> {
            final Object key = entry.getKey();
            return entry.getValue().stream().map(v -> "{" + key + "," + v + "}").collect(Collectors.joining(","));
        }).collect(Collectors.joining(","));
    }

    public Set<T> getNeighbours(T src) {
        return new HashSet<>(nodes.getOrDefault(src, new ArrayList<>()));
    }

    public List<T> depthFirstTraversal() {

        if (!nodes.isEmpty()) {
            Optional<T> key = nodes.keySet().stream().findFirst();
            return depthFirstTraversal(key.get());
        } else {
            return depthFirstTraversal(null);
        }
    }

    public List<T> depthFirstTraversal(T currNode) {
        if (currNode != null && nodes.containsKey(currNode)) {
            List<T> traversal = new ArrayList<>(nodes.size());
            Set<T> visited = new HashSet<>(nodes.size());
            dfsTraversalChain(currNode, traversal, visited);

            if (nodes.size() > visited.size()) {
                for (T k : nodes.keySet()) {
                    if (!visited.contains(k)) {
                        dfsTraversalChain(k, traversal, visited);
                    }
                }
            }
            return traversal;
        } else {
            return new ArrayList<>(0);
        }
    }

    @SuppressWarnings("unchecked")
    private void dfsTraversalChain(T currNode, List<T> traversal, Set<T> visited) {

        Stack<Object[]> stack = new Stack<>(nodes.size());
        traversal.add(currNode);
        visited.add(currNode);
        int neighIndex = -1;
        while (currNode != null) {
            List<T> neigh = nodes.get(currNode);
            while ((++neighIndex) < neigh.size() && visited.contains(neigh.get(neighIndex))) ;
            if (neighIndex < neigh.size()) {
                stack.push(new Object[]{currNode, neighIndex});
                currNode = neigh.get(neighIndex);
                visited.add(currNode);
                traversal.add(currNode);
                neighIndex = -1;
            } else if (!stack.isEmpty()) {
                Object[] cache = stack.pop();
                currNode = (T) cache[0];
                neighIndex = (int) cache[1];
            } else {
                currNode = null;
            }
        }
    }

    public List<T> breadthFirstTraversal(){
        if (!nodes.isEmpty()) {
            Optional<T> key = nodes.keySet().stream().findFirst();
            return breadthFirstTraversal(key.get());
        } else {
            return breadthFirstTraversal(null);
        }
    }

    public List<T> breadthFirstTraversal(T currNode) {
        if (currNode != null && nodes.containsKey(currNode)) {
            Set<T> visited = new HashSet<>(nodes.size());
            List<T> traversal = new ArrayList<>(nodes.size());
            bfsTraversalChain(currNode, traversal, visited);

            if (nodes.size() > visited.size()) {
                for (T k : nodes.keySet()) {
                    if (!visited.contains(k)) {
                        bfsTraversalChain(k, traversal, visited);
                    }
                }
            }
            return traversal;
        } else {
            return new ArrayList<>(0);
        }
    }

    private void bfsTraversalChain(T currNode, List<T> traversal, Set<T> visited) {

        Queue<T> queue = new Queue<>(nodes.size());
        queue.enqueue(currNode);
        visited.add(currNode);
        traversal.add(currNode);

        while (!queue.isEmpty()) {
            for (T n : nodes.get(queue.dequeue())) {
                if (!visited.contains(n)) {
                    queue.enqueue(n);
                    visited.add(n);
                    traversal.add(n);
                }
            }
        }
    }
}