#!/bin/bash
java -cp lib/commons-cli-1.2.jar:lib/commons-logging-api-1.1.jar:lib/easymock-2.5.2.jar:lib/httpasyncclient-4.0-alpha1.jar:lib/httpclient-4.1.jar:lib/httpcore-4.1.jar:lib/httpcore-nio-4.1.jar:lib/jtap.jar:lib/junit-4.8.2.jar:lib/slf4j-api-1.6.1.jar:lib/jar:slf4j-simple-1.6.1.jar:./jTapCli.jar lee.util.jtap.JTapCli %1 %2
