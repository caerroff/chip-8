all: memory
	@javac src/*.java -d bin

memory:
	@javac src/Memory/*.java -d bin/Memory

run: all
	@java -cp bin Main