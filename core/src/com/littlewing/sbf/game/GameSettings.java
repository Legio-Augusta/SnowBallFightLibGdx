package com.littlewing.sbf.game;

/*
 * TODO investigate move to Character class separate fighter, enermy, boss etc.
 */
public class GameSettings
{
    public int a; // Total fee repair aftermath
    public int boss_level;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int fighter_hp; // Fighter hp ?
    public String str_k;
    public int l;
    public int caried_missile;
    public int n; // seem missile damage
    public int missile_left; // missile number
    public String str_p;
    public int q;
    public int r; // level or stage, mode of hero fighter/game settings
    public int s;
    public int t; // appear in boss screen
    public String str_u;
    public int v;

    public void initGame1()
    {
        this.fighter_hp = this.g;
        this.missile_left = this.caried_missile;
        this.t = this.r;
    }

    public void readRSSetting(SBFRecordStore recordStore)
    {
        // this.enemy_distance_1 = recordStore.returnByteCalc();
        // this.boss_level = recordStore.returnByteCalc();
        // this.d = recordStore.returnByteCalc();
        // this.e = recordStore.returnByteCalc();
        // this.f = recordStore.returnByteCalc();
    }

    public void loadDataFromReadTxt(ReadText readText)
    {
        readText.processTxt(this.d);
        this.n = readText.int_arr_m[0];
        this.caried_missile = readText.int_arr_m[1]; // missile
        this.caried_missile = (this.caried_missile > 0) ? this.caried_missile : 8; // Read txt as byte temorary not work well, so init 8 missile as default.
        this.q = readText.int_arr_m[2];
        this.str_p = readText.buildString();
    }

    public void readArmPlasmaMissile(ReadText readText)
    {
        readText.readTextFromStream("arm");
        loadConfig3(readText);
        readText.readTextFromStream("plasma");
        loadConfig2(readText);
        readText.readTextFromStream("missile");
        loadDataFromReadTxt(readText);
    }

    public void loseHP(int paramInt)
    {
        this.fighter_hp -= 1; // ( (paramInt - this.h < 1) ? 1 : paramInt - this.h); // nickfarrow
    }

    public void loadConfig2(ReadText readText)
    {
        readText.processTxt(this.e);
        this.s = readText.int_arr_m[0];
        this.r = readText.int_arr_m[1];
        this.v = readText.int_arr_m[2];
        this.str_u = readText.buildString();
    }

    public void loadConfig3(ReadText readText)
    {
        readText.processTxt(this.f);
        this.g = 1000;
        this.h = readText.int_arr_m[0];
        this.i = readText.int_arr_m[1];
        this.l = readText.int_arr_m[2];
        this.str_k = readText.buildString();
    }

    public void initSetting()
    {
        this.a = 0;
        this.c = -1;
        this.boss_level = 0;
        this.d = (this.e = this.f = 1);
    }

    public void calcFromRecord(SBFRecordStore recordStore)
    {
        // recordStore.byteCalculate(this.enemy_distance_1);
        // recordStore.byteCalculate(this.boss_level);
        // recordStore.byteCalculate(this.d);
        // recordStore.byteCalculate(this.e);
        // recordStore.byteCalculate(this.f);
    }
}
