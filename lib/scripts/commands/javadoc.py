#!/usr/bin/env python

from __future__ import print_function

import os
import os.path
import shutil

from lib.scripts import dir, process, util


MESSAGE = "JavaDoc generation"


def init():
    if not os.path.exists(dir.JAVADOC):
        os.makedirs(dir.JAVADOC)

    util.delete_files(dir.JAVADOC, ".tex", ".map")


def generate_javadoc():
    packages = [f for f in os.listdir(dir.SRC_MAIN) if os.path.isdir(os.path.join(dir.SRC_MAIN, f))]

    process.run_or_die("""javadoc -docletpath '{0}'
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
                           -private""".format(os.path.join(dir.LIB, "TeXDoclet.jar"),
                                              os.path.join(dir.JAVADOC, "javadoc.tex"),
                                              dir.SRC_MAIN,
                                              " ".join(packages)),
                       cwd=dir.DOCS,
                       error_message="JavaDoc generation failed (javadoc error)")


def build():
    init()
    generate_javadoc()
    shutil.copyfile(os.path.join(dir.DOCS, "includes", "javadoc_pre.tex"),
                    os.path.join(dir.DOCS, "javadoc", "javadoc_pre.tex"))
