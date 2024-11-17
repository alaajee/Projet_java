# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive lib/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testLecture CompileLecture TestAffichage TestDjikstra TestScenario TestSimulation TestSimulationNaif

testInvader:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestInvader.java

testLecture:
	javac -d bin -sourcepath src src/tests/TestLecteurDonnees.java
	
CompileLecture: 
	javac -d bin -sourcepath src src/classes/*.java
TestAffichage:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestAffichage.java
TestDjikstra:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestDjikstra.java
TestScenario:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestScenario.java
TestSimulation:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestSimulation.java
TestSimulationNaif:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestSimulationNaif.java
# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:lib/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: 
	java -classpath bin:lib/gui.jar TestInvader

exeLecture: 
	java -classpath bin TestLecteurDonnees cartes/carteSujet.map

exeAffichage:
	java -classpath bin:lib/gui.jar tests/TestAffichage cartes/carteSujet.map
exeDjikstra:
	java -classpath bin:lib/gui.jar tests/TestDjikstra 
exeScenario:
	java -classpath bin:lib/gui.jar tests/TestScenario cartes/carteSujet.map
exeSimulationNaif:
	java -classpath bin:lib/gui.jar tests/TestSimulationNaif cartes/carteSujet.map
exeCarteSujet:
	java -classpath bin:lib/gui.jar tests/TestSimulation cartes/carteSujet.map
exeMushroom:
	java -classpath bin:lib/gui.jar tests/TestSimulation cartes/mushroomOfHell-20x20.map
exeSpiral:
	java -classpath bin:lib/gui.jar tests/TestSimulation cartes/spiralOfMadness-50x50.map
exeDesert:
	java -classpath bin:lib/gui.jar tests/TestSimulation cartes/desertOfDeath-20x20.map	
clean:
	rm -rf bin/*
