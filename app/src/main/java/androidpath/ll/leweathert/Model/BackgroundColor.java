package androidpath.ll.leweathert.Model;

import java.util.ArrayList;

/**
 * Created by Le on 2015/4/28.
 */
public class BackgroundColor {
    private ArrayList<String> colorList;

    public BackgroundColor() {
        init();
    }

    private void init() {
        colorList = new ArrayList<String>();
        colorList.add("#FF6666");
        colorList.add("#CC9999");
        colorList.add("#666699");
        colorList.add("#FF9900");
        colorList.add("#0099CC");
        colorList.add("#CCCC99");
        colorList.add("#CC3399");
        colorList.add("#99CC00");
        colorList.add("#3399CC");
        colorList.add("#CC6600");
        colorList.add("#999999");
        colorList.add("#CCCC33");
        colorList.add("#FF9933");
        colorList.add("#009933");
        colorList.add("#0099CC");
        colorList.add("#FF9933");
        colorList.add("#FF9966");
    }
    public String getRandomColor() {
        int index = (int)(Math.random() * colorList.size());
        return colorList.get(index);
    }
}
