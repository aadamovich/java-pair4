package com.playtech;

import java.util.*;

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

        Point origin = new Point(0,0);

        int r = 100;


        Comparator<Point> c = (Point a, Point b) -> (int) Math.round(Math.signum(a.distance(origin) - b.distance(origin)));

        points
                .stream()
                .filter(p -> p.getX() > rect.getX())
                .filter(p -> p.getY() > rect.getY())
                .filter(p -> p.getX() < (rect.getX() + rect.getH()))
                .filter(p -> p.getY() < (rect.getY() + rect.getL()))
                .filter(p -> p.distance(origin) < r)
                .sorted(c)
                .limit(2)
                .forEach(s -> System.out.println(s));




    }

}
