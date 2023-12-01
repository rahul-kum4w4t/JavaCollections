package in.zero.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayUtilsTest {

    @Test
    void shiftTest() {
        Integer[] data = {4, 2, 9, 7, 3, 0, 1, 6, 8, 5};

        assertEquals(3, ArrayUtils.shift(data, 4));
        assertTrue(Arrays.stream(data).filter(Objects::nonNull).allMatch(val -> val != 3));

        assertEquals(4, ArrayUtils.shift(data, 0));
        assertTrue(Arrays.stream(data).filter(Objects::nonNull).allMatch(val -> val != 4));

        assertEquals(5, ArrayUtils.shift(data, 7));
        assertTrue(Arrays.stream(data).filter(Objects::nonNull).allMatch(val -> val != 5));

        assertArrayEquals(Arrays.copyOfRange(data, 0, 7), new Integer[]{2, 9, 7, 0, 1, 6, 8});
    }

    @Test
    void unshiftTest() {
        Integer[] data = {2, 9, 7, 0, 1, 6, 8, null, null, null};

        ArrayUtils.unshift(data, 3, 3);
        assertEquals(data[3], 3);

        ArrayUtils.unshift(data, 0, 4);
        assertEquals(data[0], 4);

        ArrayUtils.unshift(data, 9, 5);
        assertEquals(data[9], 5);

        assertArrayEquals(data, new Integer[]{4, 2, 9, 7, 3, 0, 1, 6, 8, 5});

        ArrayUtils.unshift(data, 7, -1);
        assertTrue(Arrays.stream(data).filter(Objects::nonNull).allMatch(val -> val != 5));
    }

    @Test
    void insertTest() {
        Integer[] data = {2, 9, 7, 0, 1, 6, 8, null, null, null};
        ArrayUtils.copyRangeToAnotherArray(new Integer[]{5, 3, 4}, 0, 3, data, 7, data.length);
        assertArrayEquals(data, new Integer[]{2, 9, 7, 0, 1, 6, 8, 5, 3, 4});

        data = new Integer[]{2, 9, 7, 0, 1, 6, 8, null};
        ArrayUtils.copyRangeToAnotherArray(new Integer[]{-1, -2, -3, -4, -5}, 0, 5, data, 4, data.length);
        assertArrayEquals(data, new Integer[]{2, 9, 7, 0, -1, -2, -3, -4});
    }
}














