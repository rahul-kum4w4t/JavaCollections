package in.zero.link.graph;


import java.util.*;
import java.util.stream.Collectors;

import in.zero.array.Queue;
import in.zero.array.Stack;

/**
 * Generic type undirected Graph DS
 * @param <T> Generic type
 * @author rahul.kumawat
 * @since 22-05-2024
 */
public class UndirectedGraph<T> {

    public static void main(String[] args) {
        UndirectedGraph<Integer> g = new UndirectedGraph<>();
//        g.add(1, 2);
//        g.add(1, 3);
//        g.add(2, 4);
//        g.add(2, 3);
//        g.add(2, 5);
//        g.add(3, 4);
//        g.add(3, 5);
//        g.add(4, 5);
//        g.add(4, 6);
//        g.add(4, 7);
//        g.add(8, 10);
//        g.add(9, 10);
        g.add(1, 2);
        g.add(1, 3);
        g.add(2, 4);
        g.add(2, 5);
        g.add(3, 6);
        g.add(3, 7);
        g.add(4, 8);
        g.add(4, 9);
        g.add(5, 10);
        g.add(5, 11);
        g.add(6, 12);
        g.add(6, 13);
        g.add(7, 14);
        g.add(7, 15);
        g.add(8, 16);
        g.add(8, 17);
        g.add(9, 18);
        g.add(9, 19);
        g.add(10, 20);
        g.add(10, 21);
        g.add(11, 22);
        g.add(11, 23);
        g.add(12, 24);
        g.add(12, 25);
        g.add(13, 26);
        g.add(13, 27);
        g.add(14, 28);
        g.add(14, 29);
        g.add(15, 30);
        g.add(15, 31);
        g.add(16, 32);
        g.add(16, 33);
        g.add(17, 34);
        g.add(17, 35);
        g.add(18, 36);
        g.add(18, 37);
        g.add(19, 38);
        g.add(19, 39);
        g.add(20, 40);
        g.add(20, 41);
        g.add(21, 42);
        g.add(21, 43);
        g.add(22, 44);
        g.add(22, 45);
        g.add(23, 46);
        g.add(23, 47);
        g.add(24, 48);
        g.add(24, 49);
        g.add(25, 50);
        g.add(25, 51);
        g.add(26, 52);
        g.add(26, 53);
        g.add(27, 54);
        g.add(27, 55);
        g.add(28, 56);
        g.add(28, 57);
        g.add(29, 58);
        g.add(29, 59);
        g.add(30, 60);
        g.add(30, 61);
        g.add(31, 62);
        g.add(31, 63);
        g.add(32, 64);
        g.add(32, 65);
        g.add(33, 66);
        g.add(33, 67);
        g.add(34, 68);
        g.add(34, 69);
        g.add(35, 70);
        g.add(35, 71);
        g.add(36, 72);
        g.add(36, 73);
        g.add(37, 74);
        g.add(37, 75);
        g.add(38, 76);
        g.add(38, 77);
        g.add(39, 78);
        g.add(39, 79);
        g.add(40, 80);
        g.add(40, 81);
        g.add(41, 82);
        g.add(41, 83);
        g.add(42, 84);
        g.add(42, 85);
        g.add(43, 86);
        g.add(43, 87);
        g.add(44, 88);
        g.add(44, 89);
        g.add(45, 90);
        g.add(45, 91);
        g.add(46, 92);
        g.add(46, 93);
        g.add(47, 94);
        g.add(47, 95);
        g.add(48, 96);
        g.add(48, 97);
        g.add(49, 98);
        g.add(49, 99);
        g.add(50, 100);
        g.add(50, 101);
        g.add(51, 102);
        g.add(51, 103);
        g.add(52, 104);
        g.add(52, 105);
        g.add(53, 106);
        g.add(53, 107);
        g.add(54, 108);
        g.add(54, 109);
        g.add(55, 110);
        g.add(55, 111);
        g.add(56, 112);
        g.add(56, 113);
        g.add(57, 114);
        g.add(57, 115);
        g.add(58, 116);
        g.add(58, 117);
        g.add(59, 118);
        g.add(59, 119);
        g.add(60, 120);
        g.add(60, 121);
        g.add(61, 122);
        g.add(61, 123);
        g.add(62, 124);
        g.add(62, 125);
        g.add(63, 126);
        g.add(63, 127);
        g.add(64, 128);
        g.add(64, 129);
        g.add(65, 130);
        g.add(65, 131);
        g.add(66, 132);
        g.add(66, 133);
        g.add(67, 134);
        g.add(67, 135);
        g.add(68, 136);
        g.add(68, 137);
        g.add(69, 138);
        g.add(69, 139);
        g.add(70, 140);
        g.add(70, 141);
        g.add(71, 142);
        g.add(71, 143);
        g.add(72, 144);
        g.add(72, 145);
        g.add(73, 146);
        g.add(73, 147);
        g.add(74, 148);
        g.add(74, 149);
        g.add(75, 150);
        g.add(75, 151);
        g.add(76, 152);
        g.add(76, 153);
        g.add(77, 154);
        g.add(77, 155);
        g.add(78, 156);
        g.add(78, 157);
        g.add(79, 158);
        g.add(79, 159);
        g.add(80, 160);
        g.add(80, 161);
        g.add(81, 162);
        g.add(81, 163);
        g.add(82, 164);
        g.add(82, 165);
        g.add(83, 166);
        g.add(83, 167);
        g.add(84, 168);
        g.add(84, 169);
        g.add(85, 170);
        g.add(85, 171);
        g.add(86, 172);
        g.add(86, 173);
        g.add(87, 174);
        g.add(87, 175);
        g.add(88, 176);
        g.add(88, 177);
        g.add(89, 178);
        g.add(89, 179);
        g.add(90, 180);
        g.add(90, 181);
        g.add(91, 182);
        g.add(91, 183);
        g.add(92, 184);
        g.add(92, 185);
        g.add(93, 186);
        g.add(93, 187);
        g.add(94, 188);
        g.add(94, 189);
        g.add(95, 190);
        g.add(95, 191);
        g.add(96, 192);
        g.add(96, 193);
        g.add(97, 194);
        g.add(97, 195);
        g.add(98, 196);
        g.add(98, 197);
        g.add(99, 198);
        g.add(99, 199);
        g.add(100, 200);
        g.add(1, 200);
        g.add(200, 300);
        g.add(300, 400);
        g.add(400, 500);
        g.add(500, 600);
        g.add(600, 700);
        g.add(700, 800);
        g.add(800, 900);
        g.add(900, 1000);
        g.add(1000, 1);
        g.add(1, 100);
        g.add(50, 150);
        g.add(100, 200);
        g.add(150, 250);
        g.add(200, 300);
        g.add(250, 350);
        g.add(300, 400);
        g.add(350, 450);
        g.add(400, 500);
        g.add(450, 550);
        g.add(500, 600);
        g.add(550, 650);
        g.add(600, 700);
        g.add(650, 750);
        g.add(700, 800);
        g.add(750, 850);
        g.add(800, 900);
        g.add(850, 950);
        g.add(900, 1000);


        System.out.println("************ Graph ****************");
        System.out.println(g);
        System.out.println("************ DFT ****************");
        System.out.println(g.depthFirstTraversal(4).stream().map(String::valueOf).collect(Collectors.joining(", ")));
        System.out.println("************ BFT ****************");
        System.out.println(g.breadthFirstTraversal(4).stream().map(String::valueOf).collect(Collectors.joining(", ")));

        long start;
        System.out.println("************ Find Path Recursive ****************");
        start = System.currentTimeMillis();
        System.out.println(g.findPathRecr(1, 7).stream().map(path -> path.stream().map(String::valueOf).collect(Collectors.joining(", "))).collect(Collectors.joining("\n")));
        System.out.println("Execution Time (ns): " + (System.currentTimeMillis() - start));
        System.out.println("************ Find Path Iterative ****************");
        start = System.currentTimeMillis();
        System.out.println(g.findPathIter(1, 7).stream().map(path -> path.stream().map(String::valueOf).collect(Collectors.joining(", "))).collect(Collectors.joining("\n")));
        System.out.println("Execution Time (ns): " + (System.currentTimeMillis() - start));

    }

    private final Map<T, List<T>> nodes;

    public UndirectedGraph() {
        nodes = new HashMap<>();
    }

    /**
     * add any node to the graph
     * @param src
     * @param dest
     */
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

    /**
     * Provides string conversion for graph, in the form of edges
     * @return
     */
    public String toString() {
        return nodes.entrySet().stream().map(entry -> {
            final Object key = entry.getKey();
            return entry.getValue().stream().map(v -> "{" + key + "," + v + "}").collect(Collectors.joining(","));
        }).collect(Collectors.joining(","));
    }

    /**
     * Provides neighbours of any node/vertex
     * @param src node under consideration
     * @return all the unique neighbours
     */
    public Set<T> getNeighbours(T src) {
        return new HashSet<>(nodes.getOrDefault(src, new ArrayList<>()));
    }

    /**
     * Provides Depth first search of any graph, If any starting node is not given
     *
     * @return Depth traversal of graph
     */
    public List<T> depthFirstTraversal() {

        if (!nodes.isEmpty()) {
            Optional<T> key = nodes.keySet().stream().findFirst();
            return depthFirstTraversal(key.get());
        } else {
            return depthFirstTraversal(null);
        }
    }

    /**
     * Provides Depth first search of any graph, If any starting node is given
     *
     * @param currNode Starting node
     * @return Depth traversal of graph
     */
    public List<T> depthFirstTraversal(T currNode) {
        if (currNode != null && nodes.containsKey(currNode)) {
            List<T> traversal = new ArrayList<>(nodes.size());
            Set<T> visited = new HashSet<>(nodes.size());
            depthFirstTraversal(currNode, traversal, visited);

            if (nodes.size() > visited.size()) {
                for (T k : nodes.keySet()) {
                    if (!visited.contains(k)) {
                        depthFirstTraversal(k, traversal, visited);
                    }
                }
            }
            return traversal;
        } else {
            return new ArrayList<>(0);
        }
    }

    @SuppressWarnings("unchecked")
    private void depthFirstTraversal(T currNode, List<T> traversal, Set<T> visited) {

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

    /**
     * Provides Breadth first traversal of graph, If any starting node is not given
     *
     * @return Breadth traversal of graph
     */
    public List<T> breadthFirstTraversal() {
        if (!nodes.isEmpty()) {
            Optional<T> key = nodes.keySet().stream().findFirst();
            return breadthFirstTraversal(key.get());
        } else {
            return breadthFirstTraversal(null);
        }
    }

    /**
     * Provides Breadth first traversal of graph, If any starting node is given
     *
     * @param currNode Starting node
     * @return Breadth traversal of graph
     */
    public List<T> breadthFirstTraversal(T currNode) {
        if (currNode != null && nodes.containsKey(currNode)) {
            Set<T> visited = new HashSet<>(nodes.size());
            List<T> traversal = new ArrayList<>(nodes.size());
            breadthFirstTraversal(currNode, traversal, visited);

            if (nodes.size() > visited.size()) {
                for (T k : nodes.keySet()) {
                    if (!visited.contains(k)) {
                        breadthFirstTraversal(k, traversal, visited);
                    }
                }
            }
            return traversal;
        } else {
            return new ArrayList<>(0);
        }
    }

    private void breadthFirstTraversal(T currNode, List<T> traversal, Set<T> visited) {

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

    /**
     * Find path between two vertices
     * Iterative approach, Little slower but can prevent memory overflow, But utilises more memory comparative to recursive approach
     *
     * @param src  Source node
     * @param dest Destination node
     * @return List of all paths
     */
    public List<List<T>> findPathIter(T src, T dest) {

        List<List<T>> paths = new ArrayList<>();
        Stack<Object[]> stack = new Stack<>(nodes.size());
        List<T> path = new ArrayList<>(nodes.size());
        Set<T> visited = new HashSet<>(nodes.size());

        path.add(src);
        visited.add(src);
        int neighIndex = -1;

        while (src != null) {
            List<T> neigh = nodes.get(src);
            while ((++neighIndex) < neigh.size() && visited.contains(neigh.get(neighIndex))) ;
            if (neighIndex < neigh.size()) {
                stack.push(new Object[]{src, neighIndex});
                src = neigh.get(neighIndex);
                path.add(src);
                if (src == dest) {
                    paths.add(new ArrayList<>(path));
                    path.remove(src);
                    Object[] cache = stack.pop();
                    src = (T) cache[0];
                    neighIndex = (int) cache[1];
                } else {
                    visited.add(src);
                    neighIndex = -1;
                }
            } else if (!stack.isEmpty()) {
                visited.remove(src);
                path.remove(src);
                Object[] cache = stack.pop();
                src = (T) cache[0];
                neighIndex = (int) cache[1];
            } else {
                src = null;
            }
        }
        return paths;
    }

    /**
     * Find path between two vertices
     * Recursive approach, Little faster but can make memory overflow in case of a very big complicated graph
     *
     * @param src  Source node
     * @param dest Destination node
     * @return List of all paths
     */
    public List<List<T>> findPathRecr(T src, T dest) {
        if (src != null && dest != null) {
            Set<T> visited = new HashSet<>(nodes.size());
            List<T> path = new ArrayList<>(nodes.size());
            List<List<T>> paths = new ArrayList<>();
            findPathRecur(src, dest, visited, path, paths);
            return paths;
        }
        return new ArrayList<>(0);
    }

    private void findPathRecur(T currNode, T dest, Set<T> visited, List<T> path, List<List<T>> paths) {

        if (currNode == dest) {
            path.add(currNode);
            paths.add(new ArrayList<>(path));
            path.remove(currNode);
        } else {
            visited.add(currNode);
            path.add(currNode);
            for (T node : nodes.get(currNode)) {
                if (!visited.contains(node)) {
                    findPathRecur(node, dest, visited, path, paths);
                }
            }
            visited.remove(currNode);
            path.remove(currNode);
        }
    }
}