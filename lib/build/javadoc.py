#!/usr/bin/env python

from __future__ import print_function

import os
import os.path
import shlex
import shutil
import sys

from subprocess import Popen, PIPE

from lib import debug, util


def init():
    javadoc_dir = os.path.join(util.DOCS_DIR, "javadoc")
    if not os.path.exists(javadoc_dir):
        os.makedirs(javadoc_dir)

    util.delete_files(javadoc_dir, ".tex", ".map")


def generate_javadoc():
    javadoc_dir = os.path.join(util.DOCS_DIR, "javadoc")
    source_dir = os.path.join(util.SRC_DIR, "main")
    packages = [f for f in os.listdir(source_dir) if os.path.isdir(os.path.join(source_dir, f))]

    process = Popen(shlex.split("""javadoc -docletpath '{0}'
                                           -doclet org.stfm.texdoclet.TeXDoclet
                                           -tree
                                           -noindex
                                           -hyperref
                                           -output '{1}'
                                           -sourcepath {2}
                                           -subpackages {3}
                                           -include
                                           -sectionlevel section
                                           -serial
                                           -private"""
                                .format(os.path.join(util.LIB_DIR, "TeXDoclet.jar"),
                                        os.path.join(javadoc_dir, "javadoc.tex"),
                                        source_dir,
                                        " ".join(packages))),
                    cwd=util.DOCS_DIR,
                    stdout=PIPE)
    (output, error) = process.communicate()
    exit_code = process.wait()
    if exit_code != 0:
        debug.error("JavaDoc generation failed (external command error)")
        sys.exit(exit_code)
    print(output.decode("utf-8"))


def build():
    debug.separator("Generating JavaDoc")
    init()
    generate_javadoc()
    shutil.copyfile(os.path.join(util.DOCS_DIR, "includes", "javadoc_pre.tex"),
                    os.path.join(util.DOCS_DIR, "javadoc", "javadoc_pre.tex"))
    debug.success("JavaDoc generation")