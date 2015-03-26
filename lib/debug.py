#!/usr/bin/env python

from __future__ import print_function

from lib.util import *


def get_terminal_size():
    import fcntl
    import termios
    import struct

    try:
        hw = struct.unpack('hh', fcntl.ioctl(1, termios.TIOCGWINSZ, '1234'))
    except struct.error:
        try:
            hw = (os.environ['LINES'], os.environ['COLUMNS'])
        except KeyError:
            hw = (25, 80)

    return hw


def info(message):
    print(ascii_format(message + " [INFO]", Colors.BOLD))


def success(message):
    print(ascii_format(message + " [SUCCESS]", Colors.SUCCESS))


def warning(message):
    print(ascii_format(message + " [WARNING]", Colors.WARNING))


def error(message):
    print(ascii_format(message + " [ERROR]", Colors.ERROR))


def separator(message=""):
    (rows, columns) = get_terminal_size()
    print(ascii_format(message.center(columns, '='), Colors.BOLD))