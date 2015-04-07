#!/usr/bin/env python

from __future__ import print_function

import os
import os.path
import re
import shutil

from lib.scripts import dir, process, util


MESSAGE = "JavaDoc generation"


def init():
    if not os.path.exists(dir.JAVADOC):
        os.makedirs(dir.JAVADOC)

    util.delete_files(dir.JAVADOC, ".tex", ".map")


def generate_javadoc():
    packages = [] 
    
    pkg_regex = re.compile(r".*main.(.*)")
    for dirname, dirnames, filenames in os.walk(dir.SRC_MAIN):
        for subdirname in dirnames:
            pkg = "{0}.{1}".format(dirname, subdirname)
            packages.append(pkg_regex.match(pkg).group(1))
    
    packages.sort()

    process.run_or_die("""javadoc -docletpath '{0}'
                           -doclet org.stfm.texdoclet.TeXDoclet
                           -shortinherited
                           -nosummaries
                           -doctype fancyhdr
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
