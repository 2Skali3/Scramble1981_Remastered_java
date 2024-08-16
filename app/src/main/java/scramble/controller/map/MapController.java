package scramble.controller.map;

import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;

import scramble.model.common.impl.PairImpl;
import scramble.model.map.MapStageFactoryImpl;
import scramble.model.map.api.MapStageFactory;
import scramble.model.map.api.MapColumn;
import scramble.model.map.api.MapStage;
import scramble.model.map.impl.MapColumnImpl;
import scramble.model.map.impl.MapElement;
import scramble.model.map.utils.LandscapeUtils;
import scramble.view.compact.GameView;
import scramble.view.compact.LandscapePanel;

/**
 * The {@code MapController} class is responsible for the preparation of data
 * related to {@link MapStage} model
 * for presentation in the view layer.
 * 
 * <p>
 * This controller collects and organizes data from the {@link MapStageFactory},
 * making sure it is ready for display.
 * </p>
 * 
 * @see MapStageFactory
 */
public class MapController {
    private static final MapStageFactory STAGE_FACTORY = new MapStageFactoryImpl();
    private static List<Integer> stageStartingX = new ArrayList<>();

    private final List<MapColumn> columns;
    private int columnIndex;

    /**
     * Controller for the class {@link MapController}.
     */
    public MapController() {
        final List<MapStage> stages = this.fillMapStage();
        this.columnIndex = 0;
        this.columns = new ArrayList<>();
        this.fillColumns(stages);
    }

    private List<MapStage> fillMapStage() {
        final List<MapStage> stages = new ArrayList<>();
        stages.add(STAGE_FACTORY.prestage());
        stages.add(STAGE_FACTORY.stage1());
        return stages;
    }

    private void fillColumns(final List<MapStage> stages) {
        int x = 0;
        for (final MapStage mapStage : stages) {
            stageStartingX.add(x);
            for (int i = 0; i < mapStage.size(); i++) {
                final MapElement ceilingColumn = mapStage.getCloumnCeiling(i);
                final MapElement floorColumn = mapStage.getCloumnFloor(i);
                ceilingColumn.updatePosition(
                        new PairImpl<Integer, Integer>(x * ceilingColumn.getWidth(), ceilingColumn.getY()));
                floorColumn.updatePosition(
                        new PairImpl<Integer, Integer>(x * floorColumn.getWidth(), floorColumn.getY()));

                this.columns.add(new MapColumnImpl(new ArrayList<>(Arrays.asList(ceilingColumn)),
                        new ArrayList<>(Arrays.asList(floorColumn)), ceilingColumn.getY(), floorColumn.getY()));
                x++;
            }
        }
    }

    /**
     * Return the number of columns in this controller.
     * 
     * @return the number of columns in this controller
     */
    public int getMapSize() {
        return this.columns.size();
    }

    // to-do: creazione classe Column
    /**
     * Return the columns that have to be displayed. For optimization purpose, only
     * a fiew columns will be returned.
     * <p>
     * The number of returned columns is defined by
     * {@link LandscapePanel#TOTAL_COLUMNS_LOADED}.
     * </p>
     * 
     * @return a list of columns
     */
    public List<MapColumn> getColumnsToDisplay() {
        final List<MapColumn> columnsToDisplay = new ArrayList<>();
        for (int i = 0; i < LandscapePanel.TOTAL_COLUMNS_LOADED; i++) {
            columnsToDisplay.add(columns.get(this.columnIndex + i));
        }
        this.columnIndex += LandscapePanel.EXTRA_COLUMNS_LOADED;
        if (this.columnIndex + (GameView.WINDOW_WIDTH / LandscapeUtils.NUMBER_OF_PX_IN_MAP_PER_SPRITE)
                + 4 > this.columns
                        .size()) {
            this.columnIndex = 0;
        }
        return columnsToDisplay;
    }

    /**
     * Reset the Landscape to the indicated x position.
     * 
     * @param x position to reset the Landscape
     */
    public void resetToX(final int x) {
        this.columnIndex = x;
    }

    /**
     * Getter for the list of the starting x position of
     * each stage.
     * 
     * @return the list with all the starter x position of each stage
     */
    public static List<Integer> getStageStartingX() {
        return new ArrayList<>(stageStartingX);
    }
}