all: memory
	javac src/*.java -d ./bin

memory:
	javac src/Memory/*.java -d bin

run: all
	java -cp bin Main assets/Landing.ch8
