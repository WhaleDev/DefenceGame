package com.example.lastdefence.game;

import com.example.lastdefence.constant.Map;
import com.example.lastdefence.view.GameView;

public class Game {
    int mapNum;
    transient GameView gp;
    public int[][] map_tower =  new int[Map.MAP_DATA[0].length][Map.MAP_DATA[0][0].length]; //用于增加塔后，怪物寻找路径
    public int[] source = Map.source[mapNum];
    public int[] target = Map.target[mapNum];



    public Game(GameView gp)
    {
        this.gp=gp;
        this.mapNum = gp.mapNum;
        source=Map.source[mapNum];
        target=Map.target[gp.mapNum];//目标点 col,row
        //复制地图，防止放塔之后修改常量类
        for(int i=0;i<Map.MAP_DATA[0].length;i++){
            for(int j=0; j<Map.MAP_DATA[0][0].length;j++){
                map_tower[i][j] = Map.MAP_DATA[mapNum][i][j];
            }
        }
    }

//建塔后进行标志
    public void setMap_Tower(int x, int y) {

        this.map_tower[x][y] = 1;
    }
}
