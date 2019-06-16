package com.amaro.bakingapp.util;

import com.amaro.bakingapp.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DesignUtils {

    private static final List<Integer> gradients =  Arrays.asList(R.drawable.gradient_blue,
            R.drawable.gradient_purple,
            R.drawable.gradient_pink,
            R.drawable.gradient_orange,
            R.drawable.gradient_yellow,
            R.drawable.gradient_green
    );

    public static int randomGradient() {

        Random rand = new Random();
        return gradients.get(rand.nextInt(gradients.size()));
    }

    public static int getGradient(int position) {
        if(position > gradients.size()) {
            position = position % gradients.size();
        }

        return gradients.get(position);
    }

}
