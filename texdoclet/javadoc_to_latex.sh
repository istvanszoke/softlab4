#!/bin/bash

rm ../docs/javadoc/*.tex

OLDDIR=$(pwd)
cd ../src

javadoc -docletpath ../texdoclet/TeXDoclet.jar \
	-doclet org.stfm.texdoclet.TeXDoclet \
	-tree \
	-noindex \
	-hyperref \
	-output ../docs/javadoc/javadoc.tex \
	-sourcepath . \
	-subpackages org:main \
	-include \
	-sectionlevel section \
	-serial \
	-private \

cd $OLDDIR

exit $?	 
