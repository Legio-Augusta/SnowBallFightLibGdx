package com.littlewing.sbf.game;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ReadText
{
    public int a = 1; // related to informations
    public int b = 0;
    public boolean bool_c = false;
    public boolean bool_d = false;
    public boolean bool_e = false;
    public boolean bool_f = false;
    public int g = 1;
    public int h = 1;
    public int i;
    public int j;
    public int k;
    // public byte[] byte_arr_l = new byte['?']; // ASCII '?' = 63
    public byte[] byte_arr_l = new byte['?']; // ASCII '?' = 63
    public int[] int_arr_m = new int[9];
    public int n = -1;
    public int o = -1;

    public boolean processTxt(int paramInt)
    {
        int i1 = 0;
        if (this.n != paramInt)
        {
            if (this.bool_c) {
                this.a = this.g;
            } else if (this.bool_d) {
                this.a = this.h;
            } else {
                this.a = 1;
            }
            if (this.bool_e) {
                this.a = 5;
            }
            this.b = 0;
            this.n = paramInt;
        }
        this.o = 0;
        for (int i2 = 0; i2 < 9; i2++) {
            this.int_arr_m[i2] = 0;
        }
        this.k = extractValueFromTxt(paramInt); // 1, 2 => infi loop, 3 10 4 ...
        if (this.k == -1)
        {
            this.k = 0;
            return false;
        }
        // <1>+20000+40000+5 +5 is number of fighter you have to destroy before next target.
        while (bool_ascii_helper('+')) { // boolean processText (String str)
            // Exception on i1 = 8 or 9
            if (i1 <= 8) {
                this.o = (this.int_arr_m[(i1++)] = calcIntCharFromASCII(this.k));
            } else {
                break;
            }
        }
        this.k += 2;
        return true;
    }

    /*
     * Guest: Calculate integer value from byte (ASCII)
     */
    public int calcIntCharFromASCII(int paramInt)
    {
        int i2 = 0;
        int i1 = 0; // dungnv
        for (i1 = paramInt; i1 < this.j; i1++) {
            if ((this.byte_arr_l[i1] >= 48) && (this.byte_arr_l[i1] <= 57)) {
                break;
            }
        }
        while (i1 < this.j)
        {
            if ((this.byte_arr_l[i1] >= 48) && (this.byte_arr_l[i1] > 57)) { // ASCII 0-9
                break;
            }
            i2 *= 10;
            i2 += this.byte_arr_l[i1] - 48; // byte - 48 (x48 = 0, x57 = 9)
            i1++;
        }
        this.k = i1;
        return i2;
    }

    public void readTextFromStream(String paramString) // Load text seem has problem, it not shown on screen
    {
        String str = "assets/text/" + paramString + ".txt";
        String textAsInputStream = getTextAsString(paramString);
        InputStream localInputStream = new ByteArrayInputStream(textAsInputStream.getBytes(StandardCharsets.UTF_8));

        try
        {
            // InputStream localInputStream = getClass().getResourceAsStream(str);
            this.j = localInputStream.read(this.byte_arr_l, 0, this.byte_arr_l.length-1); // Original has no -1, call read(byte_arr)
            if( (this.j < this.byte_arr_l.length) && (this.j >= 0) ) {
                this.byte_arr_l[this.j] = 0;
            }
            localInputStream.close();
        }
        catch (Exception localException) {}
        byte_arr_helper();
        this.k = 0;
    }

    // TODO use conditional or some way to return value based on some flag; read byte seem not easy ?
    public String buildString()
    {
        String str = null;
        int i1 = 0; // dungnv
        for (i1 = this.k; i1 < this.j; i1++)
        {
            if (this.byte_arr_l[i1] == 60)
            {
                this.k = i1;
                return null;
            }
            if (this.byte_arr_l[i1] == 10)
            {
                str = new String(this.byte_arr_l, this.k, i1 - this.k);
                break;
            }
        }
        this.k = (i1 + 1);
        return str;
    }

    /*
     * l[i1] == '<' && screen(i1) == paramInt
     * Calculate integer value inside <?> mark.
     * Return index i1? i1 is not simple index (increment) it may be has value
     */
    public int extractValueFromTxt(int paramInt)
    {
        for (int i1 = 0; i1 < this.j; i1++) {
            if ((this.byte_arr_l[i1] == 60) && (calcIntCharFromASCII(i1) == paramInt)) {
                return i1;
            }
        }
        return -1;
    }

    public void byte_arr_helper()
    {
        this.i = 0;
        for (int i1 = 0; i1 < this.j; i1++) {
            if (this.byte_arr_l[i1] == 10) {
                this.i += 1;
            }
        }
        this.n = -1;
    }
    /**
     * @param paramChar
     * @return
     * eg. + in text "<100>+101 The radar is situated"
     * use : while (processText('+'))
     * byte array this.l; &#43 is Dec value of '+' ASCII
     */
    public boolean bool_ascii_helper(char paramChar)
    {
        int i1 = 0; // dungnv
        for (i1 = this.k; i1 < this.j; i1++) // int i1
        {
            // System.out.println(" e this.l arr : " + i1 + " " + this.l[i1]);
            if (this.byte_arr_l[i1] == 10)
            {
                this.k = (i1 - 1);
                return false;
            }
            // this.l is byte array. this.l[i1] is char ? if not then why compare it with paramChar ?
            // if this.l[i1] is char then why it can assigned/compared to 10 ?
            // May be byte-char conversion here
            // A byte is 8 bits, processText char is 16 bits
            // 60 Dec: '<'
            // 10: 'NL' new line to 90: 'Z' in AZ-1 (ms1.txt)
            if (this.byte_arr_l[i1] == paramChar) {
                break;
            }
        }
        this.k = i1;
        return true;
    }

    /*
     * TODO use read file way
     */
    public String getTextAsString(String paramString) {
        if( paramString.equals(new String("about")) ) {
            return "<1>\n" +
                    "ArchAngel Ver 1.0.0\n" +
                    "\n" +
                    "Published by wait4u\n" +
                    "URL: www.wait4u.net\n" +
                    "E-mail: info@wait4u.net\n" +
                    "\n" +
                    "Developed by WithMobile";
        } else if( paramString.equals(new String("arm")) ) {
            return "<1>\n" +
                    "ArchAngel Ver 1.0.0\n" +
                    "\n" +
                    "Published by wait4u\n" +
                    "URL: www.wait4u.net\n" +
                    "E-mail: info@wait4u.net\n" +
                    "\n" +
                    "Developed by WithMobile\n" +
                    "nickfarrow:text$ cat arm.txt \n" +
                    "<1>+10+1+0\n" +
                    "T2-COMPO\n" +
                    "<2>+20+2+2000\n" +
                    "T-10 COMPO\n" +
                    "<3>+40+3+4000\n" +
                    "EX-T COMPO\n" +
                    "<4>+80+4+8000\n" +
                    "ERA 1000\n" +
                    "<5>+160+5+16000\n" +
                    "ERA 2000\n" +
                    "<6>+320+6+50000\n" +
                    "ERA 3000";
        } else if( paramString.equals(new String("end")) ) {
            return "<11>\n" +
                    "The year 2029...\n" +
                    "the prototype of\n" +
                    "'Arch Angel'\n" +
                    "<12>\n" +
                    "proofed to be the\n" +
                    "key to peace.\n" +
                    "Since then it has\n" +
                    "<13>\n" +
                    "entered mass\n" +
                    "production and 3700\n" +
                    "units are securing\n" +
                    "<14>\n" +
                    "world peace in the\n" +
                    "name of the\n" +
                    "confederated forces.\n";
        } else if( paramString.equals(new String("help0")) ) {
            return "<100>+101\n" +
                    "The radar is\n" +
                    "situated in the\n" +
                    "upper left corner\n" +
                    "of the monitor.\n" +
                    "Enemies are marked \n" +
                    "as yellow points,\n" +
                    "their missiles are\n" +
                    "<101>+102\n" +
                    "marked red.Your \n" +
                    "energy bar is \n" +
                    "displayed on the\n" +
                    "upper right corner. \n" +
                    "If your energy \n" +
                    "drops to zero,\n" +
                    "Arch Angel will be\n" +
                    "<102>+103\n" +
                    "self-destructed.\n" +
                    "Also your\n" +
                    "ammunition bar is\n" +
                    "displayed on the\n" +
                    "upper right corner.\n" +
                    "If this bar is at\n" +
                    "zero,you have to\n" +
                    "<103>+104\n" +
                    "reload your MG.\n" +
                    "The number on the\n" +
                    "top of the monitor\n" +
                    "shows the distance\n" +
                    "to the target area.\n" +
                    "The direction \n" +
                    "marker indicates\n" +
                    "<104>+50\n" +
                    "the direction.\n" +
                    "\n";
        } else if( paramString.equals(new String("help1")) ) {
            return "<200>+201\n" +
                    "Briefing:Here you\n" +
                    "will be briefed \n" +
                    "about the mission\n" +
                    "objective.\n" +
                    "<201>+202\n" +
                    "Start:The mission\n" +
                    "will start with\n" +
                    "this option.\n" +
                    "<202>+203\n" +
                    "Machine shop:You \n" +
                    "can upgrade your\n" +
                    "machine gun,plasma\n" +
                    "cannon and armor\n" +
                    "in the machine\n" +
                    "shop.The machine\n" +
                    "gun is used\n" +
                    "<203>+204\n" +
                    "in shuttle mode\n" +
                    "(air fighter) and\n" +
                    "the plasma cannon\n" +
                    "in robot mode. \n" +
                    "<204>+205\n" +
                    "Specs:Here you\n" +
                    "can check the\n" +
                    "current status of\n" +
                    "Arch Angel.\n" +
                    "<205>+50\n" +
                    "System:Here you \n" +
                    "can save and load\n" +
                    "games,change \n" +
                    "settings and enter\n" +
                    "the help section.\n";
        } else if( paramString.equals(new String("help2")) ) {
            return "<300>+301\n" +
                    "You will receive\n" +
                    "the mission\n" +
                    "objectives in the\n" +
                    "briefing section \n" +
                    "and then leave\n" +
                    "for combat.When\n" +
                    "<301>+302\n" +
                    "successfully\n" +
                    "achieving the\n" +
                    "mission goals,you\n" +
                    "will be rewarded\n" +
                    "with credits with \n" +
                    "which you can \n" +
                    "upgrade Arch Angel\n" +
                    "<302>+50\n" +
                    "at the machine\n" +
                    "shop. There are 8\n" +
                    "missions,which \n" +
                    "have to be\n" +
                    "completed, to\n" +
                    "successfully end \n" +
                    "the war.\n";
        } else if( paramString.equals(new String("helpmain")) ) {
            return "<61>+99+199+299+399+500\n" +
                    "1.Monitor\n" +
                    "2.Menu\n" +
                    "3.Start game\n";
        } else if( paramString.equals(new String("info")) ) {
            return "<5>+10+20+30+999\n" +
                    "1.Missile\n" +
                    "2.Plasma Canon\n" +
                    "3.Armor\n" +
                    "<10>+1\n" +
                    "<20>+1\n" +
                    "<30>+1\n" +
                    "\n" +
                    "\n" +
                    "\n";
        } else if( paramString.equals(new String("open")) ) {
            return "<11>\n" +
                    "The year 2028...\n" +
                    "A conflict between\n" +
                    "the Earth and the\n" +
                    "<12>\n" +
                    "other planets has\n" +
                    "led the worlds to \n" +
                    "the edge of processText final\n" +
                    "<13>\n" +
                    "war which threatens\n" +
                    "man's very survival.\n" +
                    "In processText struggle to end\n" +
                    "<14>\n" +
                    "the conflict, the\n" +
                    "confederation has\n" +
                    "developed processText\n" +
                    "<15>\n" +
                    "revolutionary land-\n" +
                    "sea-air-space unit \n" +
                    "in processText top secret\n" +
                    "<16>\n" +
                    "operation.\n" +
                    "Due to the fact that\n" +
                    "this unit looks like\n" +
                    "<17>\n" +
                    "processText fire-spitting angel\n" +
                    "its code name is \n" +
                    "'Arch Angel'.\n" +
                    "\n";
        } else if( paramString.equals(new String("plasma")) ) {
            return "<1>+40+50+0\n" +
                    "MAG 300KV\n" +
                    "<2>+60+60+2000\n" +
                    "LINIE 360KV\n" +
                    "<3>+100+70+4000\n" +
                    "DUNA 420KV\n" +
                    "<4>+150+80+8000\n" +
                    "MAG 480KV\n" +
                    "<5>+300+90+16000\n" +
                    "LINIE 540KV\n" +
                    "<6>+500+300+50000\n" +
                    "DUNA 600KV";
        } else if( paramString.equals(new String("missile")) ) {
            return "<1>+100+8+0\n" +
                    "IMI 88MM\n" +
                    "<2>+200+10+2000\n" +
                    "CALIBER 106MM\n" +
                    "<3>+400+12+4000\n" +
                    "CALIBER 128MM\n" +
                    "<4>+800+14+8000\n" +
                    "IMI 144MM\n" +
                    "<5>+1500+16+16000\n" +
                    "KX-T 156MM\n" +
                    "<6>+3000+18+50000\n" +
                    "KIBA 188MM\n" +
                    "\n";
        } else if( paramString.equals( new String("ms1") ) ) {
            return "<1>+20000+40000+5\n" +
                    "<2>+100+30+1+200\n" +
                    "AZ-1\n" +
                    "<3>+1200+100+0+1000\n" +
                    "AL 101A\n" +
                    "<4>\n" +
                    "EndingMent\n" +
                    "<10>\n" +
                    "Mission 1\n" +
                    "<11>\n" +
                    "In Dark Eden an area in\n" +
                    "the Nekkar desert, the\n" +
                    "enemy has concentrated \n" +
                    "<12>\n" +
                    "its troops to prepare \n" +
                    "for war. You will face\n" +
                    "processText group of 10 AZ-1\n" +
                    "<13>\n" +
                    "combat units, which you\n" +
                    "need to destroy. Then,\n" +
                    "follow the directions\n" +
                    "<14>\n" +
                    "of your navigator for\n" +
                    "60 km, which will leads\n" +
                    "you directly to their\n" +
                    "<15>\n" +
                    "newly developed AL-101\n" +
                    "air fighter...\n" +
                    "Destroy it!\n";
        } else if( paramString.equals( new String("ms2") ) ) {
            return "<1>+30000+50000+7\n" +
                    "<2>+100+30+1+200\n" +
                    "AZ-1\n" +
                    "<3>+1500+150+0+2000\n" +
                    "AL 101B\n" +
                    "<4>\n" +
                    "EndingMent\n" +
                    "<10>\n" +
                    "Mission 2\n" +
                    "<11>\n" +
                    "The remaining enemy\n" +
                    "troops are hiding\n" +
                    "in the Bellatrix\n" +
                    "<12>\n" +
                    "mountains, north-east\n" +
                    "of Dark Eden.\n" +
                    "Destroy 7 of their \n" +
                    "<13>\n" +
                    "AZ-1 units and then \n" +
                    "follow the navigator\n" +
                    "for 80km and destroy\n" +
                    "<14>\n" +
                    "their upgraded AL-101\n" +
                    "unit!\n" +
                    "Good luck!";
        } else if( paramString.equals( new String("ms3") ) ) {
            return "<1>+40000+60000+9\n" +
                    "<2>+300+40+3+300\n" +
                    "AZ-2\n" +
                    "<3>+2400+200+2+4000\n" +
                    "AL 101EX\n" +
                    "<4>\n" +
                    "EndingMent\n" +
                    "<10>\n" +
                    "Mission 3\n" +
                    "<11>\n" +
                    "On satellite photos\n" +
                    "we have discovered\n" +
                    "an enemy production \n" +
                    "<12>\n" +
                    "plant north-east of\n" +
                    "your position. Destroy\n" +
                    "9 AZ-2 units which \n" +
                    "<13>\n" +
                    "are guarding the plant\n" +
                    "to prepare everything\n" +
                    "for processText massive air \n" +
                    "<14>\n" +
                    "strike. Then follow \n" +
                    "the navigator for \n" +
                    "100km and destroy \n" +
                    "<15>\n" +
                    "the AL-101 master\n" +
                    "unit!\n";
        } else if( paramString.equals( new String("ms4") ) ) {
            return "<1>+50000+70000+11\n" +
                    "<2>+600+60+4+500\n" +
                    "N-AZ\n" +
                    "<3>+3000+250+3+8000\n" +
                    "BL 102\n" +
                    "<4>\n" +
                    "EndingMent\n" +
                    "<10>\n" +
                    "Mission 4\n" +
                    "<11>\n" +
                    "The enemy is\n" +
                    "concentrating its\n" +
                    "troops around Castor \n" +
                    "<12>\n" +
                    "in Pollux. Destroy \n" +
                    "11 of their N-AZ units\n" +
                    "and then follow the\n" +
                    "<13>\n" +
                    "navigator for 120km to\n" +
                    "the Arcturus mountains \n" +
                    "north-east of Castor\n" +
                    "<14>\n" +
                    "where you will face processText\n" +
                    "so far unidentified\n" +
                    "combat unit. \n" +
                    "<15>\n" +
                    "Identify and \n" +
                    "destroy it!\n";
        } else if( paramString.equals( new String("ms5") ) ) {
            return "<1>+60000+80000+13\n" +
                    "<2>+1200+100+5+1000\n" +
                    "AL 101A\n" +
                    "<3>+4000+300+4+15000\n" +
                    "BL 102EX\n" +
                    "<4>\n" +
                    "EndingMent\n" +
                    "<10>\n" +
                    "Mission 5\n" +
                    "<11>\n" +
                    "The unidentified air\n" +
                    "unit you faced in \n" +
                    "your last mission was\n" +
                    "<12>\n" +
                    "processText prototype called\n" +
                    "BL-102, the successor\n" +
                    "of the AL-101.\n" +
                    "<13>\n" +
                    "Important supply\n" +
                    "lines towards the\n" +
                    "Maaz mountain \n" +
                    "<14>\n" +
                    "south-east of\n" +
                    "Arcturus indicate\n" +
                    "that the production \n" +
                    "<15>\n" +
                    "line for the BL-102\n" +
                    "is there.\n" +
                    "Destroy the 13 AL-101\n" +
                    "<16>\n" +
                    "A-Type units and then\n" +
                    "follow the navigator\n" +
                    "for 140km and destroy\n" +
                    "<17>\n" +
                    "the BL-102 unit which\n" +
                    "will be waiting for\n" +
                    "you!\n";
        } else if( paramString.equals( new String("ms6") ) ) {
            return "<1>+70000+90000+15\n" +
                    "<2>+1500+150+6+2000\n" +
                    "AL 101B\n" +
                    "<3>+5400+350+5+30000\n" +
                    "FC-12\n" +
                    "<4>\n" +
                    "EndingMent\n" +
                    "<10>\n" +
                    "Mission 6\n" +
                    "<11>\n" +
                    "Now you will enter\n" +
                    "your first sea mission.\n" +
                    "Enemy troops have \n" +
                    "<12>\n" +
                    "gathered in Procyon,\n" +
                    "an island in the\n" +
                    "Arneb sea. \n" +
                    "<13>\n" +
                    "Destroy 15 of the \n" +
                    "AL-101 B-Type units \n" +
                    "and then follow the \n" +
                    "<14>\n" +
                    "navigator for 160km in \n" +
                    "processText low-level flight \n" +
                    "below 100m.\n" +
                    "<15>\n" +
                    "There are many cliffs\n" +
                    "and rocks in the sea, \n" +
                    "so be careful. \n" +
                    "<16>\n" +
                    "On this mission you\n" +
                    "will face FC-12 air\n" +
                    "units.\n" +
                    "<17>\n" +
                    "Engage them in combat\n" +
                    "and bring them down!\n";
        } else if( paramString.equals( new String("ms7") ) ) {
            return "<1>+80000+100000+17\n" +
                    "<2>+2400+200+7+4000\n" +
                    "AL 101EX\n" +
                    "<3>+7000+400+6+50000\n" +
                    "YFC-2\n" +
                    "<4>\n" +
                    "EndingMent\n" +
                    "<10>\n" +
                    "Mission 7\n" +
                    "<11>\n" +
                    "The remaining troops\n" +
                    "have fled south-east\n" +
                    "of Procyon. Destroy\n" +
                    "<12>\n" +
                    "the 17 remaining\n" +
                    "AL-101 units and then \n" +
                    "follow the navigator\n" +
                    "<13>\n" +
                    "180km to processText new area\n" +
                    "where we have seen\n" +
                    "some YFC-2 units, \n" +
                    "<14>\n" +
                    "which are successor\n" +
                    "units of the FC-12.\n";
        } else if( paramString.equals( new String("ms8") ) ) {
            return "<1>+90000+110000+19\n" +
                    "<2>+3000+250+8+8000\n" +
                    "BL 102\n" +
                    "<3>+10000+500+7+0\n" +
                    "ARCHANGEL MK2\n" +
                    "<4>\n" +
                    "EndingMent\n" +
                    "<10>\n" +
                    "Mission 8\n" +
                    "<11>\n" +
                    "Bad News: The main\n" +
                    "force with BL-102s\n" +
                    "is coming for the \n" +
                    "<12>\n" +
                    "final strike. Leave\n" +
                    "now and destroy 19\n" +
                    "units. Then follow\n" +
                    "<13>\n" +
                    "your navigator for\n" +
                    "200km in order to...\n" +
                    "Damn! What is this?!\n" +
                    "<14>\n" +
                    "It seems that you\n" +
                    "will then face an\n" +
                    "Arch Angel MK2 unit, \n" +
                    "<15>\n" +
                    "which was developed \n" +
                    "in processText top secret\n" +
                    "operation. Dealers\n" +
                    "<16>\n" +
                    "seem to have sold the\n" +
                    "construction plans to \n" +
                    "the enemy. This will\n" +
                    "<17>\n" +
                    "be the mightiest unit\n" +
                    "you have ever faced..\n" +
                    "Our fate lies in your\n" +
                    "<18>\n" +
                    "hands!\n" +
                    "\n";
        } else if( paramString.equals(new String("shop")) ) {
            return "<1>+99+199+299+999\n" +
                    "\n" +
                    "1.Missile\n" +
                    "2.Plasma Canon\n" +
                    "3.Armor\n" +
                    "\n" +
                    "<2>+999\n" +
                    "\n" +
                    "You cannot use the\n" +
                    "machine shop while\n" +
                    "you are in training.\n" +
                    "\n" +
                    "<100>+101+102+103+104+105+106+1\n" +
                    "1=IMI 88MM\n" +
                    "2=CALIBER 106MM\n" +
                    "3=CALIBER 128MM\n" +
                    "4=IMI 144MM\n" +
                    "5=KX-T 156MM\n" +
                    "6=KIBA 188MM\n" +
                    "7=BACK\n" +
                    "<110>+111+100\n" +
                    "1.YES\n" +
                    "2.NO\n" +
                    "<111>+100\n" +
                    "1. BACK\n" +
                    "<200>+201+202+203+204+205+206+1\n" +
                    "1=MAG 300KV\n" +
                    "2=LINIE 360KV\n" +
                    "3=DUNA 420KV\n" +
                    "4=MAG 480KV\n" +
                    "5=LINIE 540KV\n" +
                    "6=DUNA 600KV\n" +
                    "7=BACK\n" +
                    "<210>+211+200\n" +
                    "1.YES\n" +
                    "2.NO\n" +
                    "<211>+200\n" +
                    "1.\n" +
                    "<300>+301+302+303+304+305+306+1\n" +
                    "1=T2-COMPO\n" +
                    "2=T-10 COMPO\n" +
                    "3=EX-T COMPO\n" +
                    "4=ERA 1000\n" +
                    "5=ERA 2000\n" +
                    "6=ERA 3000\n" +
                    "7=BACK\n" +
                    "<310>+311+300\n" +
                    "1.YES\n" +
                    "2.NO\n" +
                    "<311>+300\n" +
                    "1.\n";
        } else if( paramString.equals(new String("subm")) ) {
            return "<1>+10+20+30+2+40+999\n" +
                    "1. RESUME\n" +
                    "2. SOUND @    \n" +
                    "3. PLASMA @           \n" +
                    "4. INSTRUCTIONS\n" +
                    "5. MAIN MENU\n" +
                    "6. QUIT\n" +
                    "<2>+1\n" +
                    "MOVEMENT@2=4=6=8 \n" +
                    "DIAGONAL MOVEMENT@1=3 \n" +
                    "MACHINE GUN@5=OK\n" +
                    "MISSILE@9\n" +
                    "PLASMA CANON@5=OK \n" +
                    "PLASMA CANON RELOAD@0 \n" +
                    "1. BACK\n";
        } else if( paramString.equals(new String("system")) ) {
            return "<1>+10+20+30+40+50+999+70\n" +
                    "1.Save Game\n" +
                    "2.Load Game\n" +
                    "3.Sound \n" +
                    "4.Plasma Canon\n" +
                    "5.Information\n" +
                    "6.Quit\n" +
                    "\n" +
                    "<2>+10+20+34+50+999+0\n" +
                    "1.New Game\n" +
                    "2.Continue\n" +
                    "3.Settings\n" +
                    "4.Information\n" +
                    "6.Quit\n" +
                    "\n" +
                    "<10>+11+1\n" +
                    "Do you want to\n" +
                    "save this game?\n" +
                    "\n" +
                    "1.Yes\n" +
                    "2.No\n" +
                    "<12>+1\n" +
                    "Saving complete.\n" +
                    "\n" +
                    "<20>+21+1\n" +
                    "Do you want to\n" +
                    "load the game?\n" +
                    "\n" +
                    "1.Yes\n" +
                    "2.No\n" +
                    "<22>+1\n" +
                    "Loading complete.\n" +
                    "\n" +
                    "<23>+1\n" +
                    "There is no saved\n" +
                    "game.\n" +
                    "\n" +
                    "<31>+33+1\n" +
                    "Sound Control\n" +
                    "\n" +
                    "1.Sound off\n" +
                    "\n" +
                    "<32>+33+1\n" +
                    "Sound Control\n" +
                    "\n" +
                    "1.Sound on\n" +
                    "<34>+30+1\n" +
                    "1. Sound Controls\n" +
                    "\n" +
                    "<41>+43+1\n" +
                    "Plasma Canon\n" +
                    "\n" +
                    "1.Manual\n" +
                    "<42>+43+1\n" +
                    "Plasma Canon\n" +
                    "\n" +
                    "1.Automatic\n" +
                    "\n";
        }

        return "";
    }
}
