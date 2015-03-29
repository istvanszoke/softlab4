#!/usr/bin/env python

from __future__ import print_function

import importlib
import json
import os
import sys

from lib.scripts import debug, dir, util


def use_command(name):
    name = "lib.scripts.commands." + name
    try:
        build_command = getattr(importlib.import_module(name), "build")
    except ImportError:
        debug.error("No command with the name {0} found".format(name))
    build_command()


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
    for command in commands[command_name]:
        use_command(command)


