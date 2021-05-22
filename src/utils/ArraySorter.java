package utils;

import entities.Entity;

public class ArraySorter {
    public static void qSort(Entity[] entityArray, int length) {
        mqSort(entityArray, 0, length - 1);
    }

    private static void mqSort(Entity[] entityArray, int low, int high) {
        if (low < high) {
            int pi = low;
            for (int i = low; i <= high; i++) {
                if (entityArray[i].compareTo(entityArray[pi]) < 0) {
                    Entity temp = entityArray[i];
                    entityArray[i] = entityArray[pi];
                    entityArray[pi] = temp;
                    pi = i;
                }
                mqSort(entityArray, low, pi - 1);
                mqSort(entityArray, pi + 1, high);
            }
        }
    }
}
