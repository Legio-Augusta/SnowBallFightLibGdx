package wait4u.littlewing.snowballfight;

import com.badlogic.gdx.Game;
import wait4u.littlewing.snowballfight.screens.Screen1;
import wait4u.littlewing.snowballfight.screens.VictoryScreen;

/**
 * Created by nickfarow on 13/10/2016.
 */
public class SnowBallFight extends Game {

    public void create () {
        setScreen(new Screen1(new SnowBallFight()));
        setScreen(new VictoryScreen(this));
    }

    //    private Image imgLogo;
    //    private Image imgMM;
    //    private Image imgBk;
    //    private Image imgSl;
    //    private Image imgPl;
    //    private Image imgCh;
    //    private Image imgBack;
    //    private Image imgAl;
    //    private Image imgShadow;
    //    private Image imgPok;
    //    private Image imgPPang;
    //    private Image imgPPang1;
    //    private Image imgH_ppang;
    //    private Image imgSnow_g;
    //    private Image imgPwd;
    //    private Image[] imgItem;
    //    private Image[] imgItem_hyo;
    //    private Image imgVill;
    //    private Image imgSchool;
    //    private Image imgShop;
    //    private Image[] imgSpecial;
    //    private Image imgSp;
    //    private Image[] imgEffect;
    //    private Image imgVictory;
    //    private Image imgV;
    //    private Image imgHero_v;
    //    private Image imgLose;
    //    private Image imgHero_l;
    //    private Image imgStage_num;
    //    private Image[] imgStage;

    //    if (speed == 5) {
    //        game_speed = 8;
    //    }
    //        if (speed == 4) {
    //        game_speed = 17;
    //    }
    //        if (speed == 3) {
    //        game_speed = 24;
    //    }
    //        if (speed == 2) {
    //        game_speed = 31;
    //    }
    //        if (speed == 1) {
    //        game_speed = 38;
    //    }
}