#!/bin/bash

mkdir docs/javadoc
rm docs/javadoc/*.tex

cd src

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

cp ../texdoclet/javadoc_pre.tex ../docs/javadoc

cd ..

exit $?
