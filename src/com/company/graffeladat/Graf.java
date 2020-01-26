package com.company.graffeladat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Irányítatlan, egyszeres gráf.
 */
public class Graf {
    private final int csucsokSzama;
    /**
     * A gráf élei.
     * Ha a lista tartalmaz egy (A,B) élt, akkor tartalmaznia kell
     * a (B,A) vissza irányú élt is.
     */
    private final ArrayList<El> elek = new ArrayList<>();
    /**
     * A gráf csúcsai.
     * A gráf létrehozása után új csúcsot nem lehet felvenni.
     */
    private final ArrayList<Csucs> csucsok = new ArrayList<>();

    /**
     * Létehoz egy úgy, N pontú gráfot, élek nélkül.
     *
     * @param csucsok A gráf csúcsainak száma
     */
    public Graf(int csucsok) {
        this.csucsokSzama = csucsok;

        // Minden csúcsnak hozzunk létre egy új objektumot
        for (int i = 0; i < csucsok; i++) {
            this.csucsok.add(new Csucs(i));
        }
    }

    /**
     * Hozzáad egy új élt a gráfhoz.
     * Mindkét csúcsnak érvényesnek kell lennie:
     * 0 &lt;= cs &lt; csúcsok száma.
     *
     * @param cs1 Az él egyik pontja
     * @param cs2 Az él másik pontja
     */
    public void hozzaad(int cs1, int cs2) {
        if (cs1 < 0 || cs1 >= csucsokSzama ||
                cs2 < 0 || cs2 >= csucsokSzama) {
            throw new IndexOutOfBoundsException("Hibas csucs index");
        }

        // Ha már szerepel az él, akkor nem kell felvenni
        for (El el : elek) {
            if (el.getCsucs1() == cs1 && el.getCsucs2() == cs2) {
                return;
            }
        }

        elek.add(new El(cs1, cs2));
        elek.add(new El(cs2, cs1));
    }

    // ezt csináltál előszőr, mikor még nem tudtam használni a halmaz sor verem adatszerkezeteket
    // a rendes, azokkal megoldott feladatot a halmazsorverem nevű branch-ben lehet megtalálni
    public void szelessegiBejaras(int kezdoPont) {
        List<Integer> bejartPontok = new ArrayList<>();
        List<Integer> kovetkezoPontok = new ArrayList<>();

        kovetkezoPontok.add(kezdoPont);
        bejartPontok.add(kezdoPont);

        for (int i = 0; i < kovetkezoPontok.size(); i++) {
            int k = kovetkezoPontok.get(i);
            System.out.print(k);

            for (El el : this.elek) {
                if (el.getCsucs1() == k && !bejartPontok.contains(el.getCsucs2())) {
                    kovetkezoPontok.add(el.getCsucs2());
                    bejartPontok.add(el.getCsucs2());
                }
            }
        }
    }

    // mivel én listát használtam halmaz és sor helyett, így ugyanaz, mint a szélességi bejárás
    public void melysegiBejaras(int kezdoPont) {
        List<Integer> bejartPontok = new ArrayList<>();
        List<Integer> kovetkezoPontok = new ArrayList<>();

        kovetkezoPontok.add(kezdoPont);
        bejartPontok.add(kezdoPont);

        for (int i = 0; i < kovetkezoPontok.size(); i++) {
            int k = kovetkezoPontok.get(i);
            System.out.print(k);

            for (El el : this.elek) {
                if (el.getCsucs1() == k && !bejartPontok.contains(el.getCsucs2())) {
                    kovetkezoPontok.add(el.getCsucs2());
                    bejartPontok.add(el.getCsucs2());
                }
            }
        }
    }

    public boolean osszefuggo(int kezdoPont) {
        List<Integer> bejartPontok = new ArrayList<>();
        List<Integer> kovetkezoPontok = new ArrayList<>();

        kovetkezoPontok.add(kezdoPont);
        bejartPontok.add(kezdoPont);

        for (int i = 0; i < kovetkezoPontok.size(); i++) {
            int k = kovetkezoPontok.get(i);

            for (El el : this.elek) {
                if (el.getCsucs1() == k && !bejartPontok.contains(el.getCsucs2())) {
                    kovetkezoPontok.add(el.getCsucs2());
                    bejartPontok.add(el.getCsucs2());
                }
            }
        }

        if (bejartPontok.size() == this.csucsokSzama)
            return true;
        else
            return false;
    }

    public Graf feszitoFa(int kezdoPont) {
        Graf fa = new Graf(this.csucsokSzama);

        List<Integer> bejartPontok = new ArrayList<>();
        List<Integer> kovetkezoPontok = new ArrayList<>();

        kovetkezoPontok.add(kezdoPont);
        bejartPontok.add(kezdoPont);

        for (int i = 0; i < kovetkezoPontok.size(); i++) {
            int k = kovetkezoPontok.get(i);

            for (El el : this.elek) {
                if (el.getCsucs1() == k && !bejartPontok.contains(el.getCsucs2())) {
                    bejartPontok.add(el.getCsucs2());
                    kovetkezoPontok.add(el.getCsucs2());
                    fa.hozzaad(el.getCsucs1(), el.getCsucs2());
                }
            }
        }

        return fa;
    }

    public HashMap<Integer, Integer> mohoSzinezes() {
        HashMap<Integer, Integer> szinezes = new HashMap<>();

        int maxSzin = this.csucsokSzama;

        for (int aktualisCsucs = 0; aktualisCsucs < this.csucsokSzama - 1; aktualisCsucs++) {
            List<Integer> valaszthatoSzinek = new ArrayList<>();
            for (int j = 0; j < maxSzin - 1; j++) {
                valaszthatoSzinek.add(j);
            }
            Collections.sort(valaszthatoSzinek);

            for (El el : this.elek) {
                if (el.getCsucs1() == aktualisCsucs)
                    if (szinezes.containsKey(el.getCsucs2())) {
                        int szin = szinezes.get(el.getCsucs2());
                        valaszthatoSzinek.remove(szin);
                    }
            }
            int valasztottSzin = valaszthatoSzinek.get(0);
            szinezes.put(aktualisCsucs, valasztottSzin);
        }
        return szinezes;
    }

    @Override
    public String toString() {
        String str = "Csucsok:\n";
        for (Csucs cs : csucsok) {
            str += cs + "\n";
        }
        str += "Elek:\n";
        for (El el : elek) {
            str += el + "\n";
        }
        return str;
    }
}

