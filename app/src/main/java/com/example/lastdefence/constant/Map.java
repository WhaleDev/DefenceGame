package com.example.lastdefence.constant;

public class Map {
    public final static float a=35;//正方的边长
    //11行   20列
    /**
     * 0可建塔

     * 1 x++
     * 2 y++
     * 3 x--
     * 4 y--
     * 5 终点
     */
    public final static int[][][] MAP_DATA=
            {
                     /*
                     * 测试关卡（第一关）
                     * 最简直路径，可建塔数为10座
                     * */
                    {

                             {0,0,1,1,1,1,2,0,0,0},                        //1
                             {0,0,4,0,0,0,2,0,0,0},
                             {1,1,4,0,0,0,1,1,1,5},
                             {0,0,0,0,0,0,0,0,0,0},
                             {0,0,0,0,0,0,0,0,0,0},
                    },
                    {
                            {0,0,0,0,0,0,0,0,0,0},                        //2
                            {1,1,2,0,0,0,0,0,0,0},
                            {0,0,2,0,0,0,0,0,0,0},
                            {0,0,1,1,1,1,1,1,1,5},
                            {0,0,0,0,0,0,0,0,0,0},
                    },
                    {
                            {0,2,0,0,0,0,0,0,0,0},                        //3
                            {0,2,0,0,0,0,0,0,0,0},
                            {0,2,0,0,0,1,1,1,1,5},
                            {0,1,1,1,1,4,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                    },
                    {
                            {0,0,0,0,0,0,0,0,0,0},                        //4
                            {1,1,1,2,0,0,0,0,0,0},
                            {4,0,0,2,0,1,1,1,1,5},
                            {4,0,0,1,1,4,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                    },
                    {

                            {0,2,0,0,0,0,0,0,0,0},                        //5
                            {0,2,0,0,0,0,0,0,0,0},
                            {0,2,0,0,0,1,1,1,1,5},
                            {0,2,0,1,1,4,0,0,0,0},
                            {0,1,1,4,0,0,0,0,0,0},
                    },
                    {

                            {1,2,0,0,0,0,0,0,0,0},                        //6
                            {0,2,0,0,0,0,0,0,0,0},
                            {0,1,2,0,0,1,1,1,1,5},
                            {0,0,1,1,1,4,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                    },
                    {
                            {0,2,3,3,0,0,5,3,3,0},                        //7
                            {0,2,0,0,0,0,0,0,4,0},
                            {0,2,0,0,0,0,0,0,4,0},
                            {0,2,0,0,0,0,0,0,4,0},
                            {0,1,1,1,1,1,1,1,4,0},
                    },
                    {
                            {0,0,0,2,0,0,0,0,0,0},                        //8
                            {0,0,0,2,0,0,0,0,0,0},
                            {0,2,3,3,0,1,1,1,5,0},
                            {0,2,0,0,0,4,0,0,0,0},
                            {0,1,1,1,1,4,0,0,0,0},
                    },
                    {
                            {0,2,0,0,0,0,0,0,0,0},                        //9
                            {0,2,0,0,0,0,0,0,0,0},
                            {0,2,0,0,0,1,1,1,1,5},
                            {0,2,0,0,0,4,0,0,0,0},
                            {0,1,1,1,1,4,0,0,0,0},
                    },
                    {
                            {2,0,1,1,2,0,1,1,2,0},                        //10
                            {2,0,4,0,2,0,4,0,2,0},
                            {2,0,4,0,2,0,4,0,2,0},
                            {2,0,4,0,2,0,4,0,2,0},
                            {1,1,4,0,1,1,4,0,5,0},

                    },
 };

    public static final int[][] source={


            {0,2}, {0,1}, {1,0}, {0,3}, {1,0},  //1=====5
            {0,0}, {3,0}, {3,0}, {1,0}, {0,0},  //6=====10


    };//出发点 col,row

    public static final int[][] target={

            {9,2},  {9,3}, {9,2}, {9,2}, {9,2},   //1===5
            {9,2},  {5,0}, {8,2}, {9,2}, {8,4},  //6===10
    };//终点
}
