# Astral Tactics — Journal de développement

## Session 01 — 17 octobre 2026

### Réalisé
- Configuration Gradle multimodule, toolchain Java 25 et catalogue de versions.
- Wrapper officiel Gradle 9.8.1, distribution protégée par SHA-256.
- Grille logique 10×10, projection isométrique 96×48, survol et sélection persistante.
- Caméra orthographique, FitViewport et exclusion des bandes noires du picking.
- Lancement LWJGL 3 et logs SLF4J / Logback ; audio désactivé pour cette itération.
- Import PowerShell refusant les collisions ; aucun dossier .git livré.
- Documentation architecture, roadmap et démarrage Windows/Linux.

### Vérifications réellement exécutées
Environnement de préparation Linux, Temurin 25.0.4.1, Gradle 9.8.1.
- `gradle clean build` : succès ; trois modules compilés et distributions produites.
- `gradle --offline clean build` : succès après mise en cache des dépendances.
- `bash ./gradlew --version` : succès, Gradle 9.8.1 / JDK 25.0.4.1.
- 10 tests JUnit exécutés : 3 domaine, 5 projection, 2 sélection ; aucun échec.
- La première tentative de résolution des dépendances a été bloquée par le réseau
  du conteneur ; la configuration réseau locale de préparation a permis le build.
  Aucun réglage réseau propre au conteneur n'est inclus dans le projet livré.

### À valider sur le poste utilisateur
- Import des sources dans le dépôt Windows existant.
- Build via gradlew.bat ou bash ./gradlew avec le JDK utilisateur.
- Lancement graphique, survol, clics, coins et redimensionnement.
- Lancement et build hors connexion après préparation des caches Windows.
- Le script PowerShell n'a pas été exécuté ici : PowerShell n'est pas installé.
- Aucun lancement graphique n'a été validé dans cet environnement sans affichage.

### Prochaine étape
Attendre la validation de l'itération 01 avant d'ajouter personnage et déplacements.
