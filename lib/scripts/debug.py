#!/usr/bin/env python

from __future__ import print_function

import os

from . import format
from lib.scripts.format import *


def get_terminal_size():
    import platform
    current_os = platform.system()

    if current_os == "Windows":
        return get_terminal_size_windows()
    else:
        return get_terminal_size_linux()


def get_terminal_size_windows():
    res = None
    try:
        from ctypes import windll, create_string_buffer
        h = windll.kernel32.GetStdHandle(-12)
        csbi = create_string_buffer(22)
        res = windll.kernel32.GetConsoleScreenBufferInfo(h, csbi)
    except:
        return 80, 25
    if res is not None:
        import struct
        (bufx, bufy, curx, cury, wattr,
         left, top, right, bottom, maxx, maxy) = struct.unpack("hhhhHhhhhhh", csbi.raw)
        sizex = right - left + 1
        sizey = bottom - top + 1
        return sizex, sizey
    else:
        return 80, 25


def get_terminal_size_linux():
    import fcntl
    import termios
    import struct

    try:
        hw = struct.unpack('hh', fcntl.ioctl(1, termios.TIOCGWINSZ, '1234'))
    except struct.error:
        try:
            hw = (os.environ['LINES'], os.environ['COLUMNS'])
        except KeyError:
            hw = (80, 25)

    return hw


def info(message):
    print(format.ascii_format(message + " [INFO]", Colors.BOLD))


def success(message):
    print(format.ascii_format(message + " [SUCCESS]", Colors.SUCCESS))


def warning(message):
    print(format.ascii_format(message + " [WARNING]", Colors.WARNING))


def error(message):
    print(format.ascii_format(message + " [ERROR]", Colors.ERROR))


def separator(message=""):
    (columns, rows) = get_terminal_size()
    print(format.ascii_format(message.center(columns, '='), Colors.BOLD))

