#!/usr/bin/env python

from __future__ import print_function

import os
import re

from lib.scripts import debug, dir, format, process, util
from lib.scripts.format import Colors


MESSAGE = "Documentation generation"


def normalize(output):
    # Regexes line-by-line:
    # Under/Overfull messages
    # Square brackets
    # LaTeX includes
    # T1 lines
    # Anything with angled brackets
    full = re.compile(r"^((Under|Over)full.*|"
                      r"[[)][0-9]*[]]*.*|"
                      r".*[(<{]+[./\()]+.*|"
                      r"\\T1/ptm.*|.*[<>]+.*)$",
                      re.M)
    newlines = re.compile(r" *" + os.linesep + os.linesep + "+")

    ret = full.sub("", output)
    return newlines.sub(os.linesep + os.linesep, ret)


def colorize(output):
    intro = re.compile(r"(^.*this is pdftex.*$)", re.M | re.I)
    warning = re.compile(r"(^.*warning:.*$)", re.M | re.I)
    error = re.compile(r"(^.*error.*$)", re.M | re.I)
    success = re.compile(r"(^.*written.*pdf.*$)", re.M | re.I)

    ret = intro.sub(format.ascii_format(r"\1", Colors.BOLD), output)
    ret = warning.sub(format.ascii_format(r"\1", Colors.WARNING), ret)
    ret = error.sub(format.ascii_format(r"\1", Colors.ERROR), ret)
    ret = success.sub(format.ascii_format(r"\1", Colors.SUCCESS), ret)
    return ret


def generate():
    process.run_or_die("pdflatex -halt-on-error szoftlab4.tex",
                       cwd=dir.DOCS,
                       encoding="windows-1252",
                       output_function=lambda out: print(normalize(colorize(out))),
                       error_message="Documentation generation failed (pdflatex error)")


def build():
    for i in range(1, 4):
        debug.separator("LaTeX Generation, Pass: {0}".format(i))
        generate()
    util.delete_files(dir.DOCS, ".aux", ".lof", ".log", ".out", ".toc")
