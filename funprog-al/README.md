pour lancer le projet: lancer la commande "sbt run" à la racine du projet

Pour tester le programme: Il faut créer un objet Test prenant en paramètre les donnée dédiée à la configutation de l'environnement.
Il faut ensuite utiliser les fonction test de la classe Test pour vérifier si les données de l'environnement produisent bien les résultat attendu. La comparaison entre les donnée résultat et les données attendu se fait à l'aide d'une fonction assert. La fonction TestProg permet de tester le programme avec des données tests) 


pour tester le  programme avec les données test existante, décommentez les ligne du fichier Main.scala:

3  import test.TestObject
42  TestObject.testProg()

L'environnement de test (donc avec les données de test) est crée avec l'objet Test. Il faut lui fournir les limite de la pelouse et la liste des tondeuse.

github: https://github.com/tcatonet/ScalaTondeuse
