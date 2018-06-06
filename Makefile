.PHONY: build clean run

build: tema3

run:
	java -Xmx1G Main ${ARGS}

tema3:
	javac *.java

clean:
	rm -rf *.class