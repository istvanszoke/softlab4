#!/bin/bash

cd "$TOP_DIR"

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

description=
get_generic_description() {
    class="${1%.*}"
    
    pre="A"
    if echo "$class" | grep "^[AaEeIiOoUu].*" > /dev/null; then
        pre="Az"
    fi

    description="$pre $class osztály implementációját tartalmazza."
    if cat "$2" | grep "^.*public interface.*$" > /dev/null; then
        description="$pre $class interfész deklarációját tartalmazza."
    fi
}

get_javadoc_description() {
    description=$(cat $1 | grep "^ \* .*$" | head -n 1 | cut -c4-)
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
    
    get_javadoc_description "$path"

    if [ -z "$description" ]; then
        get_generic_description "$name" "$path"
    fi

    echo "\\fajl"
    printf "{%s}\n{%d byte}\n{%s}\n{%s}\n\n" "$path" "$size" "$date" "$description"
done

echo "\\end{tabularx}"

