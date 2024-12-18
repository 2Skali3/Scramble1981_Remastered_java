package scramble.model.bullets;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import scramble.model.common.impl.GameElementImpl;
import scramble.model.common.impl.PairImpl;
import scramble.model.map.impl.MapElement;
import scramble.utility.Constants;

/**
 * This class handles the bullet model in the game. It is an implementation
 * of the game element that is controlled by the player.
 */
public class Bullet extends GameElementImpl {

    private static final Logger LOG = Logger.getLogger(Bullet.class.getName());

    private final List<BufferedImage> sprites;
    private final List<BufferedImage> sprite;
    private final List<BufferedImage> explosionSprites;
    private final BulletType type;
    private final Random random = new Random();

    private boolean animationComplete;
    private int currentSpriteIndex;
    private boolean hit;
    private int xSpeed;
    private int ySpeed;

    /**
     * Class construnctor.
     *
     * @param x    starting position on the X axis
     * @param y    starting position on the Y axis
     * @param type the type of the bullet
     */
    public Bullet(final int x, final int y, final BulletType type) {
        super(x, y, Constants.BULLETS_SIZE_MAP.get(type).getFirstElement(),
                Constants.BULLETS_SIZE_MAP.get(type).getSecondElement());
        this.sprites = new ArrayList<>();
        this.sprite = new ArrayList<>();
        this.explosionSprites = new ArrayList<>();
        this.type = type;

        switch (type) {
            case TYPE_HORIZONTAL:
                try {
                    // Safe way to get resource
                    sprite.add(ImageIO.read(Bullet.class.getResource("/bullets/bullet" + ".png")));
                } catch (IOException e) {
                    LOG.severe("Ops!");
                    LOG.severe(e.toString());
                }
                break;

            case TYPE_BOMB:
                for (int i = 1; i <= Constants.SPRITE_NUMBER_BOMB; i++) {
                    try {
                        sprites.add(ImageIO.read(Bullet.class.getResource("/bomb/bomb" + i + ".png")));
                    } catch (IOException e) {
                        LOG.severe("Ops!");
                        LOG.severe(e.toString());
                    }
                }

                for (int i = 1; i <= Constants.SPRITE_NUMBER_BOMB_EXPLOSION; i++) {
                    try {
                        explosionSprites
                                .add(ImageIO
                                        .read(Bullet.class.getResource("/bomb/explosion/bomb_explodes" + i + ".png")));
                    } catch (IOException e) {
                        LOG.severe("Ops!");
                        LOG.severe(e.toString());
                    }
                }

                break;

            default:
                break;
        }
        this.hit = false;

    }

    /**
     * Handles the bullet's movement for different type of bullets.
     *
     */
    public void moveByType() {
        switch (type) {
            case TYPE_HORIZONTAL -> {
                ySpeed = 0;
                xSpeed = Constants.XSPEED_HORIZONTAL_BULLET;
                move();
                break;
            }
            case TYPE_BOMB -> {
                ySpeed = Constants.YSPEED_BOMB;
                xSpeed = Constants.XSPEED_BOMB;
                move();
                break;
            }
            default -> {

                break;
            }
        }
    }

    /**
     * Controls the movement of the bomb explosion.
     * 
     * @param explosionSpeedX
     */
    public void moveExplosion(final int explosionSpeedX) {
        ySpeed = 0;
        xSpeed = explosionSpeedX;
        move();
    }

    /** {@inheritDoc} */
    // Per rimuovere il suppressWarning ho dovuto creare una lista da 1 elemento e
    // tornarlo :)
    @Override
    public BufferedImage getSprite() {
        switch (type) {
            case TYPE_HORIZONTAL:
                return sprite.get(0);

            case TYPE_BOMB:
                return getNextBombSprite();

            default:
                // Throwing an exception for an unknown BulletType
                throw new IllegalArgumentException("Unknown BulletType: " + type);
        }
    }

    /**
     * Check if the bullet is colliding with the map.
     *
     * @param map the map
     * @return true it has touched the map
     */
    public boolean checkGroundCollision(final List<MapElement> map) {
        for (final MapElement me : map) {
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
        final int num = random.nextInt(Constants.SPRITE_NUMBER_BOMB_EXPLOSION);
        return explosionSprites.get(num);
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
     * Setter for hit, the boolean recording collision status.
     *
     * @param hit the new value
     */
    public void setHit(final boolean hit) {
        this.hit = hit;
    }

    /**
     * Getter for the type of bullet.
     * 
     * @return type
     **/
    public BulletType getType() {
        return this.type;
    }

    private void move() {
        updatePosition(new PairImpl<>(getPosition().getFirstElement() + xSpeed,
                getPosition().getSecondElement() + ySpeed));
    }

    private BufferedImage getNextBombSprite() {
        if (animationComplete) {
            return sprites.get(Constants.SPRITE_NUMBER_BOMB - 1); // Return the last sprite when the animation is
                                                                  // complete
        }

        final BufferedImage currentSprite = sprites.get(currentSpriteIndex);

        // Update the current sprite index
        if (currentSpriteIndex < Constants.SPRITE_NUMBER_BOMB - 1) {
            currentSpriteIndex = currentSpriteIndex + 1;
        } else {
            animationComplete = true;
        }

        return currentSprite;
    }

}
