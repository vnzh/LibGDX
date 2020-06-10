package com.dune.game.core.bases;

import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.Assets;
import com.dune.game.core.BattleMap;
import com.dune.game.core.units.Owner;
;

public class PlayerBase extends Base {

    public  PlayerBase (BattleMap battleMap) {
        super(battleMap);
        this.position = new Vector2(80, (BattleMap.ROWS_COUNT / 2 - 1) * BattleMap.CELL_SIZE);
        this.textureRegion = Assets.getInstance().getAtlas().findRegion("playerBase");
        this.owner = Owner.PLAYER;
        basesSetup();
    }

    @Override
    public void basesSetup() {

        battleMap.setCellBase(0, BattleMap.ROWS_COUNT - 2);
        battleMap.setCellBase(0, BattleMap.ROWS_COUNT - 1);
        battleMap.setCellBase(1, BattleMap.ROWS_COUNT - 2);
        battleMap.setCellBase(1, BattleMap.ROWS_COUNT - 1);
    }


}
