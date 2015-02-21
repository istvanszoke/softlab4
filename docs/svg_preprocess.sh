#!/bin/bash

for file in $(find . -name '*.svg');
do
    rsvg-convert -f pdf -o "${file%.*}.pdf" "$file";
done;

exit 0
