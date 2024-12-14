all: build run clear

build:
	@javac -d bin Main.java
	@javac -d bin aulas/*.java
	@javac -d bin libs/*.java
run:
	@java -cp bin Main
clear:
	rm -r bin

git:
	git config --global user.name Barradas13
	git config --global user.email febarradas13@gmail.com
script: