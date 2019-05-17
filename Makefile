all:
	javac -sourcepath src -d out -classpath sat4j-1.6.jar src/MainClass.java
	java -classpath out;sat4j-1.6.jar MainClass
