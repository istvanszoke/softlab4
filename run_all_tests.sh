#!/usr/bin/bash

for f in src/resources/tests/protoin/*.txt; do
    base=$(basename $f)
    
    echo "${base%%.*}";
    ./gradlew run --quiet < "$f" | 
    java -jar lib/differ.jar src/resources/tests/protoout/"${base%%.*}".txt
    
    echo;
done;
