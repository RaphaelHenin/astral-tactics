# Astral Tactics — Itération 01

Prototype desktop Java 25 / libGDX : grille isométrique 10×10, survol et sélection.

## Prérequis
JDK 25 complet, JAVA_HOME vers le JDK, Git. IntelliJ avec Project SDK et Gradle JVM
sur le JDK 25, distribution Gradle réglée sur Wrapper. Pas d'installation globale de Gradle.
Connexion requise au premier build pour la distribution Gradle et Maven Central.

## Import dans le dépôt Windows existant
Extraire le ZIP dans un dossier temporaire distinct du dépôt, puis lancer dans PowerShell :

```powershell
& 'CHEMIN_EXTRAIT\astral-tactics\tools\Install-Source.ps1' -Destination "$env:USERPROFILE\Documents\games\astral-tactics"
```

Le script contrôle les collisions avant toute copie et refuse de remplacer des fichiers.
Il ne contient aucun dossier .git et conserve donc le dépôt existant.
Si la politique PowerShell bloque le script, ouvrir le script pour le vérifier puis utiliser
`powershell.exe -NoProfile -ExecutionPolicy Bypass -File ... -Destination ...`
pour cette seule invocation, sans modifier la politique permanente.

## Compiler et lancer — PowerShell
Depuis le dépôt :

```powershell
.\gradlew.bat --version
.\gradlew.bat clean build
.\gradlew.bat :game-desktop:run
```

## Compiler et lancer — MSYS2 UCRT64 / Git Bash
Depuis ~/Documents/games/astral-tactics :

```bash
bash ./gradlew --version
bash ./gradlew clean build
bash ./gradlew :game-desktop:run
```

Sous Linux : `chmod +x gradlew`, puis `./gradlew clean build` et
`./gradlew :game-desktop:run`. Pilote OpenGL et environnement graphique nécessaires.

## Vérification manuelle
1. La fenêtre affiche 100 losanges ; la carte entière est visible.
2. Survoler : case cyan ; coordonnées dans le titre, entre 0 et 9.
3. Cliquer : case orange, sélection persistante après déplacement de la souris.
4. Cliquer une autre case : une seule sélection orange.
5. Vérifier les quatre coins : (0,0) en haut, (9,9) en bas, (9,0) à droite, (0,9) à gauche.
6. Sortir de la carte puis cliquer : aucun survol, sélection conservée.
7. Redimensionner en fenêtre large puis étroite : carte centrée et clics alignés.
8. Les bandes noires ne sélectionnent aucune case.

## Hors connexion
Après un build ET un lancement réussis en ligne, fermer le jeu puis essayer :

```powershell
.\gradlew.bat --offline clean build
.\gradlew.bat --offline :game-desktop:run
```

MSYS2 : mêmes tâches avec `bash ./gradlew --offline ...`.
Cela vérifie les caches pour l'OS courant. Un nouveau PC, un autre OS ou une nouvelle
version de dépendance peut nécessiter un nouveau téléchargement. Conserver le JDK,
le projet et le cache Gradle ; ne pas purger ce cache avant un trajet.

## Sources à lire
- game-core/.../grid/Cell.java et Grid.java : domaine sans libGDX.
- game-client/.../rendering/IsometricProjection.java : projection et picking.
- game-client/.../input/GridSelection.java : état de sélection testable.
- game-client/.../AstralTacticsGame.java : cycle de vie, caméra et dessin.
- game-desktop/.../DesktopLauncher.java : lancement et configuration native.

Les rapports de tests sont dans chaque module sous build/reports/tests/test/index.html.
ARCHITECTURE.md décrit les décisions, ROADMAP.md les sessions et DEVLOG.md les vérifications.
