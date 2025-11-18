package model;
import java.awt.*;
import javax.swing.*;

import java.util.Random;

public class Util {
    public static <ImageIcon> ImageIcon RandomPick(ImageIcon[] arr){
        int randomIndex = (int) (Math.random() * arr.length);
        return arr[randomIndex];
    }
}
