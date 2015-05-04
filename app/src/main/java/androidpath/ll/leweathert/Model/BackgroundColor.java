package androidpath.ll.leweathert.Model;

import java.util.ArrayList;
import java.util.Random;

import androidpath.ll.leweathert.R;

/**
 * Created by Le on 2015/4/28.
 */
public class BackgroundColor {
    private ArrayList<PairColor> colorList;

    public BackgroundColor() {
        init();
    }

    private void init() {
        colorList = new ArrayList<PairColor>();
        colorList.add(new PairColor(R.color.theme1_start, R.color.theme1_end));
//        colorList.add("#FF6666");
//        colorList.add("#CC9999");
//        colorList.add("#666699");
//        colorList.add("#FF9900");
//        colorList.add("#0099CC");
//        colorList.add("#CCCC99");
//        colorList.add("#CC3399");
//        colorList.add("#99CC00");
//        colorList.add("#3399CC");
//        colorList.add("#CC6600");
//        colorList.add("#999999");
//        colorList.add("#CCCC33");
//        colorList.add("#FF9933");
//        colorList.add("#009933");
//        colorList.add("#0099CC");
//        colorList.add("#FF9933");
//        colorList.add("#FF9966");
    }


    public PairColor getRandomColor() {
        int index = (int) (Math.random() * colorList.size());
        return colorList.get(index);
    }
}

class PairColor {
    private int startColor;
    private int endColor;

    public PairColor(int startColor, int endColor) {
        this.endColor = endColor;
        this.startColor = startColor;
    }

    public int getStartColor() {
        return startColor;
    }

    public int getEndColor() {
        return endColor;
    }
}
