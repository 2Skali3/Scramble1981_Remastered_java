package scramble.model.map.impl;

import scramble.model.common.impl.PairImpl;
import scramble.model.map.api.MapColumn;
import scramble.model.map.api.MapStage;
import scramble.model.map.api.MapStageFactory;
import scramble.model.map.util.LandsDataLoader;
import scramble.model.map.util.elaborator.StageGenerator;
import scramble.utility.Constants;

/**
 * Implementation of the interface MapStageFactory.
 * 
 * @see MapStageFactory
 */
public class MapStageFactoryImpl implements MapStageFactory<MapColumn> {
    /**
     * the starter height value of the ceiling.
     */
    public static final int STARTER_CEILING_HEIGHT = -1;
    /**
     * the starter height value of the floor.
     */
    public static final int STARTER_FLOOR_HEIGHT = 35;

    private final StageGenerator mapStageGenerator;

    /**
     * Constructor of the class MapStageFactory.
     */
    public MapStageFactoryImpl() {
        this.mapStageGenerator = new StageGenerator(new PairImpl<Integer, Integer>(
                MapStageFactoryImpl.STARTER_CEILING_HEIGHT, MapStageFactoryImpl.STARTER_FLOOR_HEIGHT));
    }

    /**
     * @inheritDoc
     */
    @Override
    public MapStage<MapColumn> prestage() {
        return mapStageGenerator.convertDataToMapStage(LandsDataLoader.getPrestageData(),
                Constants.SPRITE_PER_PRESTAGE_WIDTH);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MapStage<MapColumn> stage1() {
        return mapStageGenerator.convertDataToMapStage(LandsDataLoader.getStage1Data(),
                Constants.SPRITE_PER_STAGE_WIDTH);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MapStage<MapColumn> stage2() {
        return mapStageGenerator.convertDataToMapStage(LandsDataLoader.getStage2Data(),
                Constants.SPRITE_PER_STAGE_WIDTH);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MapStage<MapColumn> stage3() {
        return mapStageGenerator.convertDataToMapStage(LandsDataLoader.getStage3Data(),
                Constants.SPRITE_PER_STAGE_WIDTH);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MapStage<MapColumn> stage4() {
        return mapStageGenerator.convertDataToMapStage(LandsDataLoader.getStage4Data(),
                Constants.SPRITE_PER_STAGE_WIDTH);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MapStage<MapColumn> stage5() {
        return mapStageGenerator.convertDataToMapStage(LandsDataLoader.getStage5Data(),
                Constants.SPRITE_PER_STAGE_WIDTH);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MapStage<MapColumn> stage6() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stage6'");
    }
}
