package com.codeforall.simplegraphics.game;

import com.codeforall.simplegraphics.graphics.Canvas;
import com.codeforall.simplegraphics.keyboard.Keyboard;
import com.codeforall.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.simplegraphics.keyboard.KeyboardHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Main game controller that implements the game loop, handles keyboard input,
 * manages spawning of enemies and pickups, and checks collisions.
 * Implements {@link KeyboardHandler} to receive WASD/arrow key events.
 */
public class Game implements KeyboardHandler {

    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;
    private static final int FRAME_DELAY = 33;

    private static final int ENEMY_SPAWN_INTERVAL = 60;
    private static final int PICKUP_SPAWN_INTERVAL = 300;
    private static final int WAVE_DURATION = 600;
    private static final int PROJECTILE_DIRECTIONS = 8;

    private Player player;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;
    private List<Pickup> pickups;
    private HUD hud;
    private Keyboard keyboard;
    private boolean running;
    private int score;
    private int wave;
    private int waveTimer;
    private int enemySpawnTimer;
    private int pickupSpawnTimer;
    private Random random;

    /**
     * Constructs a new Game instance and initializes collections.
     */
    public Game() {
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        pickups = new ArrayList<>();
        hud = new HUD();
        random = new Random();
        score = 0;
        wave = 1;
        waveTimer = 0;
        enemySpawnTimer = 0;
        pickupSpawnTimer = 0;
    }

    /**
     * Initializes the canvas, player, HUD, and keyboard, then starts the game loop.
     */
    public void start() {
        Canvas.setMaxX(CANVAS_WIDTH);
        Canvas.setMaxY(CANVAS_HEIGHT);

        player = new Player(
                CANVAS_WIDTH / 2.0 - 15,
                CANVAS_HEIGHT / 2.0 - 15,
                CANVAS_WIDTH,
                CANVAS_HEIGHT
        );

        hud.init(player.getHealth(), player.getMaxHealth());

        initKeyboard();

        running = true;
        gameLoop();
    }

    /**
     * Registers keyboard event listeners for WASD and arrow keys
     * (both pressed and released).
     */
    private void initKeyboard() {
        keyboard = new Keyboard(this);

        int[] keys = {
                KeyboardEvent.KEY_W, KeyboardEvent.KEY_A,
                KeyboardEvent.KEY_S, KeyboardEvent.KEY_D,
                KeyboardEvent.KEY_UP, KeyboardEvent.KEY_LEFT,
                KeyboardEvent.KEY_DOWN, KeyboardEvent.KEY_RIGHT
        };

        for (int key : keys) {
            KeyboardEvent pressed = new KeyboardEvent();
            pressed.setKey(key);
            pressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(pressed);

            KeyboardEvent released = new KeyboardEvent();
            released.setKey(key);
            released.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
            keyboard.addEventListener(released);
        }
    }

    /**
     * The main game loop. Runs at approximately 30 FPS using Thread.sleep().
     */
    private void gameLoop() {
        while (running) {
            update();

            try {
                Thread.sleep(FRAME_DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    /**
     * Performs one frame of game logic: updates entities, spawns, collisions,
     * cleanup, and HUD refresh.
     */
    private void update() {
        player.update();

        // Auto-attack: fire projectiles in 8 directions
        if (player.canAttack()) {
            fireProjectiles();
        }

        spawnEnemies();
        spawnPickups();

        for (Enemy enemy : enemies) {
            enemy.update();
        }

        for (Projectile projectile : projectiles) {
            projectile.update();
        }

        checkCollisions();
        removeDeadEntities();

        // Wave progression
        waveTimer++;
        if (waveTimer >= WAVE_DURATION) {
            waveTimer = 0;
            wave++;
            hud.updateWave(wave);
        }

        hud.updateHealth(player.getHealth(), player.getMaxHealth());
        hud.updateScore(score);

        if (!player.isAlive()) {
            gameOver();
        }
    }

    /**
     * Fires projectiles in 8 directions from the player center (Vampire Survivors style).
     */
    private void fireProjectiles() {
        double cx = player.getCenterX();
        double cy = player.getCenterY();

        for (int i = 0; i < PROJECTILE_DIRECTIONS; i++) {
            double angle = 2 * Math.PI * i / PROJECTILE_DIRECTIONS;
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);

            Projectile p = new Projectile(cx - 3, cy - 3, dx, dy,
                    player.getDamage(), CANVAS_WIDTH, CANVAS_HEIGHT);
            projectiles.add(p);
        }
    }

    /**
     * Spawns enemies at the edges of the canvas at regular intervals.
     * The number and type of enemies scales with the current wave.
     */
    private void spawnEnemies() {
        enemySpawnTimer++;
        int spawnRate = Math.max(ENEMY_SPAWN_INTERVAL - wave * 3, 15);

        if (enemySpawnTimer < spawnRate) {
            return;
        }

        enemySpawnTimer = 0;
        int enemiesToSpawn = 1 + wave / 3;

        for (int i = 0; i < enemiesToSpawn; i++) {
            EnemyType type = chooseEnemyType();
            double[] pos = randomEdgePosition();

            Enemy enemy = new Enemy(pos[0], pos[1], type, player);
            enemies.add(enemy);
        }
    }

    /**
     * Chooses an enemy type based on the current wave, with higher waves
     * having chances for stronger enemy types.
     *
     * @return the selected enemy type
     */
    private EnemyType chooseEnemyType() {
        int roll = random.nextInt(100);

        if (wave >= 5 && roll < 5) {
            return EnemyType.BOSS;
        } else if (wave >= 3 && roll < 20) {
            return EnemyType.TANK;
        } else if (wave >= 2 && roll < 45) {
            return EnemyType.FAST;
        }

        return EnemyType.NORMAL;
    }

    /**
     * Generates a random position along one of the four canvas edges.
     *
     * @return a double array with [x, y]
     */
    private double[] randomEdgePosition() {
        int edge = random.nextInt(4);
        double x;
        double y;

        switch (edge) {
            case 0: // top
                x = random.nextInt(CANVAS_WIDTH);
                y = -25;
                break;
            case 1: // bottom
                x = random.nextInt(CANVAS_WIDTH);
                y = CANVAS_HEIGHT + 5;
                break;
            case 2: // left
                x = -25;
                y = random.nextInt(CANVAS_HEIGHT);
                break;
            default: // right
                x = CANVAS_WIDTH + 5;
                y = random.nextInt(CANVAS_HEIGHT);
                break;
        }

        return new double[]{x, y};
    }

    /**
     * Spawns pickups at random positions on the canvas at regular intervals.
     */
    private void spawnPickups() {
        pickupSpawnTimer++;

        if (pickupSpawnTimer < PICKUP_SPAWN_INTERVAL) {
            return;
        }

        pickupSpawnTimer = 0;

        PickupType[] types = PickupType.values();
        PickupType type = types[random.nextInt(types.length)];

        double px = 50 + random.nextInt(CANVAS_WIDTH - 100);
        double py = 100 + random.nextInt(CANVAS_HEIGHT - 150);

        Pickup pickup = new Pickup(px, py, type);
        pickups.add(pickup);
    }

    /**
     * Checks collisions between projectiles and enemies, enemies and player,
     * and player and pickups.
     */
    private void checkCollisions() {
        // Projectiles vs Enemies
        for (Projectile projectile : projectiles) {
            if (!projectile.isAlive()) {
                continue;
            }

            for (Enemy enemy : enemies) {
                if (!enemy.isAlive()) {
                    continue;
                }

                if (projectile.collidesWith(enemy)) {
                    enemy.takeDamage(projectile.getDamage());
                    projectile.destroy();

                    if (!enemy.isAlive()) {
                        score += getEnemyScore(enemy.getEnemyType());
                    }
                    break;
                }
            }
        }

        // Enemies vs Player
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                continue;
            }

            if (enemy.collidesWith(player)) {
                player.takeDamage(enemy.getDamage());
                enemy.destroy();
            }
        }

        // Player vs Pickups
        Iterator<Pickup> pickupIterator = pickups.iterator();
        while (pickupIterator.hasNext()) {
            Pickup pickup = pickupIterator.next();

            if (!pickup.isAlive()) {
                continue;
            }

            if (playerCollidesWithPickup(pickup)) {
                pickup.apply(player);
                pickup.destroy();
            }
        }
    }

    /**
     * Checks if the player's bounding box overlaps with a pickup's bounding box.
     *
     * @param pickup the pickup to check
     * @return true if colliding
     */
    private boolean playerCollidesWithPickup(Pickup pickup) {
        return player.getX() < pickup.getX() + pickup.getWidth()
                && player.getX() + player.getWidth() > pickup.getX()
                && player.getY() < pickup.getY() + pickup.getHeight()
                && player.getY() + player.getHeight() > pickup.getY();
    }

    /**
     * Gets the score value for killing an enemy of the given type.
     *
     * @param type the enemy type
     * @return the score value
     */
    private int getEnemyScore(EnemyType type) {
        switch (type) {
            case FAST:
                return 15;
            case TANK:
                return 30;
            case BOSS:
                return 100;
            default:
                return 10;
        }
    }

    /**
     * Removes all dead enemies, projectiles, and pickups from their lists.
     */
    private void removeDeadEntities() {
        enemies.removeIf(e -> !e.isAlive());
        projectiles.removeIf(p -> !p.isAlive());
        pickups.removeIf(p -> !p.isAlive());
    }

    /**
     * Stops the game loop and shows the game over screen.
     */
    private void gameOver() {
        running = false;
        hud.showGameOver(CANVAS_WIDTH, CANVAS_HEIGHT, score);
    }

    /**
     * Handles key press events for movement (WASD and arrow keys).
     *
     * @param e the keyboard event
     */
    @Override
    public void keyPressed(KeyboardEvent e) {
        switch (e.getKey()) {
            case KeyboardEvent.KEY_W:
            case KeyboardEvent.KEY_UP:
                player.setMoving("up", true);
                break;
            case KeyboardEvent.KEY_S:
            case KeyboardEvent.KEY_DOWN:
                player.setMoving("down", true);
                break;
            case KeyboardEvent.KEY_A:
            case KeyboardEvent.KEY_LEFT:
                player.setMoving("left", true);
                break;
            case KeyboardEvent.KEY_D:
            case KeyboardEvent.KEY_RIGHT:
                player.setMoving("right", true);
                break;
        }
    }

    /**
     * Handles key release events for movement (WASD and arrow keys).
     *
     * @param e the keyboard event
     */
    @Override
    public void keyReleased(KeyboardEvent e) {
        switch (e.getKey()) {
            case KeyboardEvent.KEY_W:
            case KeyboardEvent.KEY_UP:
                player.setMoving("up", false);
                break;
            case KeyboardEvent.KEY_S:
            case KeyboardEvent.KEY_DOWN:
                player.setMoving("down", false);
                break;
            case KeyboardEvent.KEY_A:
            case KeyboardEvent.KEY_LEFT:
                player.setMoving("left", false);
                break;
            case KeyboardEvent.KEY_D:
            case KeyboardEvent.KEY_RIGHT:
                player.setMoving("right", false);
                break;
        }
    }

    /**
     * Entry point for the game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
