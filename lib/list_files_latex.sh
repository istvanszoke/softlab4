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

echo "\\begin{tabularx}{\linewidth}{| l | l | l | X |}"
echo "\\hline"
echo "\\textbf{Fájl neve} & \\textbf{Méret} & \\textbf{Keletkezés ideje} & \\textbf{Tartalom} \\tabularnewline"
echo "\\hline \\hline"
echo "\\endhead"

for line in "${array[@]}"
do
    split_info "$line"
    date=$(git log --diff-filter=A --follow --format=%ai -1 -- "$path" | awk '{gsub(" \\+[0-9]+.*$", ""); gsub(" ", "~"); gsub(":[0-9]+$", "~"); gsub("-", "."); print}')

    class="${name%.*}"
    
    pre="A"
    if echo "$class" | grep "^[AaEeIiOoUu].*" > /dev/null; then
        pre="Az"
    fi

    description="osztály implementációját tartalmazza."
    if cat "$path" | grep "^.*public interface.*$" > /dev/null; then
        description="interfész deklarációját tartalmazza."
    fi

    echo "\\fajl"
    printf "{%s}\n{%d byte}\n{%s}\n{%s %s %s}\n\n" "$path" "$size" "$date" "$pre" "$class" "$description"
done

echo "\\end{tabularx}"

