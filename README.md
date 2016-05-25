# IM Client By Android

##Summary

This is a IM client by android.It's need to connect to ejabberd IM server.I want to achieve some functionales like WeChat.
I'm just want to study framework & code building by coding projects.
If you like it or you have some questions, Feel free to contact me.

##Include Package

* [Android-PullToRefresh](https://github.com/chrisbanes/Android-PullToRefresh) -- Pull To Refresh Views for Android

##Application & Package Version

* Android-PullToRefresh 2.1.1

##Framework Design



##Configuration instructions

	appname : your app name
	httpport : http server port
	runmode : run modes like dev & pro etc
	autorender : close the template rendering, in this is false
	copyrequestbody : Read body information, in this is true
	EnableDocs : enable Docs or not
	mongohost = your mongodb host
	mongoport = your mongodb port, default is 27017
	mongodbname = your mongodb dbname
	filecache : IF you need cache file on desk, you need write directory path in here
	redisnetwork : redis net work type TCP/UDP
	redishost = your redis host
	redisport = your redis port, default is 6379
	redispwd = your redis authentication password

##Usage

	cd YOUR_CODE_PWD
	export GOPATH=$pwd #Set this path of your gopath
	go get github.com/astaxie/beego #need build it
	go get gopkg.in/mgo.v2 #need build it
	cd src & git clone https://github.com/littletwolee/mongoapi.git
	cd conf & modify your configuration
	cd ../ & bee run
