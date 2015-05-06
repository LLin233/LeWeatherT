package androidpath.ll.leweathert.Model;

import android.app.Application;

import java.util.Random;

import androidpath.ll.leweathert.R;

/**
 * Created by Le on 2015/4/28.
 */
public class BackgroundColor extends Application {
    private int[] colorList;
    private int[] colors;

    public void getColorList() {
        colorList = getResources().getIntArray(R.array.androidcolors);
    }

    public int[] getColors() {
        return colors;
    }

    public BackgroundColor genColors() {
        colors = new int[]{
                colorList[new Random().nextInt(colorList.length)],
                colorList[new Random().nextInt(colorList.length)]
        };
        this.setColors(colors);
        return this;
    }


    public void setColors(int[] colors) {
        this.colors = colors;
    }


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

