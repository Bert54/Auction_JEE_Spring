Pour exécuter le projet, vous avez besoin de :
Angular 10+ pour le front-end (nécessite Node.js)
Java 8+ pour les back-ends
MySQL pour la base de données
RabbitMQ Server pour la communication asynchrone
Un IDE (IntelliJ Ultimate de préférence) pour déployer les applications back-end
Un serveur d’applications (Glassfish de préférence) (nécessaire pour le back-end JEE)


Mise en place de la BDD

Une fois MySQL opérationnel, exécuter le script suivant :

CREATE DATABASE gla;
CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON gla.* TO 'user'@'localhost';
FLUSH PRIVILEGES;

Ce script crée un utilisateur, une BDD et donne les droits à l’utilisateur fraîchement créée sur la nouvelle BDD.


Mise en place du serveur de messagerie asynchrone

Pour RabbitMQ, il vous suffit de vous assurer que celui-ci tourne sur le port 5672.
Note: vous pouvez lancer une instance de RabbitMQ Server via Docker avec la commande suivante :
“docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management”
Cela garantit que les bon ports seront utilisés.

Notez qu’il est impératif que le serveur de messagerie soit lancé et tourne sur le port indiqué pour que les back-ends puissent se lancer.


Configuration du serveur d’applications

Le back-end JEE nécessite un serveur d’application afin de pouvoir fonctionner. Celui-ci doit également être configuré afin de pouvoir utiliser les transactions JTA. Nous allons expliquer comment procéder avec Glassfish 5. Si vous utilisez un autre serveur, vous devrez trouver comment faire par vous-même.

Vous allez tout d’abord devoir ajouter le connecteur MySQL (en .jar) aux libs de Glassfish. Nous vous recommandons de télécharger la version 5.1 du connecteur (par soucis de compatibilité) que vous devrez placer sous *glassfish_install*/glassfish/domains/domain1/lib/
Redémarrer ensuite le serveur si celui-ci tourne.
Vous allez maintenant configurer un pool de connexion JDBC. Pour cela, accéder au terminal de configuration du serveur dans un navigateur via l'adresse suivante : http://localhost:4848. Ensuite, dans le menu à gauche, sélectionnez “Resources”, puis “JDBC”, puis “JDBC Connection Pools”. Sur la page qui s’est affichée, cliquez sur “New”.
Dans le champ “Pool Name”, entrez ‘mysqlpool”. Dans “Resource Type”, sélectionnez “javax.sql.DataSource”. Dans “Database Driver Vendor”, sélectionnez “MySql”. Cliquez ensuite sur “Next”.
Vous allez maintenant éditer certains paramètres dans “Additional properties” en bas de la page. Pour chaque paramètre donné, vous le chercherez dans la liste et si celui-ci n’existe pas, vous devrez le créer via le bouton “Add Property”.
Cherchez le paramètre “UseSSL” puis le set à “false”.
Cherchez le paramètre “User” et le set à “user”.
Cherchez le paramètre “Password” et le set à “password”.
Cherchez les deux champs “URL” et set les deux à “jdbc:mysql://localhost:3306/gla”.
Cliquez sur “Finish”.
Il ne reste plus qu’à créer une ressource JDBC qui va utiliser le pool que vous venez de créer. Dans le menu à gauche, sélectionnez “Resources”, puis “JDBC”, puis “JDBC Resources”. Cliquez sur “New”.
Dans “JNDI Name”, saisir “jdbc/mysqlpool”. Dans Pool Name, sélectionnez la pool que vous venez de créer (“mysqlpool” si vous avez saisi le nom indiqué au-dessus).
Appuyé sur “OK”.
Le serveur est maintenant prêt.


Déploiement du back-end JEE.

Nous allons maintenant vous expliquer comment build et déployer le back-end JEE sur Glassfish en utilisant IntelliJ Ultimate. Si vous utilisez un autre IDE, vous devrez vous débrouiller par vous-même.

Tout d’abord, assurez-vous que le serveur Glassfish est éteint. Ensuite, importez le projet Maven dans IntelliJ. Assurez-vous que le projet utilise bien un JDK 8+.
Vous allez maintenant ajouter une nouvelle configuration. C’est celle-ci qui vous permettra de démarrer le serveur et de déployer l’application, le tout automatiquement.
En haut à droite, cliquer sur “Edit Configurations”. Dans la nouvelle fenêtre, cliquez sur “+” en haut à gauche et chercher “GlassFish Server Local” Une configuration est ajoutée automatiquement. Vous allez maintenant l’éditer. A droite d’”Application server”, cliquez sur “Configure..” Cliquez sur + en haut à gauche et dans le champ “GlassFish Home”, entrez le chemin où vous avez installé votre GlassFish. Appuyez sur “OK”. Votre GlassFish se trouve maintenant dans la liste à gauche. Sélectionnez là et dans “Additional Libraries and Frameworks” en bas, sélectionnez “EJB” si celui-ci est disponible à la sélection. Appuyez sur “OK”.
Dans la fenêtre de configuration, sélectionnez l’onglet “Deployment” et cliquez sur “+” à côté de “Deploy at the server startup”. Cliquez sur “Artifact..”, puis AuctionBackJEE:war exploded”. En dessous de “Deploy at the server startup”, cochez “Use custom context root” et assurez-vous que le champ à côté ne possède qu’un slash “/”.
Retournez sur l’onglet “Server”, puis dans “Server domain”, sélectionnez ou saisir “domain1”.
Enfin, si vous avez une ligne rouge qui s’affiche avec un bouton “Fix” qui apparaît à droite, cliquez sur le bouton. Cliquez sur “Apply” puis “OK”. Si tout va bien, il ne vous reste plus qu’à sélectionner votre configuration, puis à cliquer sur la flèche verte afin de démarrer le serveur et à déployer l’application.


Déploiement du back-end Spring

Vous n’avez pas grand chose à faire. Importez juste le projet Maven sous IntelliJ (Ultimate ou Community, les deux fonctionnent), assurez-vous que le projet utilise un JDK 8+ exécuter le main de la classe “AuctionbackspringApplication”.

Notez que les deux back-ends ne peuvent pas tourner en même temps. Si vous désirez exécuter le back JEE, assurez-vous que le back Spring ne tourne pas. Si vous voulez exécuter le back Spring, assurez-vous que le serveur Glassfish est éteint.


Utilisation de l’application de livraison

Cette application est une simple application en mode console. Vous pouvez donc juste la compiler et la lancer via un terminal. Prenez juste en compte que c’est un projet Maven donc vous devez compiler avec Maven. (commande : mvn package). Vous pouvez également la lancer via un IDE. Dans ce cas, vous devrez importer le projet Maven et lancer l’application comme une application Java classique (via le main).
A noter : il n’est pas nécessaire que celle-ci tourne pour utiliser l’application de vente aux enchères.


Déploiement du front

Dans un terminal, placez-vous dans la racine du projet front. Entrez la commande “npm install”. Attendez que l’installation se termine. Puis entrez la commande “ng serve”. L’application front se lance et est disponible à l’adresse “http://localhost:4200”. Ne fermez pas le terminal et n’interrompez pas l’exécution dedans car si vous faites cela, vous couperez l’application.

