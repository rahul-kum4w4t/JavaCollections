package in.zero.collection.array;

public class ArrayUtils {

    public static <T> T shift(T[] arr, int removalIndex) {
        if (arr != null) return shift(arr, removalIndex, arr.length - 1);
        return null;
    }

    public static <T> T shift(final T[] arr, final int removalIndex, final int lastIndex) {
        if (arr != null && removalIndex >= 0 && removalIndex <= lastIndex && lastIndex < arr.length) {
            T returnData = arr[removalIndex];
            for (int i = removalIndex; i < lastIndex; i++) {
                arr[i] = arr[i + 1];
            }
            arr[lastIndex] = null;
            return returnData;
        }
        return null;
    }

    public static <T> void unshift(final T[] arr, final int insertIndex, final T value, final int lastIndex) {
        if (arr != null && insertIndex >= 0 && insertIndex <= lastIndex) {
            for (int i = lastIndex; i > insertIndex; i--) {
                arr[i] = arr[i - 1];
            }
            arr[insertIndex] = value;
        }
    }

    public static <T> void unshift(final T[] arr, final int insertIndex, final T value) {
        if (arr != null) unshift(arr, insertIndex, value, arr.length - 1);
    }

    public static <T> void copyRangeToAnotherArray(
            final T[] copyFrom,
            final int cfStart,
            final int cfEnd,
            final T[] copyTo,
            final int ctStart,
            final int ctEnd
    ) {
        if (copyFrom != null && copyTo != null && cfStart >= 0 && cfStart < copyFrom.length && cfEnd > cfStart && cfEnd <= copyFrom.length && ctStart >= 0 && ctStart < copyTo.length && ctEnd > ctStart && ctEnd <= copyTo.length) {
            for (int i = cfStart, j = ctStart; i < cfEnd && j < ctEnd; j++, i++) {
                copyTo[j] = copyFrom[i];
            }
        }
    }

    public static <T> void copyRangeToAnotherArray(
            final T[] copyFrom,
            final T[] copyTo
    ) {
        copyRangeToAnotherArray(copyFrom, 0, copyFrom.length, copyTo, 0, copyTo.length);
    }

    public static <T> void copyRangeToAnotherArray(
            final T[] copyFrom,
            final int cfStart,
            final int cfEnd,
            final T[] copyTo
    ) {
        copyRangeToAnotherArray(copyFrom, cfStart, cfEnd, copyTo, 0, copyTo.length);
    }

    public static <T> void copyRangeToAnotherArray(
            final T[] copyFrom,
            final T[] copyTo,
            final int ctStart,
            final int ctEnd
    ) {
        copyRangeToAnotherArray(copyFrom, 0, copyFrom.length, copyTo, ctStart, ctEnd);
    }

    public static <T> void copyRangeToAnotherArray(
            final T[] copyFrom,
            final int cfStart,
            final T[] copyTo,
            final int ctStart
    ) {
        copyRangeToAnotherArray(copyFrom, cfStart, copyFrom.length, copyTo, ctStart, copyTo.length);
    }

    public static <T> int indexOf(T[] arr, T elem) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == elem) {
                    return i;
                }
            }
        }
        return -1;
    }
}
