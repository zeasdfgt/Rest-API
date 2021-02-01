# Maître T - Équipe 05 
![Version](https://img.shields.io/badge/version-2.0-blue.svg?cacheSeconds=2592000)

Ce projet est le développement de Maître T, un système de gestion de restaurant pour le restaurant Lunaire de la TEAM (Terriens et Extraterrestres Amateurs de Manger). La compagnie souhaite créer son propre outil interne de gestion de restaurant pour organiser la grande activité d'ouverture, le Hoppening, du 20 au 30 juillet 2150.

🏠 [Page de l'énoncé du projet](https://projet2020.qualitelogicielle.ca)

## Guide de démarrage

### Prérequis
Logiciels nécessaires au bon fonctionnement de l'application :

- Java 11 (openJDK uniquement)
- Maven 3

### Lancement de l'application
Si c'est la première fois que vous lancez l'application, il vous faut installer les dépendances à l'aide de Maven:
```sh
mvn clean install
```

### Utilisation de l'application
Une fois les dépendances installées, vous pouvez lancer l'application qui sera désservie à l'adresse suivante : http://localhost:8181
```sh
mvn exec:java -pl application
```

## Intégration Docker	

Voici la procédure à suivre afin de rouler le projet sur Docker :

```bash	
docker build -t application-glo4002 .
docker run -p 8080:8080 -p 8181:8181 application-glo4002
```

## Fichier de progression
Afin de faciliter la correction automatique, vous trouvrez un fichier qui indique l’état d’avancement du projet. Effectivement, le fichier nommé progress.json est à la racine du projet. Celui-ci a un contenu JSON similaire à cet exemple :
```
{
  "completed": ["RSV"],
  "started": ["DSO"]
}
```

## Gestion de la persistance
Pour l'instant, le client ne demande pas d'avoir de base de données. L'information est donc stockée en mémoire seulement. Si le serveur est redémarré, toutes les données sont perdues.

## Environnement de production
**Spécification**

Draveur utilise le container docker suivant pour exécuter ses corrections: [maven:3-openjdk-11](https://hub.docker.com/_/maven)

Le code est écrit en java 11.

L’environnement dans ```maven:3-openjdk-11``` ressemble à ceci
```
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /usr/share/maven
Java version: 11.0.8, vendor: N/A, runtime: /usr/local/openjdk-11
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "5.7.12-arch1-1", arch: "amd64", family: "unix"
```
Aucune autre librairie (exemple: JavaFX avec la classe javafx.util.Pair) ou service lié à Java n'est disponible dans ce conteneur. L'application gère donc correctement ses dépendances et les installe à l’aide de Maven.

La collection de tests Postman utilisée pour tester le projet est placée à la racine du projet.

## 👤 Membres de l'équipe

- [Jonathan Caron-Roberge](https://github.com/pvharmo)
- [Nicolas Dionne](https://github.com/NicolasDionne-glo4002)
- [Xavier Filion](https://github.com/Brutalysk)
- [Vincent Grégoire](https://github.com/zeasdfgt)
- [Guillaume Hendriks](https://github.com/guhen3)
- [Stéphanie Lussier](https://github.com/stephanielussier)
- [Samuel Murret-Labarthe](https://github.com/xsam00)
- [Louis-Christophe Poulin](https://github.com/Louisks)
