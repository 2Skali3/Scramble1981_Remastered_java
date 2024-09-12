package scramble.enemy;

import scramble.model.enemy.RocketImpl;
import scramble.model.bullets.Bullet;
import scramble.utility.Constants;
import scramble.model.common.impl.PairImpl;
import scramble.model.bullets.BulletType;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/** Test class for {@link RocketImpl} class. */
class RocketImplTest {

    private RocketImpl makeRocketImpl(final int x, final int y, final int width, final int height) {
        return new RocketImpl(x, y, width, height);
    }

    /** Test for correct position update. */
    @Test
    void moveRocketStateAndSpeedUpdatesPosition() {
        final RocketImpl rocket = makeRocketImpl(100, 100, 10, 10);

        // Arrange: setting initial state and invoking the entry point
        rocket.turnOnMove();
        rocket.move();

        // Assert: state-based check
        final PairImpl<Integer, Integer> position = rocket.getPosition();
        assertEquals(100 - Constants.LANDSCAPEX_SPEED, position.getFirstElement());
        assertEquals(100, position.getSecondElement());
    }

    /** Test for correct sprites cycle. */
    @Test
    void checkSpriteCyclesThroughSprites() {
        final RocketImpl rocket = makeRocketImpl(100, 100, 10, 10);

        // Act: invoking the entry point
        final BufferedImage sprite1 = rocket.getSprite();
        final BufferedImage sprite2 = rocket.getSprite();

        // Assert: return-value check
        assertNotEquals(sprite1, sprite2);
    }

    /** Test for collision with bullets. */
    @Test
    void checkCollisionBulletWithCollidingRockets() {
        final RocketImpl rocket = makeRocketImpl(100, 100, 10, 10);
        final Set<Bullet> bullets = new HashSet<>();
        final Bullet bullet = new Bullet(100, 100, BulletType.TYPE_BOMB); // Assuming Bullet has a constructor with
                                                                          // these parameters
        bullets.add(bullet);

        // Act: invoking the entry point
        final boolean result = rocket.checkCollisionBullet(bullets);

        // Assert: return-value check
        assertTrue(result);
    }
}