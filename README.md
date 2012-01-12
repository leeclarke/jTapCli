# jTapCLI

##OVERVIEW
This is a quick Command Line interface that I threw together for jTap the Java Membase Tap interface. All credit for jTap goes to mikewied and you can get updated version of the [jTap source code](https://github.com/mikewied/jtap).

Don't want to pull code and build this? Check out the [Downloads](https://github.com/leeclarke/jTapCli/downloads) for a zip of the Distro.

```
usage: jtapc
 -cs,--clearsession                           Clear session info from
                                              drive.
 -dk,--deletekey <key>                        key to be deleted.
 -dumpall                                     Dumps keys and values for
                                              the specified bucket
 -dumpkeys                                    Dumps keys for the specified
                                              bucket
 -gk,--getkey <key>                           key to be retrieved.
 -h                                           Print help for this
                                              application
 -s,--session <host,bucket,[password,port]>   host address of membase
                                              server, bucket name and
                                              optional port if not
                                              default.
```

 