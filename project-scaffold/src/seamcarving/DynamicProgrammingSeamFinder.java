package seamcarving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findSeam(Picture picture, EnergyFunction f) {
        // TODO: Replace with your code
        List<Integer> yCoor = new ArrayList<Integer>();
        double[][] pixels = new double[picture.width()][picture.height()];
        for(int y = 0; y < picture.height(); y++){
            pixels[0][y] = f.apply(picture, 0, y);
        }
        double min_pred = 0;
        for(int x = 1; x < picture.width(); x++){
            for(int y = 0; y < picture.height(); y++){
                double total_energy = f.apply(picture, x,y);
                if(y == 0){
                    min_pred = Math.min(pixels[x - 1][y],
                            pixels[x - 1][y + 1]);
                } else if(y == picture.height() - 1){
                    min_pred = Math.min(pixels[x - 1][y],
                            pixels[x - 1][y - 1]);
                } else {
                    min_pred = Math.min(pixels[x - 1][y],
                            Math.min(pixels[x - 1][y + 1],
                                    pixels[x - 1][y - 1]));
                }
                pixels[x][y] = total_energy + min_pred;
            }
        }
        int currentY = 0;
        double currentYEnergy = Double.MAX_VALUE;
        for(int y = 0; y < picture.height(); y++){
            if(currentYEnergy > pixels[picture.width() - 1][y]) {
                currentYEnergy = pixels[picture.width() - 1][y];
                currentY = y;
            }
        }
        yCoor.add(currentY);
        for(int x = picture.width() - 1; x > 0 ; x--){
            yCoor.add(findMinPredY(currentY, x, pixels));
            currentY = findMinPredY(currentY, x, pixels);
        }
        Collections.reverse(yCoor);
        return yCoor;
    }

    private int findMinPredY(int y, int x, double[][] pixels){
        int minPredY = 0;
        if(y == 0){
            if(pixels[x-1][y] > pixels[x-1][y+1]){
                minPredY = y + 1;
            } else {
                minPredY = y;
            }
        } else if(y == pixels[0].length - 1){
            if(pixels[x-1][y] > pixels[x-1][y-1]){
                minPredY = y - 1;
            } else {
                minPredY = y;
            }
        } else {
            if(pixels[x-1][y] < pixels[x-1][y-1]){
                if(pixels[x-1][y] < pixels[x-1][y+1]) {
                    minPredY = y;
                } else {
                    minPredY = y + 1;
                }
            } else {
                if(pixels[x-1][y+1] < pixels[x-1][y-1]){
                    minPredY = y + 1;
                } else {
                    minPredY = y - 1;
                }
            }
        }
        return minPredY;
    }
}
