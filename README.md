JBehave BuDDy is a desktop application and IntelliJ IDEA plugin that helps you
write story files. It provides a graphical interface for creating, editing,
and formatting stories.

Q: Why a separate application? Why not use an existing IDE JBehave plugin to 
edit story files?

A: The answer to these questions is quite simple: not all members of a team
that need to edit a story file are developers. In our practice, developers
collaborate with QA and business analysts to write the stories. We have 
found that non-developers are usually more comfortable working outside an 
IDE. We needed a tool that met the needs of any member of the team.  

JBehave BuDDy:

* validates your steps syntax and highlights keywords, making it harder to
introduce difficult-to-debug errors

![Steps](https://github.com/lantunes/jbehave-buddy/raw/master/pics/steps.jpg)

* generates examples, allowing you to see all possible cases based on the 
parameters in the scenario, and to keep only the ones you care about

![Examples](https://github.com/lantunes/jbehave-buddy/raw/master/pics/examples.jpg)

* neatly and correctly formats your story file output

![Story](https://github.com/lantunes/jbehave-buddy/raw/master/pics/story.jpg)

JBehave BuDDy is written for the Java platform. It is distributed as an .exe 
file for Windows systems, and as an executable jar file for all other operating
systems supporting a JVM. Java 1.5 or later is required. It is also a plugin
for the IntelliJ IDEA IDE, supporting versions 12.1 and higher.

Download [the IntelliJ IDEA plugin from the JetBrains Plugin Repository](http://plugins.jetbrains.com/plugin/7458?pr=idea).

![IntelliJPlugin](https://github.com/lantunes/jbehave-buddy/raw/master/pics/intellij.jpg)
