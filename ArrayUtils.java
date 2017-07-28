public static Integer[] toObject(int[] array) {
    if (array == null) {
        return null;
    }
    final Integer[] result = new Integer[array.length];
    for (int i = 0; i < array.length; i++) {
        result[i] = new Integer(array[i]);
    }
    return result;
}

public static int[] toPrimitive(Integer[] array, int valueForNull) {
    if (array == null) {
        return null;
    }
    final int[] result = new int[array.length];
    for (int i = 0; i < array.length; i++) {
        Integer b = array[i];
        result[i] = (b == null ? valueForNull : b.intValue());
    }
    return result;
}


public static int indexOf(boolean[] array, boolean valueToFind) {  }

public static boolean contains(double[] array, double valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
}
