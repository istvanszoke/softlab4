import os.path

SCRIPTS = os.path.dirname(__file__)
TOP = os.path.abspath(os.path.join(SCRIPTS, os.pardir, os.pardir))
DOCS = os.path.join(TOP, "docs")
LIB = os.path.join(TOP, "lib")
SRC = os.path.join(TOP, "src")

COMMANDS = os.path.join(SCRIPTS, "commands")
JAVADOC = os.path.join(DOCS, "javadoc")
SRC_MAIN = os.path.join(SRC, "main")
SRC_TEST = os.path.join(SRC, "test")