#!/bin/python

import datetime
import random
import sys

def generate_entry(date, duration, participiants, description):
    participiants_string = "\\newline".join(participiants)
    return """
            {%s}
            {%s h}
            {%s}
            {%s}
           """ % (date, duration, participiants_string, description)

def generate_header():
    return "\\section{Naplo}\n\\begin{naplo}"

def generate_footer():
    return "\\end{naplo}"

PARTICIPIANTS = ["Paral", "Nyari", "Nagy", "Szoke"]

def generate_hours(full_time):
    result = []
    while full_time >= 0:
        duration = random.randint(1,5)
        full_time = full_time - duration
        result.append(duration.__str__())
    return result

def generate_dates(begin, end):
    result = []
    dt = begin + datetime.timedelta(hours=8)
    result.append(dt)
    dt = begin + datetime.timedelta(days=1)
    while dt.day != end.day:
        dt = dt + datetime.timedelta(days=1)
        result.append(dt + datetime.timedelta(hours=random.randint(17,23)))
        print dt
    return result

def choose_participant():
    return [random.choice(PARTICIPIANTS)]

def is_consulation():
    return (random.randint(0,10) % 10) < 2

def run(dates = [], hours = [], participiants = []):
    print generate_header()

    print generate_entry(dates[0], 2, PARTICIPIANTS, "Tevekenyseg: Konzultacio")

    for date in dates:
        generated_entry = ""
        if is_consulation():
            generated_entry = generate_entry(date, hours.pop(), PARTICIPIANTS, "Ertekezlet: Altalanos megbeszeles az elorehaladsrol, tervezes.")
        else:
            generated_entry = generate_entry(date, hours.pop(), choose_participant(), "")
        print generated_entry

    # Tevekenyseg: Reviewra szant ido.
    print generate_entry(dates[-1], random.randint(1,2), ["Paral"], "Tevekenyseg: Reviewra szant ido.")
    print generate_entry(dates[-1], random.randint(1,2), ["Nagy"], "Tevekenyseg: Reviewra szant ido.")
    print generate_entry(dates[-1], random.randint(1,2), ["Szoke"], "Tevekenyseg: Reviewra szant ido.")
    print generate_entry(dates[-1], random.randint(1,2), ["Nyari"], "Tevekenyseg: Reviewra szant ido.")

    print generate_footer()


def main(argv):
    begin = datetime.datetime.strptime(argv[0], "%Y-%m-%d")
    end = datetime.datetime.strptime(argv[1], "%Y-%m-%d")
    run(generate_dates(begin, end), generate_hours(int(argv[2])*4), PARTICIPIANTS)

if __name__ == "__main__":
    main(sys.argv[1:])
