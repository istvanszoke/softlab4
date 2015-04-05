#!/usr/bin/env python

from __future__ import print_function

import importlib
import json
import os
import sys

from lib.scripts import debug, dir


def use_command(name):
    name = "lib.scripts.commands." + name
    try:
        build_module = importlib.import_module(name)
        debug.separator(build_module.MESSAGE + " Started")
        getattr(build_module, "build")()
        debug.success(build_module.MESSAGE)
    except ImportError:
        debug.error("No command with the name {0} found".format(name))
        return


def parse_commands():
    with open(os.path.join(dir.TOP, "build_docs.json"), "rt") as commands_file:
        dependencies = json.load(commands_file)

    command_dict = dict()
    for cmd in dependencies:
        command_dict[cmd] = tuple(dependencies[cmd])

    return command_dict


if __name__ == "__main__":
    if len(sys.argv) > 2:
        debug.error("Invalid number of arguments")

    if len(sys.argv) == 1:
        command_name = "all"
    else:
        command_name = sys.argv[1]

    commands = parse_commands()
    [use_command(command) for command in commands[command_name]]


