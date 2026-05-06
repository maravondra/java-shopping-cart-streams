package com.engeto.stream;


import com.engeto.stream.shopExample.Category;
import com.engeto.stream.shopExample.ShopItem;

import java.util.Comparator;
import java.util.List;

public class ShopMain {

    // Definice barev pomocí ANSI únikových kódů pro barevný terminál
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        printBanner();

        System.out.println(ANSI_GREEN + " [STATUS] " + ANSI_RESET + "Aplikace byla úspěšně nastartována.");
        System.out.println(ANSI_GREEN + " [INFO]   " + ANSI_RESET + "Připraveno pro výuku Java Akademie 2026.");
        System.out.println("----------------------------------------------------------");

        // Zde bude pokračovat zbytek tvé aplikac
        List<ShopItem> basket = List.of(
                new ShopItem("Notebook", Category.ELECTRONICS, 20000, 1),
                new ShopItem("Monitor", Category.ELECTRONICS, 5000, 1),
                new ShopItem("Rohlík", Category.FOOD, 3, 10),
                new ShopItem("Java Průvodce", Category.BOOKS, 450, 1)
        );

        printBasket(basket);

        // Volání výpočetní metody
        final double VAT = 0.21; // 21 % DPH
        double finalPrice = calculateTotalWithTax(basket, VAT);

        System.out.printf(ANSI_YELLOW + " CELKOVÁ CENA K ÚHRADĚ (S " + VAT*100 + "" +
                        " DPH): %.2f Kč%n" + ANSI_RESET,
                finalPrice);
        System.out.println("=========================================================================");

        printExtremeItems(basket);

        System.out.println("====== ====== ====== Sorting according the price ====== ====== ======");

        printBasket(getSortedItems(basket));
    }

    private static void printBanner() {
        System.out.println(ANSI_CYAN + "=========================================================================");
        System.out.println("      _                      _                         ");
        System.out.println("     | | __ ___   ____ _    / \\   | | ____ _  __| | ___ _ __ ___  _   _ ");
        System.out.println("  _  | |/ _` \\ \\ / / _` |  / _ \\  | |/ / _` |/ _` |/ _ \\ '_ ` _ \\| | | |");
        System.out.println(" | |_| | (_| |\\ V / (_| | / ___ \\ |   < (_| | (_| |  __/ | | | | | |_| |");
        System.out.println("  \\___/ \\__,_| \\_/ \\__,_|/_/   \\_\\|_|\\_\\__,_|\\__,_|\\___|_| |_| |_|\\__, |");
        System.out.println("                                                                   |___/ ");
        System.out.println("=========================================================================" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " :: Java Akademie ::             (v1.0.0-SNAPSHOT)" + ANSI_RESET);
        System.out.println();
    }

    /**
     * Vytiskne obsah košíku v úhledné tabulce pomocí Stream API.
     */
    private static void printBasket(List<ShopItem> items) {
        String separator = "-".repeat(78);
        System.out.println(ANSI_CYAN + "\n AKTUÁLNÍ OBSAH KOŠÍKU:" + ANSI_RESET);
        System.out.println(separator);

        // Hlavička tabulky
        System.out.printf("%-18s | %-12s | %10s | %8s | %12s%n",
                "Název", "Kategorie", "Cena/ks", "Množství", "Celkem");
        System.out.println(separator);

        // Výpis dat
        items.stream().forEach(item -> System.out.printf("%-18s | %-12s | %10.2f Kč | %8d | %12.2f Kč%n",
                item.name(),
                item.category(),
                item.unitPrice(),
                item.quantity(),
                item.getTotalPrice()
        ));

        System.out.println(separator);

        // Bonus: Rychlá suma základních cen (bez DPH) pro ukázku dalšího streamu
        double subTotal = items.stream()
                .mapToDouble(ShopItem::getTotalPrice)
                .sum();

        // Varianta mit mapToDouble()
        double totalMapToDouble = items.stream()
                .mapToDouble(item -> item.getTotalPrice()) // Převod na DoubleStream (primitiva)
                .sum();                                // DoubleStream má sum() v sobě

        // Varianta s map()
        double totalMap = items.stream()
                .map(item -> item.getTotalPrice())         // Výsledek je Stream<Double> (objekty)
                .reduce(0.0, (sum, price) -> sum + price); // Musíš ručně sčítat přes reduce

        System.out.printf(" Mezisoučet (bez DPH): %50.2f Kč%n", subTotal);
        System.out.printf(" Mezisoučet (bez DPH): %50.2f Kč%n", totalMapToDouble);
        System.out.printf(" Mezisoučet (bez DPH): %50.2f Kč%n", totalMap);
        System.out.println();
    }

    /**
     * Spočítá celkovou cenu všech položek v košíku včetně DPH.
     * Každá položka má svou sazbu daně definovanou v kategorii.
     */
    private static double calculateTotalWithTax(List<ShopItem> items, double vat) {
        return items.stream()
                // mapToDouble transformuje Stream objektů na DoubleStream (proud čísel)
                .mapToDouble(item -> item.getPriceWithTax(vat))
                .sum(); // DoubleStream pak už jen jednoduše sečteme
    }

    private static void printExtremeItems(List<ShopItem> items) {
        items.stream()
        /**
         * Tohle je mozek celé operace. Metoda max potřebuje vědět, podle čeho má určit, co je „největší“.
         * Comparator.comparingDouble(...): Říkáme Jave: „Budeš porovnávat desetinná čísla.“
         * ShopItem::unitPrice: Tohle je method reference. Říkáme tím Jave: „Tu cenu, podle které máš porovnávat,
         * najdeš v každém objektu pod metodou unitPrice().“
         * Průběh: Stream si interně „všimne“ první položky. Pak vezme druhou, porovná jejich ceny a tu
         * dražší si „zapamatuje“. Takto projde celý seznam, dokud mu v ruce nezůstane ta úplně nejdražší.
         */

        /**
         * Tady je důležitý moment. Metoda max() nevrátí přímo ten předmět, ale Optional.
         *
         * Proč? Protože co kdyby byl seznam items prázdný? V takovém případě neexistuje žádné „maximum“. Optional
         * je krabička, která buď ten nejdražší předmět obsahuje, nebo je prázdná. Java nás tak
         * chrání před slavnou NullPointerException.
         */
                .max(Comparator.comparingDouble(item -> item.unitPrice()))
//                .max(Comparator.comparingDouble(ShopItem::unitPrice))
        /**
         * Místo abychom psali složitou podmínku if (something != null), použijeme tuto elegantní metodu.
         *
         * Říká: „Pokud v té krabičce (Optional) něco je, tak vezmi ten obsah (nazveme ho item) a proveď s ním tuhle akci.“
         *
         * Pokud je krabička prázdná (košík byl prázdný), nestane se vůbec nic – program nespadne, jen se nic nevytiskne.
         */
                .ifPresent(item -> System.out.printf("TOP produkt: %s za %.2f Kč%n", item.name(), item.unitPrice()));

        items.stream()
                .min(Comparator.comparingDouble(ShopItem::unitPrice))
                .ifPresent(item -> System.out.printf("Nejlevnější: %s za %.2f Kč%n", item.name(), item.unitPrice()));
    }

    private static List<ShopItem> getSortedItems(List<ShopItem> items) {
        return items.stream()
                .sorted(Comparator.comparingDouble(ShopItem::unitPrice).reversed())
                .toList();
    }
}
