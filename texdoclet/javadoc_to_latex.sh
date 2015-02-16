#!/bin/bash

javavadoc	-docletpath path/to/TeXDoclet.jar \
    		-doclet org.stfm.texdoclet.TeXDoclet \
    		-noindex \
    		-tree \
    		-hyperref \
		-include \
    		-output ../docs/javadoc/javadoc.tex \
		-sourcepath ../src \
		-subpackages org:com

exit $?	 
