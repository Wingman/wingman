# Wingman Client
[![LGPLv3](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Releases](https://img.shields.io/github/release/Wingman/wingman.svg)](https://github.com/Wingman/wingman/releases)
![Travis-CI](https://travis-ci.org/Wingman/wingman.svg)

Wingman is an open source Oldschool RuneScape client.

Here are some of the features Wingman has to offer:

* **Plugin support**
  * Very fast and efficient game API
  * Easy-to-use event listener system
  * Supports JARed plugins
* **Settings screen**
  * Entries easily insertable by plugins
* **Ability to add/remove game code using ASM**
  * Make plugins out of the ordinary

## Launcher
If you like your Wingman kept up to date, and you are not running the client from source, consider using the [launcher](https://github.com/Wingman/wingman-launcher) which downloads the latest [Wingman release](https://github.com/Wingman/wingman/releases).

## Plugins
The plugin loader attempts to find and register all @Plugin annotated classes visible by the class loader. The class loader knows of all files in the client + user.home/Wingman/plugins.

More information is available on the [wiki](https://github.com/Wingman/wingman/wiki).

## Building
Wingman uses [Gradle](https://docs.gradle.org/current/userguide/userguide.html) for dependency management and building. Building from your own PC is really simple.

The project uses the `us.kirchmeier.capsule` plugin for packing the source and dependencies into a fully runnable JAR. Try this code to generate Wingman-capsule.jar*, which will reside in /build/libs:
```
gradlew fatCapsule
```

In order to build the project without dependencies, you can replace `gradle fatCapsule` with `gradle build`, as shown below.
```
gradlew build
```

(*Note that Wingman-capsule.jar is named Wingman.jar in the [releases](https://github.com/Wingman/wingman/releases).)

## Contributing
Contributing to the development of the client can be done by forking the project, making a change and then creating a pull request. You are also highly welcome to submit a [new issue](https://github.com/Wingman/wingman/issues/new) when something has gone wrong or when you want to suggest a new feature.
