package com.codeforall.simplegraphics.game;

/**
 * Utility class for axis-aligned bounding box (AABB) collision detection
 * between game objects.
 */
public class CollisionDetector {

    private CollisionDetector() {
    }

    /**
     * Checks whether two game objects overlap using AABB collision detection.
     *
     * @param a the first game object
     * @param b the second game object
     * @return true if the bounding boxes of a and b overlap
     */
    public static boolean checkCollision(GameObject a, GameObject b) {
        return a.getX() < b.getX() + b.getWidth()
                && a.getX() + a.getWidth() > b.getX()
                && a.getY() < b.getY() + b.getHeight()
                && a.getY() + a.getHeight() > b.getY();
    }
}
