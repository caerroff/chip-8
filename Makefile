all:
	@javac src/Main.java -d bin

run: all
	@java -cp bin Main