package com.dune.game.core.bases;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.Assets;
import com.dune.game.core.BattleMap;
import com.dune.game.core.units.Owner;

public class AiBase extends Base {

    public AiBase(BattleMap battleMap) {
        super(battleMap);
        this.position = new Vector2((BattleMap.COLUMNS_COUNT - 1) * BattleMap.CELL_SIZE,
                (BattleMap.ROWS_COUNT / 2 - 1) * BattleMap.CELL_SIZE);
        this.textureRegion = Assets.getInstance().getAtlas().findRegion("aiBase");
        this.owner = Owner.AI;
        basesSetup();
    }

    @Override
    public void basesSetup() {
        battleMap.setCellBase(BattleMap.COLUMNS_COUNT - 2, BattleMap.ROWS_COUNT - 2);
        battleMap.setCellBase(BattleMap.COLUMNS_COUNT - 2, BattleMap.ROWS_COUNT - 1);
        battleMap.setCellBase(BattleMap.COLUMNS_COUNT - 1, BattleMap.ROWS_COUNT - 2);
        battleMap.setCellBase(BattleMap.COLUMNS_COUNT - 1, BattleMap.ROWS_COUNT - 1);
    }


}
