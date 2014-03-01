Sequential Circuit Analysis
================================

- Created by: Ehab AlBadawy
- E-Mail : ehalbadawy93@gmail.com


About
=========

SCA is a software tool that helps to find a soultion for Sequential Circuit Analysis. The input to the software
tool is a file containing the sequential Circuit and the output is the file containing
the Mealy State Diagram.
SCA comes with a simple and easy GUI trying to make things easer. Problem code could be typed from scratch in
the codepad or imported from an exiting text file, and the output displayed in the same window.
     

Technologies
============

Java;

Sample tests
===============

 - SRFF with two inputs

Input:

FF_TYPE: SR
FF_NO:1
INPUT_NO: 2
OUTPUT_NO:1
INTERNAL_NO:8
INPUTS:A, B
OUTPUTS:Z
INTERNALS:Q0, Q0', S, R, 1, 2, A', B'
DESIGN:
GATE: INVERTER(Q0, Q0')
GATE: INVERTER(A, A')
GATE: INVERTER(B, B')
GATE: AND(A', B', Q0', 1)
GATE: AND(A, B, Q0', 2)
GATE: OR(1, 2, S)
GATE: AND(A, B, Q0, R)
GATE: AND(A, B, Q0, Z)
FF: SR(S, R, Q0)

Output: 

STATES: S0, S1
STATE_DIAGRAM:
S0:
AB=00->S1,Z=0
AB=10->S0,Z=0
AB=01->S0,Z=0
AB=11->S1,Z=0
S1:
AB=00->S1,Z=0
AB=10->S1,Z=0
AB=01->S1,Z=0
AB=11->S0,Z=1

- Vending Machine



Installation Instructions
=========================

Java jdk:

- Installing on Linux / Ubuntu.

Step 1: Open Application>> Accessories>> Terminal

Step 2: Type commandline as below...

    sudo apt-get install openjdk-7-jdk
    
Test the installation at the Sun Java test webpage and using the command: 

    java -version
     
you should see something like this

    java version "1.7.0_25"
    OpenJDK Runtime Environment (IcedTea 2.3.10) (7u25-2.3.10-1ubuntu0.13.04.2)
    OpenJDK Server VM (build 23.7-b01, mixed mode)


- Installing on Windows.
download the JDK from oracle main site and install it.
http://www.oracle.com/technetwork/java/javase/downloads/index.html


Author
======


- Created by: Ehab AlBadawy.

- Email address: ehalbadawy93@gmail.com

License
=======
(See LICENSE)
