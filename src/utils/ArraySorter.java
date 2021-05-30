package utils;

import entities.*;

import java.util.Arrays;
import java.util.Random;

public class ArraySorter {
    public static void qSort(Entity[] entityArray, int length) {
        if (length != 0) {
            mqSort(entityArray, 0, length - 1);
        }
    }

    private static void mqSort(Entity[] entityArray, int low, int high) {
        //TODO: figure out infinite recursion problem
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

    private static final Random RAND = new Random();
    private static final int ITERATIONS = 10;

    public static void main(String[] args) {
        System.out.println("Initiating Tests:");
        System.out.print("Testing ");
        for (int i = 0; i < ITERATIONS; i++) {
            // if (i % (ITERATIONS / 10) == (ITERATIONS / (ITERATIONS / 10))) {
                System.out.print(".");
            // }
            Entity[] entities = new Entity[16];
            int length = RAND.nextInt(17);
            for (int j = 0; j < length; j++) {
                entities[j] = createPiece();
            }
            System.out.println(Arrays.toString(entities));
            System.out.println(length);
            qSort(entities, length);;
            for (int j = 0; j < length - 1; j++) {
                if (entities[j].compareTo(entities[j + 1]) > 0) {
                    System.out.println("Fail at itr + " + i);
                    return;
                }
            }
        }
        System.out.println("All tests passed");
    }

    private static Entity createPiece() {
        int pieceType = RAND.nextInt(5) + 2;
        switch (pieceType) {
            case Entity.QUEEN_RANK:
                return new Queen((byte)0, null);
            case Entity.ROOK_RANK:
                    return new Rook((byte) 0, null);
            case Entity.KNIGHT_RANK:
                return new Knight((byte) 0, null);
            case Entity.BISHOP_RANK:
                return new Bishop((byte) 0, null);
            default:
                return new Pawn((byte) 0, null);
        }
    }
}
