package scramble.model.spaceship;

import scramble.model.common.impl.GameElementImpl;
import scramble.model.common.impl.PairImpl;
import scramble.model.enemy.Rocket;
import scramble.model.map.impl.MapElement;
import scramble.utility.Constants;

import java.io.IOException;
import java.util.logging.Logger;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class handles the spaceship model in the game. It is an implementation
 * of the game element that is controlled by the player.
 * 
 * Pattern Decorator
 */
public final class SpaceShip extends GameElementImpl implements Cloneable {

    private static final Logger LOG = Logger.getLogger(SpaceShip.class.getName());
    private static final int ANGLE = 45;

    private final List<BufferedImage> sprites;
    private final List<BufferedImage> explosionSprites;
    private final Random random;

    private boolean hit;
    private boolean left, up, right, down;
    private int xSpeed, ySpeed;

    /**
     * Class construnctor.
     *
     * @param startX starting position on the X axis
     * @param startY starting position on the Y axis
     * @param width  the width of the spaceship
     * @param height the height of the spaceship
     */
    public SpaceShip(final int startX, final int startY, final int width, final int height) {
        super(startX, startY, width, height);
        this.sprites = new ArrayList<>();
        for (int i = 1; i <= Constants.SPRITE_SPACESHIP; i++) {
            try {
                sprites.add(ImageIO.read(getClass().getResource("/ship/ship" + i + ".png")));
            } catch (IOException e) {
                LOG.severe("Ops!");
                LOG.severe(e.toString());
            }
        }
        this.explosionSprites = new ArrayList<>();
        for (int i = 1; i <= Constants.SPRITE_SPACESHIP_EXPLOSION; i++) {
            try {
                explosionSprites
                        .add(ImageIO.read(getClass().getResource("/ship/explosion/ship_explosion_frame" + i + ".png")));
            } catch (IOException e) {
                LOG.severe("Ops!");
                LOG.severe(e.toString());
            }
        }
        random = new Random();
        this.hit = false;

    }

    /**
     * Handles the spaceship's movement.
     * It does so through booleans for a better control
     * of the spaceship.
     */
    public void move() {
        if (!left && !right && !up && !down) {
            return;
        }

        resetSpeedX();
        resetSpeedY();

        if (left && !right) {
            xSpeed = -Constants.SPACESHIP_SPEED;
        } else if (right && !left) {
            xSpeed = Constants.SPACESHIP_SPEED;
        }

        if (up && !down) {
            ySpeed = -Constants.SPACESHIP_SPEED;
        } else if (down && !up) {
            ySpeed = Constants.SPACESHIP_SPEED;
        }

        if ((left || right) && (up || down)) {
            xSpeed *= Math.cos(ANGLE);
            ySpeed *= Math.cos(ANGLE);
        }

        updatePosition(new PairImpl<>(getPosition().getFirstElement() + xSpeed,
                getPosition().getSecondElement() + ySpeed));
    }

    /** {@inheritDoc} */
    @Override
    public BufferedImage getSprite() {
        final int num = random.nextInt(Constants.SPRITE_SPACESHIP);
        return sprites.get(num);
    }

    /**
     * Check if the spaceship is colliding with the map.
     *
     * @param map the map
     * @return true it has touched the map
     */
    public boolean checkGroundCollision(final List<MapElement> map) {
        for (final MapElement me : map) {
            // Comment below to disable hitbox
            if (hasCollided(me)) {
                hit = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns randomised image for explosion animation.
     *
     * @return a sprite
     */
    public BufferedImage getExpSprite() {
        final int num = random.nextInt(Constants.SPRITE_SPACESHIP_EXPLOSION);
        // hit = false;
        return explosionSprites.get(num);
    }

    /**
     * Getter for hit.
     *
     * @return hit
     */
    public boolean isHit() {
        return this.hit;
    }

    /**
     * Setter for hit, the boolean recording collision status.
     *
     * @param hit the new value
     */
    public void setHit(final boolean hit) {
        this.hit = hit;
    }

    @Override
    public SpaceShip clone() throws CloneNotSupportedException {
        return (SpaceShip) super.clone();
    }

    /**
     * Getter for X speed.
     *
     * @return speed
     */
    public int getxSpeed() {
        return xSpeed;
    }

    /**
     * Getter for Y speed.
     *
     * @return speed
     */
    public int getySpeed() {
        return ySpeed;
    }

    /** Resets x speed. */
    public void resetSpeedX() {
        this.xSpeed = 0;
    }

    /** Resets y speed. */
    public void resetSpeedY() {
        this.ySpeed = 0;
    }

    /**
     * Setter for left.
     *
     * @param left the new value
     */
    public void setLeft(final boolean left) {
        this.left = left;
    }

    /**
     * Setter for up.
     *
     * @param up the new value
     */
    public void setAbove(final boolean up) {
        this.up = up;
    }

    /**
     * Setter for right.
     *
     * @param right the new value
     */
    public void setRight(final boolean right) {
        this.right = right;
    }

    /**
     * Setter for down.
     *
     * @param down the new value
     */
    public void setDown(final boolean down) {
        this.down = down;
    }

    /**
     * Check if its happened a collision between a {@link Rocket} and the
     * {@code SpaceShip}.
     *
     * @param rockets the list of {@link Rocket} thet can cause a collision
     *
     * @return {@code true} if the {@code SpaceShip} has collided, {@code fales}
     *         otherwise
     */
    public boolean checkEnemyCollision(final List<Rocket> rockets) {
        for (final Rocket rocket : rockets) {
            if (rocket.hasCollided(this)) {
                this.hit = true;
                setHit(true);
                return true;
            }

        }
        return false;
    }

}
