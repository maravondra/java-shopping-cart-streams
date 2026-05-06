# Java Stream API: Shopping Cart Demo 🛒

Jednoduchý výukový projekt v Javě, který demonstruje sílu **Stream API** na příkladu správy nákupního košíku. Tento projekt nahrazuje klasické imperativní cykly elegantním deklarativním zápisem.

## 🎓 Co se v tomto projektu naučíte?

*   **Vytvoření Streamu:** Jak převést kolekci (`List`) na datový proud.
*   **Terminální operace:** Použití `.forEach()` pro iteraci a výpis dat.
*   **Transformace dat:** Použití `.mapToDouble()` pro extrakci konkrétních hodnot z objektů.
*   **Agregace:** Rychlý výpočet součtu pomocí `.sum()`.
*   **Formátování:** Práce s `System.out.printf` pro tvorbu přehledných tabulek v konzoli.
*   **Estetika:** Použití ANSI barevných kódů pro lepší čitelnost výstupu.

## 🛠️ Ukázka kódu: Stream vs. Suma

Místo složitého sčítání v cyklu používáme v tomto projektu tento přístup:
```java
double subTotal = items.stream()
        .mapToDouble(ShopItem::getTotalPrice)
        .sum();