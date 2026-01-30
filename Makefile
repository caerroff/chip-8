all:
	@javac src/* -d bin

run: all
	@java -cp bin Main