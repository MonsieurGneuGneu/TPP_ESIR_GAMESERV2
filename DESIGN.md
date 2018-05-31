# Principe de mise en oeuvre de la solution

## Environnement de travail
Le projet se base sur l'outil JHipster, cet outil permet de générer un front et back parametrable à la création de projet. Il est en effet possible de choisir plusieurs options comme le framework du front-end (Angular, React), l'outil de gestion du back-end (Yarn ou Maven), le type d'authentification et encore pleins d'autres options.

## Mise en oeuvre de la solution
Le client navigue sur une application web et joue aux différents jeux proposés.

Le client interagit avec un tableau de jeu, et à chaque clique une requête est envoyé au serveur qui s'occupe de faire les calculs liés au jeu, puis de faire jouer l'I.A.

La réponse est renvoyée au client qui raffraichit alors le visuel du jeu.

# Règles d'architecture
Voici comment l'application est organisée.

## Modèle statique

## Modèle dynamique
Lancement : Le client se connecte à l'application et arrive sur une page de jeu vierge.

Connexion : Le client se connecte à l'application.

Choix du jeu : Le client choisit un jeu et est ensuite redirigé vers celui-ci.

Paramètres du jeu : Le clien choisit un niveau de difficulté (Domineering et Connect 4) et la taille de la grille (Tic tac toe et Domineering).

Lancement du jeu : Le jeu est initialisé avec les paramètres demandés, une requête d'initialisation est envoyée au serveur qui lui permettra d'initialiser le tableau du jeu côté serveur, pour les calculs et les tours de l'IA.

Jouer : L'utilisateur intéragit avec le jeu en cliquant sur la ou les case(s) sur lesquelles il souhaite jouer (dépendemment du jeu). Une requête est envoyée au serveur pour savoir si il peut jouer (sauf pour Tic tac toe puisqu'il suffit simplement de verifier si la case cliquée n'est pas déjà prise sur le tableau local du front-end).
Si le joueur peut jouer, la requête explicitant son tour est envoyée au serveur (case(s) cliquée(s)). Côté serveur, son tour est pris en compte, puis l'IA commence à jouer. Lorsque l'IA termine son tour, le serveur renvoie une réponse contenant le nouveau tableau (ancien tableau + tour du joueur et de l'IA s'il y a lieu) et l'état de la partie (en cours, joueur gagnant, IA gagnante, égalité).

Cette dernière étape se repète jusqu'à ce que la partie se termine.

Le score est affiché tout au long de la partie sur un bandeau situé à droite du jeu.

Note : Avec une difficulté importante, l'IA prend un certain temps pour jouer son tour.

Erreurs : Lorsque le joueur joue à Domineering, il arrive qu'une ou deux cases du tableau de jeu disparaisse. La provenance de ce bug reste inconnue puisque les tableaux internes (côtés client et serveur) sont pourtant corrects.

# Explication de la prise en compte des contraintes d'analyse
Nous avons rencontré plusieurs problèmes lors de l'avancé de ce projet:

-Premièrement JHipster, c'est un outil très puissant et efficace. Le problème était que nous ne connaissions pas du tout cet outil et qu'il a été difficile de s'y adapter. 
Nous avons finalement réussis à nous adapter et à comprendre son fonctionnement concernant les aspects dont nous avions besoin.

-Connexion du client au serveur: La connexion au serveur et l'obtention d'une réponse à été fastidieuse, JHipster nous facilite pourtant la tâche en nous obligeant par exemple à ommettre l'adresse du serveur dans la requête pour y placer seulement l'adresse de la requête (exemple: /api/domineering/play).
Les requêtes sont de types GET, malgrés de nombreux essais avec POST nous n'arrivions pas à correctement envoyer les paramètres sous forme JSON.

-Interface utilisateur: Quelques problèmes liés à l'interface rencontrés au niveau de JHipster encore. JHipster est fournis avec Bootstrap pour le design des pages, et il est impossible de modifier cela (facilement en tout cas). Souhaitant parfois utilisé angular material, nous avons du restreindre notre selection à Bootstrap.
Il n'y a pas à proprement parler de routage dans l'application, puisque tous les jeux se jouent sur la page d'accueil (modifiée). Un service permet de savoir quel jeu est selectionné et permet alors de modifier le jeu afficher dans la zone de jeu.

# Cadre de production

## Configuration
### FRONT-END
Angular-CLI v. 1.7.4

#### Lancer le front
Rendez-vous dans le dossier TPP_ESIR_GAMESERV2/TPP_ESIR_GAMESERV2_jhipster.

Lancer l'interface client: ```npm start```

### BACK-END
#### MACOSX
Si vous utilisez une version de java différente de la version 8, il faut spécifier au système d'utiliser java8 pour le projet.
Il est possible de le faire via jenv et cask.

Installer java8 avec cask: ```brew cash install java8```

Verifier les versions de java installés : ```jenv versions```
Rendez-vous dans le dossier du projet: ```jenv add local 'javaversion'```

Avant de lancer le serveur : ```jenv exec bash```

#### Lancer le serveur
Rendez-vous dans le dossier TPP_ESIR_GAMESERV2/TPP_ESIR_GAMESERV2_jhipster

optionnel : ```mvn install```

Lancer le serveur : ```./mvnw```

## Intelligence Artificielle
Nous avons décidé de nous baser sur l'algorithme MinMax pour que nos IA puisse jouer au jeu de manière intelligente.

## Interface
Angular 5 avec bootstrap.

## Serveur
Java, géré avec Maven.