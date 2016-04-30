 # Algorithmen und Datenstrukturen

## Algorithmus Bresenham1

Zeichnet Kreis mit Radius R.

## Multiplikation langer Zahlen

__Grundoperationen:__ Operationen, die man in einem einzigen Schritt bzw. in
einer konstanten Zeiteinheit ausfuehren kann.

_Literatur: Taschenbuch der Algorithmen_

## Effizienzmessung

T: I -> N   -   Laufzeit des Algorithmus

Da es von der Eingabe abhaengt, wie schnell das Algortithmus funktioniert, gibt
es verschiedene Faelle:

+ worst case
+ average case
+ best case

### Maze solving algorithms

https://en.wikipedia.org/wiki/Maze_solving_algorithm

__Pledge algorithm__

```c++
repeat
	repeat
		Gehe geradeaus;
	until Wand erreicht;
	Drehe nach rechts;
	inkrementiere Umdrehungszaehler;
	repeat
		Folge dem Hindernis mit einer Hand;
		dabei: je nach Drehrichtung Umdrehungszaehler inc/dec;
	until Umdrehungszaehler = 0;
until Ausgang erreicht;
```

### Kreis zeichnen

Naiver Ansatz waere mit einem Zirkel, also quasi unter der Verwendung von sin
und cos Funktionen. Aber das Problem hier ist, dass die beiden Funktionen sind
sehr teuer, um zu berechnen.

```c++
// Eingabe: Radius R, Pixelanzahl n

for (int i = 0; i < n; i++)
	plot(R * cos(2*pi*i/n), R * sin(2*pi*i/n));	
```

Anderer Ansatz: x*x+y*y=R, aber mit der Wurzel ist es auch etwas teuer.

Noch besser: Ausnutzung von Spiegelaxen. 

__Bresenham Algorithmus__ - Man benutzt nur Multiplikation und Spiegelung.

```c++
x = 0; y = R; plot(0, R); plot(0, -R); plot(R, 0); plot(-R, 0);
F = 1 - R; de = 1; dse = 2 - R - R;
while (x < y) {
	if (F < 0) {
		F = F + de;
	}
	else {
		F = F + dse;
		y--;
		dse = dse + 4;
	}
	x++;
	de = de + 2;
	plot(x, y); plot(x, -y); plot(-x, y); plot(-x, -y);
	plot(y, x); plot(y, -x); plot(-y, x); plot(-y, -x);
}
```

### Multiplikation langer Zahlen

__Grundoperationen:__ Operationen, die man in einem einzigen Schritt bzw. in
einer konstanten Zeiteinheit ausfueren kann.

Arno Eigenwillig und Kurt Mehlhorn:  
	Multiplikation langer Zahlen
	
### Effizienzmessung

Bei der Betrachtung nehmen wir an, dass unsere Zahlen binzer kodiert sind.

### Zufallsvariable

Fuer einen Warscheinlichkeitsraum mit Ergebnismenge Teta nennt man eine
Abbildung X : Teta -> R Zufallsvariable.

### Erwartungswert

Erwartungswerte verhalten sich linear. 

Suche in der Liste. Idee: die haeufigst aufgerufene Elemente sind am Anfang der
Liste.

Suche in einer selbsorganisierender Liste. Jedes Mal, wenn nach einem Element
gesucht wird, wird es zum Anfang verschoben.


Nach dieser Move-to-front rule wird die doppelte Laufzeit erreicht von dem
optimalen.

Bedingte Warscheinlichkeit.

### Sequenzen

Realisierung damit Arrays oder Listen. Beide haben ihre Vorteile und Nachteile.

Dynamisches Feld(als array)

Die Kosten von pushbacks und popbacks werden amortisiert. Es gibt selten teuere
Operationen aber meisten billig und so ergibt sich O(n).

### Doppelt vertettete Liste

Anstatt Elemente zu dealokkieren, fuegen wir diese Knoten in die _free-list_
hinzu.

### Stacks und Queues




