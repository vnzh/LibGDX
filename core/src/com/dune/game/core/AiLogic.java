package com.dune.game.core;

import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.units.AbstractUnit;
import com.dune.game.core.units.UnitType;

import java.util.List;

public class AiLogic {

    private GameController gc;

    public AiLogic(GameController gc) {
        this.gc = gc;
    }


    public void update(float dt) {
        List<AbstractUnit> ai = gc.getUnitsController().getAiUnits();
        for (int i = 0; i < ai.size(); i++) {
            if (ai.get(i).getUnitType() == UnitType.HARVESTER) {
                ai.get(i).setDestination(getNearestSpice(ai.get(i)));
            }
            if (ai.get(i).getUnitType() == UnitType.BATTLE_TANK) {
                selecTarget(ai.get(i));
            }
        }
    }

    public AbstractUnit getNearestPlayer(AbstractUnit aiUnit) {

        List<AbstractUnit> player = gc.getUnitsController().getPlayerUnits();
        float dist = 1200f;
        int index = 0;
        for (int j = 0; j < player.size(); j++) {
            float distTemp = aiUnit.getPosition().dst(player.get(j).getPosition());
            if (distTemp < dist) {
                dist = distTemp;
                index = j;
            }
        }
        return player.get(index);
    }

    //если   разница   между  растоянием    до   ближайшего  врага   и до   врага  с минимальным  здоровьев
// меньше 100  то  атакуем  того,  у  когме  ментше  НР.     иначе   ближайшего
    public void selecTarget(AbstractUnit battleTank) {
        AbstractUnit playerNear = getNearestPlayer(battleTank);
        AbstractUnit playerMinHp = getMinHpPlayer();
        if (playerMinHp == playerNear) {
            battleTank.setTarget(playerMinHp);
            return;
        }
        // подранка    догонят  и  добье,  где  бы он  ни был
        if (1.0 * playerMinHp.getHpMax() / playerMinHp.getHp() > 3) {
            battleTank.setTarget(playerMinHp);
            return;
        }
        if (battleTank.position.dst(playerMinHp.position) <= battleTank.getMinDstToActiveTarget()) {
            battleTank.setTarget(playerMinHp);
            return;
        }

        if ((battleTank.position.dst(playerMinHp.position) - battleTank.position.dst(playerNear.position)) > 100) {
            battleTank.setTarget(playerNear);
            return;
        }

    }

    public AbstractUnit getMinHpPlayer() {
        List<AbstractUnit> player = gc.getUnitsController().getPlayerUnits();
        int hpMin = player.get(0).getHp();
        int index = 0;
        for (int j = 1; j < player.size(); j++) {
            int hp = player.get(j).getHp();
            if (hp < hpMin) {
                hpMin = hp;
                index = j;
            }
        }
        return player.get(index);
    }


    public Vector2 getNearestSpice(AbstractUnit harvester) {
        float dist = 1200f;
        boolean find = false;
        int yIndex = 0;
        int xIndex = 0;
        int range = 200;
        int celsize = gc.getMap().CELL_SIZE;

        //  Чтобы  не проводить  поиск  ближайшего    ресурса по всей    карте,  сперва   проводим   поиск  в области
//       размером  range,    если  в этой  области нет  ресурсов то  увеличиваем   область  поиска  на 200
        while (!find) {

            int y = (int) (harvester.position.y - range / 2);
            y = (y < 0) ? 0 : y;
            int x = (int) (harvester.position.x - range / 2);
            x = (x < 0) ? 0 : x;
            int yMax = ((y + range) >= 720) ? 700 : y + range;
            int xMax = ((x + range) >= 1200) ? 1180 : x + range;

            for (int i = x; i < xMax; i += celsize) {
                for (int j = y; j < yMax; j += celsize) {
                    if (gc.getMap().getResource(i / celsize, j / celsize) != 0) {

                        if (!checkDestinationHarvesters(harvester, i, j)) {
                            continue;
                        }
                        float distTemp = harvester.position.dst(i, j);
                        dist = (distTemp < dist) ? distTemp : dist;

                        //центр  отрисовки    ресурсов
                        yIndex = (j / celsize) * celsize + celsize / 2;
                        xIndex = (i / celsize) * celsize + celsize / 2;
//                        yIndex = j;
//                        xIndex = i;

                        find = true;
                        System.out.println("tru");
                    }
                }
            }
            range = range + 200;
        }
        Vector2 nearestResorse = new Vector2(xIndex, yIndex);
        return nearestResorse;
    }

    //      чтобы   харвестреы  не   ехали   на одну  точку  и не толкались на ней,  проверыяется,
//      есть  ли  назначение  на эту точку  у   других  комбайнов
    public boolean checkDestinationHarvesters(AbstractUnit harvester, float x, float y) {
        for (int i = 0; i < gc.getUnitsController().getAiUnits().size(); i++) {
            if (gc.getUnitsController().getAiUnits().get(i).getUnitType() != UnitType.HARVESTER) {
                continue;
            }
            if (harvester == gc.getUnitsController().getAiUnits().get(i) ||
                    gc.getUnitsController().getAiUnits().get(i).getDestination() == null) {
                continue;
            }
            if (gc.getUnitsController().getAiUnits().get(i).getDestination().dst(x, y) < gc.getMap().CELL_SIZE) {
                return false;
            }

        }
        return true;
    }

}
