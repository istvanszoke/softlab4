#!/usr/bin/env python
# coding=utf-8

from __future__ import print_function

import multiprocessing
import os
import re

from multiprocessing import Pool

from lib.scripts import dir, process, util


MESSAGE = "File list generation"


class Entry(object):
    def __init__(self, name, path, size, date, description):
        self.name = name
        self.path = path
        self.size = size
        self.date = date
        self.description = description


def generate_description(class_name, path):
    with open(path, 'rt') as source_file:
        contents = source_file.read()

    desc = re.compile(r"^ +\*.*$", re.M)
    jdoc = desc.search(contents)
    if jdoc is not None:
        return jdoc.group()[3:]

    if class_name[0].lower() in "aeiou":
        pre = "Az"
    else:
        pre = "A"

    type_re = re.compile(r"^ *public +class.*\{.*$", re.M)
    if type_re.search(contents) is not None:
        return pre + " " + class_name + " osztály implementációját tartalmazza."
    else:
        return pre + " " + class_name + " interfész deklarációját tartalmazza."


def get_date(path):
    path = path.replace("\\", "/")
    result = process.run_or_die("git log --diff-filter=A --follow --format=%ai -1 -- " + path,
                                cwd=dir.DOCS,
                                output_function=lambda _: _,
                                error_message="File list generation failed (failed to get creation date from git)")

    timezone = re.compile(r" \+[0-9]+$")
    seconds = re.compile(r":[0-9][0-9]\n", re.M)

    output = timezone.sub("", result)
    output = seconds.sub("~", output)
    output = output.replace(" ", "~")
    output = output.replace("-", ".")
    return output


def create_entry(full_path):
    class_name = os.path.basename(full_path).split(os.extsep)[0]
    relative_path = os.path.relpath(full_path, dir.TOP).replace("\\", "/")
    size = os.path.getsize(full_path)
    date = get_date(full_path)
    description = generate_description(class_name, full_path)
    return Entry(class_name, relative_path, size, date, description)


def generate_entries(path=dir.SRC_MAIN, extension="java"):
    files = util.file_list(path, os.extsep + extension)

    pool = Pool(processes=multiprocessing.cpu_count())
    entries = pool.map_async(create_entry, files).get()
    pool.close()
    entries.sort(key=lambda x: x.path)
    return entries


def print_latex(files):
    with open(os.path.join(dir.DOCS, "includes", "file_list.tex"), "w") as file_list:
        print(r"\begin{tabularx}{\linewidth}{| l | l | l | X |}", file=file_list)
        print(r"\hline", file=file_list)
        print(r"\textbf{Fájl neve} & \textbf{Méret} & \textbf{Keletkezés ideje} & \textbf{Tartalom} \tabularnewline",
              file=file_list)
        print(r"\hline \hline", file=file_list)
        print(r"\endhead", file=file_list)

        for f in files:
            print(r"\fajl", file=file_list)
            print("{{{0}}}\n{{{1} byte}}\n{{{2}}}\n{{{3}}}\n".format(f.path, f.size, f.date, f.description),
                  file=file_list)

        print(r"\end{tabularx}", file=file_list)


def build():
    print_latex(generate_entries())
