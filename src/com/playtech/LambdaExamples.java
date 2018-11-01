package com.playtech;

import java.util.*;
import java.util.function.Supplier;

public class LambdaExamples {

    static boolean printingEnabled = true;

    public static void main(String[] args) {

        List<String> data = Arrays.asList("a", "xa", "cs", "ded");

        Collections.sort(data, new MyStringComparator());

        Collections.sort(data, (String o1, String o2) -> o1.length() - o2.length());
        Collections.sort(data, (o1, o2) -> o1.length() - o2.length());

        Comparator<String> c = (o1, o2) -> o1.length() - o2.length();

        Collections.sort(data, c);
        Collections.sort(data, LambdaExamples::compare);

        data.removeIf(s -> s.length() < 2);

        Iterator<String> iter = data.iterator();
        while (iter.hasNext()) {
            if (iter.next().length() < 2) {
                iter.remove();
            }
        }


        printData("Something" + "Expenspive" + getSomethingDatabase());

        Supplier<String> s = () -> "Something" + "Expenspive" + getSomethingDatabase();
        printData(s);
        printData(() -> "Something" + "Expenspive" + getSomethingDatabase());

        debugLog("Something" + "Expenspive" + getSomethingDatabase());
        debugLog(() -> "Something" + "Expenspive" + getSomethingDatabase());

    }

    private static void debugLog(String s) {
    }

    private static void debugLog(Supplier<String> s) {
    }

    private static String getSomethingDatabase() {
        return null;
    }

    public static void printData(String data) {
        if (printingEnabled) {
            System.out.println(data);
        }
    }

    public static void printData(Supplier<String> dataProvider) {
        if (printingEnabled) {
            System.out.println(dataProvider.get());
        }
    }

    public static int compare(String o1, String o2) {
        return o1.length() - o2.length();
    }

    public static class MyStringComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.length() - o2.length();
        }
    }

}