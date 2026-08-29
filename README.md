# Jeu de Demineur

Jeu de Demineur realise en Java avec Swing.

## Telecharger

Sur GitHub, ouvrir le menu **Code**, puis choisir **Download ZIP**. Decompresser ensuite l'archive sur l'ordinateur.

## Prerequis

- Java JDK 8 ou une version plus recente
- `make` (inclus sur macOS et Linux)

## Compiler et lancer

Depuis le dossier du projet :

```bash
make -C src run
```

La commande compile les fichiers Java puis demarre le jeu. Les images du jeu sont incluses dans le dossier `Image_Sae`.

Pour supprimer les fichiers compiles :

```bash
make -C src clean
```

## Lancer sans Make

```bash
cd src
javac *.java
java Demineur
```
