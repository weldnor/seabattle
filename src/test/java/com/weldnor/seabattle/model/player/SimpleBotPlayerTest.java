package com.weldnor.seabattle.model.player;

import com.weldnor.seabattle.model.MoveType;
import com.weldnor.seabattle.model.map.CellType;
import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.map.MapUtils;
import com.weldnor.seabattle.model.map.Point;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBotPlayerTest {

    private Map ownMap;
    private Map enemyMap;
    private SimpleBotPlayer player;

    @Before
    public void setUp() throws Exception {
        ownMap = MapUtils.loadMapFromResource("/valid_maps/1.txt");
        enemyMap = MapUtils.loadMapFromResource("/valid_maps/2.txt");
        player = new SimpleBotPlayer();
    }

    @Test
    public void MakeMove_SelectShip_ReturnsShipCell() {
        for (int i = 0; i < 100; i++) {
            Point point = player.makeMove(ownMap, enemyMap, MoveType.SelectShip);
            int x = point.getX();
            int y = point.getY();
            assertThat(ownMap.getCell(x, y).getType()).isEqualTo(CellType.Ship);
        }
    }

    @Test
    public void MakeMove_SelectMine_ReturnsMineCell() {
        for (int i = 0; i < 100; i++) {
            Map map = player.makeMap();
            assertThat(MapUtils.isValid(map)).isTrue();
        }
    }
}