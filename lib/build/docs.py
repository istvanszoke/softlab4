#!/usr/bin/env python

from __future__ import print_function

import re
import shlex
import sys
from subprocess import Popen, PIPE

from lib import util, debug
from lib.util import Colors


def normalize(output):
    full = re.compile(r"^(Under|Over)full.*$", re.M)
    parens = re.compile(r"^[[)][0-9]*[]]*.*$", re.M)
    sysincludes = re.compile(r"^.*[(<{][/\(]+.*$", re.M)
    local_includes = re.compile(r"^[(]+[./)]+.*$", re.M)
    t1 = re.compile(r"^\\T1/ptm.*$", re.M)
    angle_brackets = re.compile(r"^.*[<>]+.*$", re.M)
    newlines = re.compile(r" *\n\n+")

    ret = full.sub("", output)
    ret = sysincludes.sub("", ret)
    ret = local_includes.sub("", ret)
    ret = parens.sub("", ret)
    ret = t1.sub("", ret)
    ret = angle_brackets.sub("", ret)
    ret = newlines.sub("\n\n", ret)
    return ret


def colorize(output):
    intro = re.compile(r"^.*this is pdftex.*$", re.M | re.I)
    warning = re.compile(r"^.*warning:.*$", re.M | re.I)
    error = re.compile(r"^.*error.*$", re.M | re.I)
    success = re.compile(r"^.*written.*pdf.*$", re.M | re.I)

    ret = intro.sub(util.ascii_format(intro.match(output).group(), Colors.BOLD), output)
    ret = util.format_all(warning, ret, Colors.WARNING)
    ret = util.format_all(error, ret, Colors.ERROR)
    ret = util.format_all(success, ret, Colors.SUCCESS)

    return ret


def generate():
    debug.separator("Generating Documentation")
    process = Popen(shlex.split("pdflatex -halt-on-error szoftlab4.tex"), cwd=util.DOCS_DIR, stdout=PIPE)
    (output, error) = process.communicate()
    exit_code = process.wait()
    output = output.decode("windows-1252")
    print(colorize(normalize(output)))
    if exit_code != 0:
        debug.error("Documentation generation failed (pdflatex error)")
        sys.exit(exit_code)


def build():
    generate()
    generate()
    generate()
    util.delete_files(util.DOCS_DIR, ".aux", ".lof", ".log", ".out", ".toc")
    debug.success("Documentation generation")
