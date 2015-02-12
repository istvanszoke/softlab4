#!/bin/bash

echo "Generating of the documentation started."
cd docs
pdflatex -halt-on-error szoftlab4.tex
if [ $? -ne 0 ]; then
  echo "Generating of the documentation ended. [FAILED]"
  exit -1
fi
cd ..
echo "Generating of the documentation ended. [SUCCESS]"
exit 0
