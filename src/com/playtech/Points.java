package com.playtech;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Points {

    public static void main(String[] args) {

        Rectangle rect = new Rectangle(0,0,3, 10);

        List<Point> points = Arrays.asList(
                new Point(1, 3),
                new Point(1, 3),
                new Point(2, 3),
                new Point(1, 5),
                new Point(1, 70)
        );



        points
                .stream()
                .filter(p -> p.getX() > rect.getX())
                .filter(p -> p.getY() > rect.getY())
                .filter(p -> p.getX() < (rect.getX() + rect.getH()))
                .filter(p -> p.getY() < (rect.getY() + rect.getL()))
                .sorted(p -> p.distance())
                .forEach(System.out::println);


//        ArrayList<Integer> list = new ArrayList<>();
//
//        Map<Integer, Integer> points = new HashMap<>();
//        points.put(5,10);
//        points.put(5,10);
//        points.put("dm",100d);
//        points.put("m",1000d);
//
//        list.add(10);
//        list.add(20);
//
//        Integer x1 = 5;
//        Integer x2 = 12;
//        Integer y1 = 5;
//        Integer y2 = 20;
//
//        List<String> filteredWords = list
//        .stream()
//        .filter( s -> s.length() < 4 )
//        .filter( s -> s.contains(e) )
//        .map(s -> "  " + s)
//        .forEach(System.out.println());


    }

    public static class Rectangle{
        private int x;
        private int y;
        private int h;
        private int l;

        public Rectangle(int x, int y, int h, int l) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.l = l;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getH() {
            return h;
        }

        public int getL() {
            return l;
        }
    }

}
