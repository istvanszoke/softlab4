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
The project uses the [Gradle](https://gradle.org/) build system to build the main software and LaTeX to build the documentation. A binary distribution of Gradle is downloaded on the first build, so a working internet connection is required. It is required for `javac` to be located in the `PATH` enviromental variable or the `JAVA_HOME` enviromental variable to be set.

**Common Gradle build commands:**
- `build`: builds the project, the generated files are located in `build/libs`
- `test`: builds the project and runs the available unit tests
- `run`: builds the project and runs the main application
- `install`: places a cross platform distribution of the project under `build/install/softlab4`
- `distZip`: creates a zip archive with platform specific run scripts in `build/distributions/softlab4.zip`
- `distTar`: creates a tar archive with the same specifics as `distZip`

### Building the Java code
On Windows: `./gradlew.bat [common build command]`

On Linux and OS X: `./gradlew [common build command]`

### Building the documentation
Use the `build_docs.py` script located in the root folder with one of the following arguments:
- `all`: builds the complete documentation, including the file list, JavaDoc and `svg` to `pdf` conversion. The resulting file will be located in `./docs/szoftlab4.pdf`.
- `file-list`: for a LaTeX formatted list of Java source files 
- `javadoc`: rebuilds the JavaDoc generated documentation
- `svg`: to rebuild the UML diagrams (run this if you change a diagram without changing its name).

It's also possible to declare custom documentation build commands: this can be done by adding entries to the `build.json` file located in the project root. The key in each entry will correspond to the command name, while the list contains the command components that should be run for that specific command (in order).

#### Dependencies for building the documentation:
**Windows:**
- a working 'LaTeX' distribution with `pdflatex` in `PATH`
- a Python 2 or 3 installation with `python.exe` in `PATH`
-  an `svg` to `pdf` converter in `PATH`:
  - [rsvg-convert](http://sourceforge.net/projects/tumagcc/files/rsvg-convert.exe/download)
  - [Inkscape](https://inkscape.org/en/download/windows/)
  - [ImageMagick](http://www.imagemagick.org/script/binary-releases.php)

**Ubuntu and descendants:**
- `python`
- `texlive-latex-base`
- `texlive-latex-recommended`
- `texlive-fonts-recommended` 
- `texlive-latex-extra`
- an `svg` to `pdf` converter:
  * `librsvg2-bin` 
  * `inkscape` 
  * `imagemagick`: will produce rasterized output (lower quality)

**Arch Linux:**
- `python` or `python2`
- `texlive-bin`
- `texlive-core`
- `texlive-latexextra`
- an `svg` to `pdf` converter:
  - `librsvg`
  - `inkscape`
  - `imagemagick`: will produce rasterized output (lower quality)
 
## Folder structure
- `docs`: files required for the documentation generation
- `gradle`: build system specific files that make it possible to build the project offline
- `lib`: 3rd party libraries and helper scripts
- `src/main`: the main Java sources
- `src/test`: the JUnit unit test sources
