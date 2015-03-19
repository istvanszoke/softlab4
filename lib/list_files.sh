#!/bin/bash

cd ..

name=
path=
size=
split_info() {
    local IFS=';'
    set -f
    split_line=( $@ )
    set +f
    name="${split_line[0]}"
    path="${split_line[1]}"
    size="${split_line[2]}"
}

array=
split_lines() {
    local IFS=$'\n'
    set -f
    array=( $@ )
    set +f
}

files=$(find -type f -name *.java -printf '%f;%p;%s\n' | sed s/\\.\\///g | sort -t';' -k2)

split_lines "$files"

longest_path=-1
for line in "${array[@]}"
do
    split_info "$line"
    
    if [ "${#path}" -gt "$longest_path" ]; then
        longest_path="${#path}"
    fi
done

for line in "${array[@]}"
do
    split_info "$line"
    date=$(git log --diff-filter=A --follow --format=%ai -1 -- "$path" | awk '{gsub(" \\+[0-9]+.*$", ""); print}')
     
    printf "%-*s\t%s\t%5d bytes\n" "$longest_path" "$path" "$date" "$size" 
done
