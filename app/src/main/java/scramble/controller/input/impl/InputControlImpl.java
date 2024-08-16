package scramble.controller.input.impl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

import scramble.controller.input.api.InputControl;
import scramble.controller.repaints.RepaintManager;
import scramble.model.command.impl.SpaceShipCommand;
import scramble.model.bullets.BulletType;
import scramble.model.command.impl.BulletCommand;

import scramble.view.compact.GameView;

/**
 * Implementation of InputControl. Extends KeyAdapter in order to get the
 * necessary key bindings.
 */
public class InputControlImpl extends KeyAdapter implements InputControl {

    private static final int MINUS = -10;
    private static final int PLUS = 10;
    private static final int SEC = 30;
    

    private final GameView gv;
    private final Timer timer;
    private static boolean explPause;

    private final RepaintManager rm;

    /**
     * Class constructor.
     *
     * @param gv the game view to control
     */
    public InputControlImpl(final GameView gv) {
        this.gv = new GameView(gv);
        this.rm = new RepaintManager(gv);

        timer = new Timer(SEC, e -> {this.rm.repaintManagement(); this.gv.getSpaceshipPanel().moveBullets();});
    }

    /** {@inheritDoc} */
    @Override
    public void keyPressed(final KeyEvent e) {
        if (!gv.getSpaceshipPanel().getSpaceship().isHit()) {
            final int key = e.getKeyCode();
            gv.getLandscapePanel().canNotBeRepaint();
            switch (key) {
                case KeyEvent.VK_UP ->
                    gv.getSpaceshipPanel().sendCommand(new SpaceShipCommand(gv.getSpaceshipPanel(), 0, MINUS));
                case KeyEvent.VK_DOWN ->
                    gv.getSpaceshipPanel().sendCommand(new SpaceShipCommand(gv.getSpaceshipPanel(), 0, PLUS));
                case KeyEvent.VK_LEFT ->
                    gv.getSpaceshipPanel().sendCommand(new SpaceShipCommand(gv.getSpaceshipPanel(), MINUS, 0));
                case KeyEvent.VK_RIGHT ->
                    gv.getSpaceshipPanel().sendCommand(new SpaceShipCommand(gv.getSpaceshipPanel(), PLUS, 0));
                case KeyEvent.VK_SPACE -> gv.getSpaceshipPanel()
                                            .sendCommandBullet(new BulletCommand(gv.getSpaceshipPanel(), BulletType.TYPE_HORIZONTAL));
                case KeyEvent.VK_1 -> gv.getSpaceshipPanel()
                                            .sendCommandBullet(new BulletCommand(gv.getSpaceshipPanel(), BulletType.TYPE_BOMB));
                case KeyEvent.VK_ENTER -> {
                    gv.getLandscapePanel().canBeRepaint();
                    timer.start();
                    gv.startGame();
                }
                default -> {
                    gv.getLandscapePanel().canBeRepaint();
                    break;
                }
            }
        }
    }

    @Override
    public void keyMapping() {
        // TODO Auto-generated method stub

    }

    /**
     * Setter for the boolean describing pause state.
     *
     * @param bool the new value
     */
    public static void setPaused(final boolean bool) {
        explPause = bool;
    }

    /**
     * Getter for explosion pause state.
     *
     * @return true if paused
     */
    public static boolean isExplPause() {
        return explPause;
    }

}
