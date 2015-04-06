[![Stories in Ready](https://badge.waffle.io/istvanszoke/softlab4.png?label=ready&title=Ready)](https://waffle.io/istvanszoke/softlab4)
[![Stories in Ready](https://badge.waffle.io/gregory094/softlab4.png?label=ready&title=Ready)](https://waffle.io/gregory094/softlab4)
[![Build Status](https://travis-ci.org/gregory094/softlab4.svg?branch=master)](https://github.com/gregory094/softlab4) 
[![Build Status](https://scan.coverity.com/projects/4252/badge.svg)](https://scan.coverity.com/projects/4252)

# Softlab4
[Project Site](https://github.com/gregory094/softlab4) | [Wiki](https://github.com/gregory094/softlab4/wiki) | [Issue Tracker](https://github.com/gregory094/softlab4/issues) | [Coding Style](https://github.com/gregory094/softlab4/wiki/Coding-Style) | [Contribution Guidelines](https://github.com/gregory094/softlab4/blob/master/CONTRIBUTING.md)

This is the main page of the Software Laboratory IV. 2014/15 2nd semester project. The original task description can be found on the following website (in Hungarian): [Szoftver labor 4](https://www.iit.bme.hu/~szoftlab4/). It is aimed at systems that are supported by the Java version 6 or higher. It is licensed under the GNU Lesser Lesser Public License, version 3 (LGPLv3) and belongs to the *finalize* team.

## System Requirements
* **Operating System**
  * Any operating system that are supported by the Java Runtime Environment
  * It has been tested on: Linux, Microsoft Windows and Mac OS X
* **Hardware**
  * System requirements for the different Java versions can be found here: [Java Requirements](http://java.com/en/download/help/sysreq.xml)

## Building the project
The project uses the [Gradle](https://gradle.org/) build system to build the main software and LaTeX to build the documentation. A binary distribution of Gradle is included in the project, so it it possible to build the project without an active internet connection. It is required for `javac` to be located in the `PATH` enviromental variable or the `JAVA_HOME` enviromental variable to be set.

**Common build commands:**
* `build`: builds the project, the generated files are located in `build/libs/*.jar`
* `test`: builds the project and runs the available unit tests
* `run`: builds the project and runs the main application
* `distZip`: creates a zip archive with platform specific run scripts in `build/distributions/*.zip`
* `distTar`: creates a tar archive with the same specifics as `distZip`

### Building on Windows
`./gradlew.bat [common build command]`: in the main project directory to use the common build commands described above.

`./build_docs.sh`: to build the documentation. The resulting file will be located in `./docs/szoftlab4.pdf`.

####Required software to build the documentation:
* a working 'LaTeX' distribution with `pdflatex` in `PATH`
* a POSIX compliant shell that's capable of running `.sh` scripts 
*  an `svg` to `pdf` converter in `PATH`:
  * [rsvg-convert](http://sourceforge.net/projects/tumagcc/files/rsvg-convert.exe/download)
  * [Inkscape](https://inkscape.org/en/download/windows/)
  * [ImageMagick](http://www.imagemagick.org/script/binary-releases.php)

### Building on Linux
`./gradlew [common build command]`: in the main project directory to use the common build commands described above.

`./build_docs.sh`: to build the documentation. The resulting file will be located in `./docs/szoftlab4.pdf`.

####Required packages to build the documentation:
**Ubuntu and descendants:**
* `texlive-latex-base`
* `texlive-latex-recommended`
* `texlive-fonts-recommended` 
* `texlive-latex-extra`
* an `svg` to `pdf` converter:
  * `librsvg2-bin` 
  * `inkscape` 
  * `imagemagick`: will produce rasterized output (lower quality)

**Arch Linux:**
* `texlive-bin`
* `texlive-core`
* `texlive-latexextra`
* an `svg` to `pdf` converter:
  * `librsvg`
  * `inkscape`
  * `imagemagick`: will produce rasterized output (lower quality)
 
## Folder structure
* `docs`: files required for the documentation generation
* `gradle`: build system specific files that make it possible to build the project offline
* `src/main`: the main Java sources
* `src/test`: the JUnit unit test sources
