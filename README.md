[![Stories in Ready](https://badge.waffle.io/gregory094/softlab4.png?label=ready&title=Ready)](https://waffle.io/gregory094/softlab4)
[![Build Status](https://travis-ci.org/gregory094/softlab4.svg?branch=master)](https://github.com/gregory094/softlab4) 
[![Build Status](https://scan.coverity.com/projects/4252/badge.svg)](https://scan.coverity.com/projects/4252)

# Softlab4
[Project Site](https://github.com/gregory094/softlab4) | [Wiki](https://github.com/gregory094/softlab4/wiki) | [Issue Tracker](https://github.com/gregory094/softlab4/issues) | [Coding Style](https://github.com/gregory094/softlab4/wiki/Coding-Style) | [Contribution Guidelines](https://github.com/gregory094/softlab4/blob/master/CONTRIBUTING.md)

This is the main page of the Software Laboratory IV. 2014/15 2nd semester project. The original task description can be found on the following website (in Hungarian): [Szoftver labor 4](https://www.iit.bme.hu/~szoftlab4/). It is aimed at systems that are supported by the Java version 6 or higher. It is licensed under the GNU Lesser Lesser Public License, version 3 (LGPLv3) and belongs to the *finalize* team.

## System Requirements
- **Operating System**
  - Any operating system that are supported by the Java Runtime Environment
  - It has been tested on: Linux, Microsoft Windows and Mac OS X
- **Hardware**
  - System requirements for the different Java versions can be found here: [Java Requirements](http://java.com/en/download/help/sysreq.xml)

## Building the project
### Building the Java code
The project uses the [Gradle](https://gradle.org/) build system to build the main software and LaTeX to build the documentation. A binary distribution of Gradle is downloaded on the first build, so a working internet connection is required. It is required for `javac` to be located in the `PATH` enviromental variable or the `JAVA_HOME` enviromental variable to be set.

The build is done with the platform specific Gradle Wrapper script:

**On Windows:** `.\gradlew.bat <build command>`

**On Linux and OS X:** `./gradlew <build command>`

Where `<build command>` is one of the following:

|Command  |Description                              |Output                                        |
|---------|-----------------------------------------|----------------------------------------------|
|`build`  |builds the whole project, including tests|`jar` files in `build/libs`                   |
|`test`   |runs the unit tests                      |none, if project already built                |
|`run`    |runs the main executable                 |none, if project already built                |
|`install`|generates a cross platform distribution  |uncompressed under `build/install/softlab4    |
|`distZip`|generates a cross platform distribution  |zipped under `build/distributions/softlab4.zip|
|`distTar`|generates a cross platform distribution  |tar'd under `build/distributions/softlab4.tar |

### Building the documentation
The documentation generation is handled with a platform independent Python build script:

**All platforms:** `build_docs.py <build command>`

Where `<build command>` is one of the following:

|Command    |Description                                                  |Output                        |
|-----------|-------------------------------------------------------------|------------------------------|
|`file-list`|generates a LaTeX formatted list of the Java sources         |`docs/includes/file_list.tex` |
|`javadoc`  |builds the JavaDoc documentation                             |`docs/javadoc/javadoc.tex`    |
|`pdf`      |generates the readable `pdf` documentation                   |`docs/szoftlab4.pdf`          |
|`svg`      |converts `svg` to `pdf` if necessary                         |generated pdf files in `docs/`|
|`all`      |runs all of the above to generate an up-to-date documentation|`docs/szoftlab4.pdf`          |

If called without arguments, the `all` command will be used.

It's also possible to declare custom documentation build commands: this can be done by adding entries to the `build_docs.json` file located in the project root. The key in each entry will correspond to the command name, while the list contains the command components that should be run for that specific command (in order).

#### Dependencies for building the documentation:
**Windows:**
- a working 'LaTeX' distribution with `pdflatex` in `PATH`
- a Python 2 or 3 installation with `python.exe` in `PATH`
-  an `svg` to `pdf` converter in `PATH`:
  - [rsvg-convert](http://sourceforge.net/projects/tumagcc/files/rsvg-convert.exe/download)
  - [Inkscape](https://inkscape.org/en/download/windows/)
  - [ImageMagick](http://www.imagemagick.org/script/binary-releases.php)

**Linux:**

|Dependency         |Ubuntu packages            |Arch Linux packages       |
|-------------------|---------------------------|--------------------------|
|LaTeX                   |`texlive-latex-base`       |`texlive-bin`             |
|                        |`texlive-latex-recommended`|`texlive-latex-core`      |
|                        |`texlive-latex-extra`      |`texlive-latexextra`      |
|                        |`texlive-font-recommended` |                          |
|                        |                           |                          |
|Python                  |`python` or `python3`      |`python` or `python2`     |
|                        |                           |                          |
|`svg` to `pdf` converter|`librsvg2-bin` or `inkscape` or `imagemagick`|`librsvg` or `inkscape` or `imagemagick`|
 
## Folder structure
|Folder     |Contents                                                              |
|-----------|----------------------------------------------------------------------|
|`docs`     |files required for the documentation generation                       |
|`gradle`   |build system specific files that make it possible to build the project|
|`lib`      |3rd party libraries and helper scripts                                |
|`lib/build`|command components used for documentation generation                  |
|`src/main` |source code for the main project                                      |
|`src/test` |the JUnit unit tests                                                  |

