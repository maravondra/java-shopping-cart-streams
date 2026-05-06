package com.engeto.stream;

import java.util.Arrays;
import java.util.List;

public class ExampleStreamMain {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        /**
         * Tento kód je klasickou ukázkou práce se Stream API v Javě. Lze si ho představit jako potrubí, kterým
         * protékají
         * data, přičemž každá metoda představuje jednoho pracovníka u pásu.
         */

// Tímto příkazem "vysypeme" čísla z bedny na běžící pás (vytvoříme Stream). Od této chvíle můžeme s čísly pracovat
// postupně, jedno po druhém.
        int sum = numbers.stream().peek(n -> System.out.println("Na vstupu: " + n))

            /** Mezioperace (Intermediate Operations)
             *  Tyto operace data transformují, ale samy o sobě kód nespustí (jsou tzv. "líné").
             */

            .filter(num -> num % 2 == 0)
            /**
             * Filtr: Pracovník u pásu kontroluje každé číslo.
             * Podmínka num % 2 == 0 propustí dál pouze sudá čísla.
             * V potrubí zůstávají: 2, 4, 6, 8, 10.
             */
            .peek(n -> System.out.println("|--> Prošlo filtrem (sudé): " + n))

            /**
             * Mapování: Tento pracovník vezme každé číslo, které prošlo filtrem, a změní ho.
             * V tomto případě každé číslo vynásobí dvěma.
             * V potrubí nyní proudí: 4, 8, 12, 16, 20.
             */
            .map(num -> num * 2).peek(n -> System.out.println("|----> Po mapování (x2): " + n))

            /**
             *ato operace spustí celý proces a vyprodukuje konečný výsledek.  .reduce(0, (a, b) -> a + b)
             * Redukce (Agregace): Tato metoda "shromáždí" všechna čísla z pásu do jednoho výsledku.
             * 0 je počáteční hodnota (identita).
             * (a, b) -> a + b je instrukce, jak čísla spojit – v tomto případě je sečíst.
             * Průběh: $0 + 4 = 4$, $4 + 8 = 12$, $12 + 12 = 24$, $24 + 16 = 40$, $40 + 20 = 60$.
             */
            .reduce(0, (a, b) -> a + b);


        System.out.println();
        System.out.println();
        System.out.println();


        int sumVar = numbers.stream()
                .peek(n -> System.out.println("Na vstupu: " + n))
                .map(num -> num * 2) //double the even numbers
                .peek(n -> System.out.println("|--> Po mapování (x2): " + n))
                .filter(num -> num % 2 == 0) //filter out odd numbers
                .peek(n -> System.out.println("|----> Prošlo filtrem (sudé): " + n))
                .reduce(0, (a, b) -> a + b);

        System.out.println("Celkový součet: filter -> map :" + sum);

        System.out.println("Celkový součet: map -> filter :" + sumVar);

    }
}
