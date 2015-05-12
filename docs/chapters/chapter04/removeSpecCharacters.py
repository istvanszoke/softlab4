

import glob
import os

myPath = os.path.dirname(os.path.realpath(__file__))

for file in os.listdir(myPath):
	tmp = file
	if '.svg' in tmp:
		tmp = tmp.replace('.',"")
		tmp = tmp.replace('(',"")
		tmp = tmp.replace(')',"")
		tmp = tmp.replace("svg","")
		tmp = tmp + '.svg'
		os.rename(file,tmp)

