#!/bin/bash

BASEDIR=$(dirname $0)
PIDFILE=$BASEDIR/pidfile

if [ -z "$1" ]; then
	# write error message
	echo "usage: spqr-websocket-server.sh {start|stop} <configuration file>"
	exit 1
fi

if [ -z "$2" ]; then
	# write error message
	echo "usage: spqr-websocket-server.sh {start|stop} <configuration file>"
	exit 1
fi

if [ "$1" = "start" ]; then
	
	# check for running service 
	if [ -f "$PIDFILE" ]; then
   		echo "spqr websocket server is running!"
   		exit 1
	fi
	
	CLASSPATH=.:$BASEDIR/../lib/*
    java -d64 -server -XX:MaxPermSize=512M -XX:MaxGCPauseMillis=500 -XX:+UseG1GC -Xms1G -cp $CLASSPATH com.ottogroup.bi.spqr.websocket.server.SPQRWebSocketServer $2 &	
	echo $! > $PIDFILE
	echo "spqr websocket server running. process id: `cat $PIDFILE`"
fi

if [ "$1" = "stop" ]; then

	# check for running service
	if [ ! -f "$PIDFILE" ]; then
   		echo "spqr websocket server is not running!"
   		exit 1
	fi

	kill -9 `cat $PIDFILE`
	rm $PIDFILE
	echo "spqr websocket server shut down!"
fi

if [ "$1" = "restart" ]; then
	# check for running service
	if [ -f "$PIDFILE" ]; then
		kill -9 `cat $PIDFILE`
	    rm $PIDFILE
		echo "spqr websocket server shut down!"
	fi

	CLASSPATH=.:$BASEDIR/../lib/*
    java -d64 -server -XX:MaxPermSize=512M -XX:MaxGCPauseMillis=500 -XX:+UseG1GC -Xms1G -cp $CLASSPATH com.ottogroup.bi.spqr.websocket.server.SPQRWebSocketServer $2 &	
	echo $! > $PIDFILE
	echo "spqr websocket server running. process id: `cat $PIDFILE`"
fi









