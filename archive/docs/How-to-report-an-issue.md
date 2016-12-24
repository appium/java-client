#Be sure that it is not a server-side problem if you are facing something that looks like a bug

The Appium Java client is the thin client which just sends requests and receives responces generally. 
Be sure that this bug is not reported [here](https://github.com/appium/appium/issues) and/or there is
no progress on this issue.

#The good issue report should contain

### Description

The bug report should contain a brief description of a bug.
If it is the feature request then there should be the description of this feature and the way that it should work.

### Environment (bug report)

* java client build version or git revision if you use some shapshot:
* Appium server version or git revision if you use some shapshot:
* Desktop OS/version used to run Appium if necessary:
* Node.js version (unless using Appium.app|exe) or Appium CLI or Appium.app|exe:
* Mobile platform/version under test:
* Real device or emulator/simulator:

### Details

If it is necessary there should provided more details


### Code To Reproduce Issue (good to Have if you report a bug)

It's easier to reproduce bug and much faster to fix it.
You can git clone https://github.com/appium/sample-code or https://github.com/appium/sample-code/tree/master/sample-code/apps and reproduce an issue using Java and sample apps.
Also you can create a [gist](https://gist.github.com) with pasted java code sample or paste it at ussue description using markdown. About markdown please read [Mastering markdown](https://guides.github.com/features/mastering-markdown/) and 
[Writing on GitHub](https://help.github.com/categories/writing-on-github/)

### Ecxeption stacktraces (bug report)

There should be created a [gist](https://gist.github.com) with pasted stacktrace of exception thrown by java.

### Link to Appium logs

There should be created a [gist](https://gist.github.com) which is a paste of your _full_ Appium logs, and link them to a new issue. Do _not_ paste your full Appium logs at the issue description, as it will make this issue very long and hard to read! 
If you are reporting a bug, _always_ include Appium logs as linked gists! It helps to define the problem correctly and clearly. 


#Issue template
There is [ISSUE_TEMPLATE.md](https://github.com/appium/java-client/blob/master/ISSUE_TEMPLATE.md) which should help you to make a good issue report.

#... And don't say that you weren't warned. 

If a report is not readable and/or there is no response from a reporter and some important details are needed then the issue will be closed after some time.
