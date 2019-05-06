# Appium Environment Troubleshooting

Quite often there are questions about why Appium throws an error about missing environment variable, for example `ANDROID_HOME`, or about missing binaries, like `idevice_id`, `carthage` or `java`. This article explains what might be a cause of such problem and how to resolve it.

## Prerequisites

In order to understand this topic you should know concept of environment variables, how it works in your operating system and how you can change the system environment if needed. In particular, it is important to know the meaning of `PATH` environment variable. Read https://en.wikipedia.org/wiki/Environment_variable and https://en.wikipedia.org/wiki/PATH_(variable) for more details.

## How To Verify What Is Missing

Appium itself is a NodeJS application and uses the same environment as its host `node` process. If you experience an error related to local environment setup then verify the actual process environment first. In Mac OS, for example, it is possible to do this via `ps eww <PID>` command, where `PID` is the process identifier of the running Appium's host Node process. In Windows the [ProcessExplorer](https://docs.microsoft.com/sysinternals/downloads/process-explorer) utility can be used for such purpose. Then make sure the corresponding variable is there and it is set to a proper value, or, in case there is an error finding some binary, make sure the parent folder of this binary is present in `PATH` list, the binary itself on the local file system and can be executed manually.

## How To Fix Missing Environment Variables

Usually, if one starts Appium from the command line, then it inherits all the environment variables from the parent process (`bash`/`cmd`, etc.). This means that if you start Appium manually or with a script then make sure its parent process has all the necessary environemnt variables set to proper values. Also, it is possible to set variables on [per-process](https://stackoverflow.com/questions/10856129/setting-an-environment-variable-before-a-command-in-bash-not-working-for-second) basis. This might be handy if Appium is set up to start automatically with the operating system, because on early stages of system initialization it might be that the "usual" environment is not present yet.

In case the Appium process is started programatically, for example with java client's `AppiumDriverLocalService` helper class, then it might be necessary to setup the environment [in the client code](https://github.com/appium/java-client/pull/753), because prior to version 6.0 the client does not inherit it from the the parent process by default.
