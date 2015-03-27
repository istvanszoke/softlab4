#!/usr/bin/env python

from __future__ import print_function

import json
import os
import sys

from lib import util, debug


def import_command(name):
    name = "lib.build." + name
    mod = __import__(name)
    components = name.split('.')
    for comp in components[1:]:
        mod = getattr(mod, comp)
    return mod


def parse_commands():
    with open(os.path.join(util.TOP_DIR, "build_docs.json"), "rt") as commands_file:
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
        try:
            build_command = import_command(command)
            build_command.build()
        except ImportError:
            debug.error(command + " is not a valid command, please check you commands.json file")