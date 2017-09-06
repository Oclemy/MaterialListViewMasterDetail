package com.tutorials.hp.materiallistviewmasterdetail.mData;

import com.tutorials.hp.materiallistviewmasterdetail.R;
import java.util.ArrayList;
/**
 - Our data holder class
 - Contains a static arraylist that acts as our data source.
 */
public class DataHolder {

    public static ArrayList<Galaxy> galaxies=new ArrayList<>();
    /*
    - Return galaxies with initial data.
     */
    public static ArrayList<Galaxy> getData()
    {
        if(galaxies.size() == 0)
        {
            Galaxy g=new Galaxy("Galaxy Title","This is the description...",galaxies.size(), R.drawable.cartwheel);
            galaxies.add(g);
        }
        return galaxies;
    }
}
