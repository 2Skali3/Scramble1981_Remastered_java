package scramble.model.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import scramble.model.bullets.Bullet;
import scramble.model.common.impl.GameElementImpl;
import scramble.model.common.impl.PairImpl;
import scramble.utility.Constants;

/**
 * Extension of the abstract class {@link GameElementImpl} for the
 * implementation of the {@code FuelTank}.
 */
public class FuelTank extends GameElementImpl {

    private static final Logger LOG = Logger.getLogger(FuelTank.class.getName());
    private static final int SPRITES = 2;
    private static final int EXP_SPRITES = 4;
    private static final int EXPLOSION_DURATION = 15;
    private static final int FUEL_REFILL = 15;

    private final List<BufferedImage> sprites;
    private final List<BufferedImage> explosionSprites;

    private int currentSprite;
    private int currentExpSprite;
    private boolean hit;

    private boolean exploded;

    private int counterForExplosion;

    /**
     * Constructor for the class {@code FuelTank}.
     *
     * @param x      the x position of the {@code FuelTank}
     * @param y      the y position of the {@code FuelTank}
     * @param width  the width of the {@code FuelTank}
     * @param height the height of the {@code FuelTank}
     */
    public FuelTank(final int x, final int y, final int width, final int height) {
        super(x, y, width, height);
        this.sprites = new ArrayList<>();
        this.explosionSprites = new ArrayList<>();
        for (int i = 1; i <= SPRITES; i++) {
            try {
                sprites.add(ImageIO.read(FuelTank.class.getResource("/fueltank/fuel_dump_" + i + ".png")));
            } catch (IOException e) {
                LOG.severe("Ops!");
                LOG.severe(e.toString());
            }
        }
        for (int i = 1; i <= EXP_SPRITES; i++) {
            try {
                explosionSprites
                        .add(ImageIO.read(FuelTank.class.getResource("/fueltank/explosion_frame" + i + "_shader.png")));
            } catch (IOException e) {
                LOG.severe("Ops! couldn't load enemy_rocket_explosion_sprites");
                LOG.severe(e.toString());
            }
        }
        this.currentSprite = 0;
        this.hit = false;
        this.exploded = false;
    }

    /** Method for the movement of the {@code FuelTank}. */
    public void move() {
        updatePosition(new PairImpl<Integer, Integer>(getPosition().getFirstElement() - Constants.LANDSCAPEX_SPEED,
                getPosition().getSecondElement()));
    }

    /** {@inheritDoc} */
    @Override
    public BufferedImage getSprite() {
        currentSprite += 1;
        currentSprite = currentSprite % SPRITES;
        return sprites.get(currentSprite);
    }

    /**
     * Getter for the explosion sprite of the {@code FuelTank}.
     *
     * @return BufferedImage
     */
    public BufferedImage getExplosionSprite() {
        currentExpSprite += 1;
        currentExpSprite = currentExpSprite % EXP_SPRITES;
        return explosionSprites.get(currentExpSprite);
    }

    /**
     * Check if a collision between a {@link Bullet} and a {@code FuelTank} is
     * happened.
     *
     * @param bullets is a set of bullets
     * @return true if a collision is happened, false otherwise
     */
    public boolean checkCollisionBullet(final Set<Bullet> bullets) {
        for (final Bullet b : bullets) {
            if (hasCollided(b)) {
                hit = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for hit.
     *
     * @return hit
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * Setter for hit.
     *
     * @param hit
     */
    public void setHit(final boolean hit) {
        this.hit = hit;
    }

    /**
     * Getter for exploded.
     *
     * @return true if is exploded, false otherwise
     */
    public boolean isExploded() {
        return exploded;
    }

    /**
     * Setter for exploded.
     *
     * @param exploded the new value
     */
    public void setExploded(final boolean exploded) {
        this.exploded = exploded;
    }

    /**
     * Getter for the counter of the explosion.
     *
     * @return the counter of the explosion
     */
    public int getCounterForExplosion() {
        return counterForExplosion;
    }

    /**
     * Method for increment {@code counterForExplosion}.
     *
     * @return the counter of the explosion incremented
     */
    public int incrementCounterForExplosion() {
        return this.counterForExplosion++;
    }

    /**
     * Getter for the duration of the explosion.
     *
     * @return the duration of the explosion
     */
    public static int getExplosionDuration() {
        return EXPLOSION_DURATION;
    }

    /**
     * Getter for the ammount of fuel refilled by the tank.
     *
     * @return the ammount of fuel refilled by the tank
     */
    public static int getFuelRefill() {
        return FUEL_REFILL;
    }
}
