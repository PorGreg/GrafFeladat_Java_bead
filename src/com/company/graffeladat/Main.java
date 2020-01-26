package com.company.graffeladat;
/*
 * @author Pór Gergő
 */

public class Main {
    public static void main(String[] args) {
        Graf graf = new Graf(6);

        graf.hozzaad(0, 1);
        graf.hozzaad(1, 2);
        graf.hozzaad(0, 2);
        graf.hozzaad(2, 3);
        graf.hozzaad(3, 4);
        graf.hozzaad(4, 5);
        graf.hozzaad(2, 4);

        System.out.println(graf);
        graf.szelessegiBejaras(4);
        System.out.println();
        graf.melysegiBejaras(3);
        System.out.println();
        System.out.println(graf.osszefuggo(5));
        System.out.println();
        System.out.println(graf.feszitoFa(1));
        System.out.println();
        System.out.println(graf.mohoSzinezes());
    }
}
