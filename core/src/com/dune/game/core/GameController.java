package com.dune.game.core;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.FloatArray;

public class GameController {
    private BattleMap map;
    private ProjectilesController projectilesController;
    private Tank tank;
    private int score;
    private TextManager text;

    public TextManager getText() { return text; }

    public Tank getTank() {
        return tank;
    }

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    public BattleMap getMap() {
        return map;
    }

    // Инициализация игровой логики
    public GameController() {
        Assets.getInstance().loadAssets();
        this.map = new BattleMap();
        this.projectilesController = new ProjectilesController(this);
        this.tank = new Tank(this, 200, 200);
        this.score = 0;
        this.text = new TextManager(score);

    }

    public void update(float dt) {

        tank.update(dt);
        spiceCollect();
        projectilesController.update(dt);
        checkCollisions(dt);
        text.update(score);

    }

    public void checkCollisions(float dt) {
    }

    public void spiceCollect() {
        float hypotenuse = 56.57f;  //   корень (40*40+40*40)

        Polygon tankPolygon = new Polygon(new float[]{tank.position.x, tank.position.y, tank.position.x - 40, tank.position.y,
                tank.position.x - 40, tank.position.y - 40, tank.position.x, tank.position.y - 40,
                tank.position.x, tank.position.y});
//        Polygon spicesPoligon = new Polygon(new float[]{0, 0, 16, 0, 16, 16, 0, 16, 0, 0});
        tankPolygon.rotate(tank.getAngle());


        float xMin = tank.position.x - hypotenuse;
        if (xMin < 0) xMin = 0;
        float xMax = tank.position.x + hypotenuse;
        if (xMax > 1200) xMax = 1200;
        float yMin = tank.position.y - hypotenuse;
        if (yMin < 0) yMin = 0;
        float yMax = tank.position.y + hypotenuse;
        if (yMax > 720) yMax = 720;
        int count = 1;
        for (int i = (int) xMin / 16; i < (int) xMax / 16; i++) {
            for (int j = (int) yMin / 16; j < (int) yMax / 16; j++) {
//        for (int i = 0; i < 75; i++) {
//            for (int j = 0; j < 45; j++) {
                Polygon spicesPoligon = new Polygon(new float[]{i * 16, j * 16, i * 16 + 16, j * 16,
                        i * 16 + 16, j * 16 + 16, i * 16, j * 16 + 16, i * 16, j * 16});
                if (map.getSpicesValue(i, j) == 1) {
                    spicesPoligon.setPosition(j * 16, i * 16);
                    printPolygone("Tank", tankPolygon);
                    printPolygone("Spice", spicesPoligon);
                    System.out.println("Spice detected" + count++ + "  " + i * 16 + ", " + j * 16);

//                    float[] tank = tankPolygon.getVertices();
//                    System.out.println(tank.toString());

                    if (Intersector.intersectPolygons(new FloatArray(tankPolygon.getVertices()),
                            new FloatArray(spicesPoligon.getVertices()))) {
                        System.out.println(Intersector.intersectPolygons(new FloatArray(tankPolygon.getVertices()),
                                new FloatArray(spicesPoligon.getVertices())));
                        map.setSpicesValue(i, j, 0);
                        System.out.println("Spice colleted");
                        score++;
                    }
                }

            }
        }
//        for (int i = 0; i < 75; i++) {
//            for (int j = 0; j < 45; j++) {
//                if (map.getSpicesValue(i,j) == 1) {
//                if (tank.position.x -40 <= i *16 &&  i *16 <= tank.position.x+40
//                        && tank.position.y - 40 <= j *16 && j * 16 <= tank.position.y + 40 ) {
//                    map.setSpicesValue(i, j,0);
//                }
//                }
//            }
//        }

    }

    public void printPolygone(String text, Polygon polygon) {
        System.out.print(text + " polygon");
        float[] ver = polygon.getVertices();
        for (int i = 0; i < ver.length; i++) {
            System.out.print(ver[i] + "  ");
        }
        System.out.println();
    }
}
