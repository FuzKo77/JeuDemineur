JAVAC = javac
JAVA = java
JFLAGS = -implicit:none

# Cibles
all: CaseDemineur.class EventClicSouris.class OptionJeuEvent.class Grille.class DimensionEvent.class DimensionGrille.class MenuEvent.class MenuAcceuil.class Demineur.class

run: all
	$(JAVA) Demineur

# Dépendances de compilation
CaseDemineur.class: CaseDemineur.java
	$(JAVAC) $(JFLAGS) CaseDemineur.java

EventClicSouris.class: EventClicSouris.java CaseDemineur.class Grille.class
	$(JAVAC) $(JFLAGS) EventClicSouris.java

OptionJeuEvent.class: OptionJeuEvent.java Grille.class
	$(JAVAC) $(JFLAGS) OptionJeuEvent.java

Grille.class: Grille.java CaseDemineur.class EventClicSouris.class OptionJeuEvent.class
	$(JAVAC) $(JFLAGS) Grille.java

DimensionEvent.class: DimensionEvent.java Grille.class
	$(JAVAC) $(JFLAGS) DimensionEvent.java

DimensionGrille.class: DimensionGrille.java DimensionEvent.class Grille.class
	$(JAVAC) $(JFLAGS) DimensionGrille.java

MenuEvent.class: MenuEvent.java DimensionGrille.class Grille.class
	$(JAVAC) $(JFLAGS) MenuEvent.java

MenuAcceuil.class: MenuAcceuil.java MenuEvent.class
	$(JAVAC) $(JFLAGS) MenuAcceuil.java

Demineur.class: Demineur.java MenuAcceuil.class
	$(JAVAC) $(JFLAGS) Demineur.java

clean:
	rm -f *.class sauvegarde.txt


