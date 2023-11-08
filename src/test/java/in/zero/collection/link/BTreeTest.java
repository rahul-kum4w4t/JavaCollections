package in.zero.collection.link;

import in.zero.collection.BTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

public class BTreeTest {

    final static Integer[] testData = {10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35, 42, 51, 28, 80, 11};

    @Test
    void testAdd() {
        BTree<Integer> tree = new BTree<>(Integer.class, true, 10);
        Arrays.stream(testData).forEach(val -> {
            tree.add(val);
        });
        System.out.println(tree);
        assertTrue(Arrays.stream(testData).allMatch(tree::search), "Btree data not found");
    }
}
