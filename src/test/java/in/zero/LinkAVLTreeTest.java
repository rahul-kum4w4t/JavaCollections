package in.zero;

import junit.framework.TestCase;

public class LinkAVLTreeTest extends TestCase {

    public LinkAVLTreeTest(String testName) {
        super(testName);
    }

    public void testApp() {

        final int numOfElems = 13;
        final double minHeight = Math.floor(Math.log(numOfElems) / Math.log(2));
        final double maxHeight = 1.44 * Math.log(numOfElems) / Math.log(2);
        LinkAVLTree<Integer> ints = new LinkAVLTree<>();
        ints.addAll(10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35);
        System.out.println(minHeight+":"+maxHeight);
        int height = ints.getHeight();
        assertTrue((minHeight <= height) && (maxHeight >= height));
    }
}
