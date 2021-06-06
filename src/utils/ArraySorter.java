package utils;

import entities.*;

import java.util.Arrays;
import java.util.Random;

public class ArraySorter {
    public static void qSort(Entity[] entityArray, int length) {
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length ;j++) {
                if  (entityArray[i].compareTo(entityArray[j]) > 0) {
                    Entity temp = entityArray[i];
                    entityArray[i] = entityArray[j];
                    entityArray[j] = temp;
                }
            }
        }
    }


    private static final Random RAND = new Random();
    private static final int ITERATIONS = 10000;

    public static void main(String[] args) {
        System.out.println("Initiating Tests:");
        System.out.print("Testing ");
        for (int i = 0; i < ITERATIONS; i++) {
            if (i % (ITERATIONS / 100) == 0) {
                System.out.print(".");
            }
            Entity[] entities = new Entity[16];
            int length = RAND.nextInt(17);
            for (int j = 0; j < length; j++) {
                entities[j] = createPiece();
            }
            qSort(entities, length);;
            for (int j = 0; j < length - 1; j++) {
                if (entities[j].compareTo(entities[j + 1]) > 0) {
                    System.out.println("Fail at itr + " + i);
                    return;
                }
            }
        }
        System.out.println();
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
