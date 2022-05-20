all: Main.class

Main.class: src/Entities/*.java src/Controller/*.java src/Window/*.java src/FIle/*.java
	mkdir -p out
	javac -cp ./src ./src/Main/Main.java -d out

execute1: 
	java -cp ./out Main.Main menu db/logs.txt db/auto.txt

execute2: 
	java -cp ./out Main.Main read modelObject.txt db/auto.txt

clean:
	rm -f out/*.class