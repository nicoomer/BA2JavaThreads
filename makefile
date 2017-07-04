JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Article.java \
	Graph.java \
	Main.java \
	Task.java \
	Treatment.java \
	Worker.java \
	Workstation.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class