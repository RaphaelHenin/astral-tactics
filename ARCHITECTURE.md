# Astral Tactics — Architecture

## Décisions — 17 octobre 2026
- Java 25, Gradle 9.8.1 avec Wrapper, libGDX 1.14.2, JUnit Jupiter 6.1.3.
- DSL Gradle Groovy ; versions centralisées dans gradle/libs.versions.toml.
- Modules Gradle, sans JPMS pour cette itération.
- game-core : coordonnées entières et limites de grille. Aucune dépendance libGDX.
- game-client : dépend du domaine, utilise libGDX pour le rendu et les entrées.
- game-desktop : point de composition, fenêtre LWJGL 3, natives et backend de logging.
- Les assets racine sont inclus dans les ressources desktop. Aucun chemin absolu utilisateur.
- SLF4J 2.0.20 / Logback 1.6.5 ; seul desktop journalise pour le moment.

## Représentation spatiale
La grille logique est rectangulaire : colonne et ligne dans [0,10).
L'affichage seul est isométrique. Les centres suivent :

    x = (colonne - ligne) × largeur / 2
    y = -(colonne + ligne) × hauteur / 2

Le repère graphique a Y vers le haut. L'origine est le centre de (0,0).
Chaque case représente [c-.5,c+.5) × [r-.5,r+.5), ce qui donne une convention
unique aux frontières et évite la troncature incorrecte des positions négatives.
La projection et la sélection n'accèdent pas à OpenGL et sont testées sans fenêtre.
La souris passe par viewport.unproject avant la projection inverse.
Les bandes noires du FitViewport sont exclues du picking.

## Boucle graphique et ressources
libGDX appelle create, resize, render et dispose. render redessine l'état courant ;
la sélection change au clic, pas selon le delta temporel. ShapeRenderer est créé
après le contexte OpenGL puis libéré dans dispose. La caméra est orthographique,
avec un FitViewport 1200×800. La carte reste centrée lors du redimensionnement.
Le clic hors carte conserve la sélection ; l'orange a priorité sur le cyan.

## Évolution
Composition simple, sans ECS au départ. Réévaluer après un besoin réel de traitement
uniforme de nombreuses entités. Les futures règles et transitions de combat resteront
dans game-core, indépendantes du temps de rendu et des entrées matérielles.
Un ordre explicite des acteurs et un RNG à graine contrôlée seront étudiés lors du combat.
Aucune promesse de compatibilité multijoueur n'est faite pour le prototype.
