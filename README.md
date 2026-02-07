# Chip-8
A Chip-8 Emulator written in Java.

## Running
After installing Java, run the following to compile and execute
```bash
javac src/*.java -d ./bin
javac src/Memory/*.java -d bin
java -cp bin Main {pathToRom}
```

or, conviniently
```bash
make
make run
```
## Introduction
### Goal
The goal is to have a full Chip-8 Emulator capable of running the basic programs designed for this virtual chip, as well as new ones.

### What is working
- Display (displaying a basic 640x320 window that can be drawn to).
### What is a WIP
- Memory management
- Loading file


## Source
This emulator is following [this](https://www.cs.columbia.edu/~sedwards/classes/2016/4840-spring/designs/Chip8.pdf) documentation. The final product is supposed to respect the design described in the document.
