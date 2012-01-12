@echo off 

set CP=lib/commons-cli-1.2.jar
set CP=%CP%;lib/commons-logging-api-1.1.jar
set CP=%CP%;lib/easymock-2.5.2.jar
set CP=%CP%;lib/httpasyncclient-4.0-alpha1.jar
set CP=%CP%;lib/httpclient-4.1.jar
set CP=%CP%;lib/httpcore-4.1.jar
set CP=%CP%;lib/httpcore-nio-4.1.jar
set CP=%CP%;lib/jtap.jar
set CP=%CP%;lib/junit-4.8.2.jar
set CP=%CP%;lib/slf4j-api-1.6.1.jar
set CP=%CP%;lib/jar;slf4j-simple-1.6.1.jar
set CP=%CP%;jTapCli.jar


@java -cp %CP% lee.util.jtap.JTapCli %1 %2