all: Main.class

Main.class: src/Entities/*.java src/Controller/*.java src/Window/*.java src/FIle/*.java
	mkdir -p out
	javac -cp ./src ./src/Main/Main.java -d out

execute: 
	java -cp ./out Main.Main

clean:
	rm -f out/*.class