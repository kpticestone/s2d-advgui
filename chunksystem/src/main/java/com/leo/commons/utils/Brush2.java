package com.leo.commons.utils;

import java.util.Iterator;

import com.badlogic.gdx.math.Circle;
import com.leo.commons.geom.Point;

/*

    #

     #
    #O#
     #

            ###
           #####
           ##O##
           #####
            ###


          ##
          ###
          ####
          O###
          #
          #
          #

 */
public class Brush2 implements Iterable<Point> {

    int[] arr;

    public Brush2(int size) {
//        Circle c = new Circle(0, 0, size);
        this.arr = new int[size];
        for (int x = 0; x < size; x++) {
//            for (int y = 0; y < size; y++) {
//                if (c.contains(x, y)) {
                    this.arr[x] = (int) Math.round(Math.sqrt(size*size - x*x));
//                }
//            }
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return null;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            Brush2 bbr = new Brush2(i);
            bbr.print();
        }
    }

    private void print() {
        System.err.println("--------" + this.arr.length + "--------------");
        for (int a : this.arr) {
            System.err.print(a);
            System.err.print(" ");
        }
        System.err.println();
    }
}
