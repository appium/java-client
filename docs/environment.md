# Appium Environment Troubleshooting

Quite often there are questions about why Appium throws an error about missing environment variable, for example `ANDROID_HOME`, or about missing binaries, like `idevice_id`, `carthage` or `java`. 
This article explains what might be a cause of such problem and how to resolve it.

## Prerequisites

In order to understand this topic you should know concept of environment variables, how it works in your operating system and how you can change the system environment if needed. 
In particular, it is important to know the meaning of `PATH` environment variable. Read https://en.wikipedia.org/wiki/Environment_variable and https://en.wikipedia.org/wiki/PATH_(variable) for more details.

## How To Verify What Is Missing

Appium itself is a NodeJS application and uses the same environment as its host `node` process. If you experience an error related to local environment setup then verify the actual process environment first. 
In Mac OS, for example, it is possible to do this via `ps eww <PID>` command, where `PID` is the process identifier of the running Appium's host Node process. 
In Windows the [ProcessExplorer](https://docs.microsoft.com/sysinternals/downloads/process-explorer) utility can be used for such purpose. 
Then make sure the corresponding variable is there and it is set to a proper value, or, in case there is an error finding some binary, make sure the parent folder of this binary is present in `PATH` list, the binary itself on the local file system and can be executed manually.

## How To Fix Missing Environment Variables

Usually, if one starts Appium from the command line, then it inherits all the environment variables from the parent process (`bash`/`cmd`, etc.). 
This means that if you start Appium manually or with a script then make sure its parent process has all the necessary environment variables set to proper values.
Use `env` command to check the currently defined environment variables of your *nix shell interpreter or `set` for cmd.exe shell in Windows.

On *nix system you could add/edit environment variables of your shell either by using the `export` command (only works in scope of the current shell session) or by editing the appropriate shell config if it is necessary to keep the changes.
The path to this config depends on the currently selected interpreter. Use `echo $SHELL` to figure out what your current interpreter is.
Bash, for example, loads the config from `$HOME/.bashrc` or `$HOME/.bash_profile` and ZSH from `$HOME/.zshrc`.
Remember to reload the config after it has been changed either by sourcing it (e.g. `source ~/.zshrc`) or by restarting the terminal session.

On Windows, you could use the `set` command to add/change environment variables in scope of the current shell session or edit them in [system settings](https://www.java.com/en/download/help/path.xml) to keep the changes. 
Remember to reload the config after it has been changed by restarting the command prompt session.
 
Also, it is possible to set variables on [per-process](https://stackoverflow.com/questions/10856129/setting-an-environment-variable-before-a-command-in-bash-not-working-for-second) basis. 
This might be handy if Appium is set up to start automatically with the operating system, because on early stages of system initialization it is possible that the "usual" environment has not been loaded yet.

In case the Appium process is started programatically, for example with java client's `AppiumDriverLocalService` helper class, then it might be necessary to setup the environment [in the client code](https://github.com/appium/java-client/pull/753), because prior to version 6.0 the client does not inherit it from the parent process by default.
