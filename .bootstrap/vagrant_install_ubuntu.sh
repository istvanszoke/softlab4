#!/bin/bash

sudo apt-get update -qq 
sudo apt-get install -y -qq git
sudo apt-get install -y -qq openjdk-7-jre openjdk-7-jdk openjdk-7-doc
sudo apt-get install -y -qq openjdk-6-jre openjdk-6-jdk openjdk-6-doc
sudo apt-get install -y -qq python librsvg2-bin
sudo apt-get install -y -qq texlive-latex-base texlive-latex-recommended texlive-fonts-recommended texlive-latex-extra texlive-lang-hungarian librsvg2-bin 
cd /vagrant
echo "export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8" >> /home/vagrant/.bash_profile
sudo ln -s /vagrant /home/vagrant/project
cd /home/vagrant
