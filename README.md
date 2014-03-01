
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

![SCA](https://f.cloud.github.com/assets/4296272/2302734/c676656a-a18a-11e3-8bb5-0b64745b0606.png)

     

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

Input: 


FF_TYPE: D

FF_NO:2

INPUT_NO: 2

OUTPUT_NO:1

INTERNAL_NO:18

INPUTS: N, D

OUTPUTS: Z

INTERNALS: nDash, dDash, Q1Dash, Q0Dash, D1,

D0, Q1, Q0, i0, i1, i2, i3, i4, i5, i6, i7, i8, i9

DESIGN:

GATE: AND(nDash, dDash, Q1, i0)

GATE: AND(N, Q0Dash, i1)

GATE: AND(D, Q1Dash, Q0, i2)

GATE: AND(N, Q0Dash, i3)

GATE: AND(D, Q0Dash, i4)

GATE: AND(nDash, Q1Dash, Q0, i5)

GATE: AND(dDash, Q1, i7)

GATE: AND(Q1Dash, Q0, N, i8)

GATE: AND(Q0, D, i9)

GATE: OR(i0, i1, i2, D1)

GATE: OR(i3, i4, i5, i6)

GATE: OR(i7, i6, D0)

GATE: OR(i8, i9, Z)

GATE: INVERTER(N, nDash)

GATE: INVERTER(D, dDash)

GATE: INVERTER(Q0, Q0Dash)

GATE: INVERTER(Q1, Q1Dash)

FF: D(D0, Q0)

FF: D(D1, Q1)

Output: 


STATES: S0, S1, S2, S3

STATE_DIAGRAM:

S0 :

ND=00->S0,Z=0

ND=10->S3,Z=0

ND=01->S1,Z=0

ND=11->S3,Z=0

S1 :

ND=00->S1,Z=0

ND=10->S0,Z=1

ND=01->S3,Z=1

ND=11->S2,Z=1

S2 :

ND=00->S3,Z=0

ND=10->S3,Z=0

ND=01->S1,Z=0

ND=11->S3,Z=0

S3 :

ND=00->S3,Z=0

ND=10->S1,Z=0

ND=01->S0,Z=1

ND=11->S0,Z=1

 - Up Synchronous Counter
 
Input:


Ff_TYPE: JK

FF_NO:4

INPUT_NO: 0

OUTPUT_NO:4

INTERNAL_NO:7

OUTPUTS:A, B, C, D

INTERNALS: Q0, Q1, Q2, Q3, J1, K1, J2, K2, J3, K3

DESIGN:

gate: buffer(Q0, J1)

gate: buffer(Q0, K1)

gate: and(Q0, Q1, J2)

gate: buffer(J2, K2)

gate: and(J2, Q2, J3)

gate: buffer(J3, K3)

GATE: BUFFER(Q0, Z0)

GATE: BUFFER(Q1, Z1)

GATE: BUFFER(Q2, Z2)

GATE: BUFFER(Q3, Z3)

FF: JK(1, 1, Q0)

FF: JK(J1, K1, Q1)

FF: JK(J2, K2, Q2)

FF: JK(J3, K3, Q3)

Output


STATES: S0, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12,

S13, S14, S15

STATE_DIAGRAM:

S0:

X=noInputFound->S1,DCBA=0000

S1:

X=noInputFound->S2,DCBA=0001

S2:

X=noInputFound->S3,DCBA=0010

S3:

X=noInputFound->S4,DCBA=0011

S4:

X=noInputFound->S5,DCBA=0100

S5:

X=noInputFound->S6,DCBA=0101

S6:

X=noInputFound->S7,DCBA=0110

S7:

X=noInputFound->S8,DCBA=0111

S8:

X=noInputFound->S9,DCBA=1000

S9:

X=noInputFound->S10,DCBA=1001

S10:

X=noInputFound->S11,DCBA=1010

S11:

X=noInputFound->S12,DCBA=1011

S12:

X=noInputFound->S13,DCBA=1100

S13:X=noInputFound->S14,DCBA=1101

S14:

X=noInputFound->S15,DCBA=1110

S15:

X=noInputFound->S0,DCBA=1111



Tool Limitation:
=================

- The order of statements before and after DESIGN is nether fixed nor a case sensitive.
- Flip Flop types supported : JK, SR, D, T
- Gates supported : AND, NAND, OR, NOR, XOR, XNOR, INVERTER, BUFFER
- Gates max input No. : 3
- Inputs/outputs number: Expected to work with any number (output number can't be zero)
- Internals/Flip Flip number : Expected to work with any number.
- IF there is no input, don't type the INPUTS statement.
- IF 0 or 1 passed to Flip Flop inputs it will be treated as a number not a string variable.
- Flip flop outputs must be in terms of Q0, Q1, Q2, ... .
- Putting INVERTER gate down could cause some bugs, so try to avoid them and put it in the top.
- SCA is not that much user friendly program as expected, tried to make it so but there is still much work to do.

Future Work:
==============

- Handle more errors.
- Drawing the state diagram.


References:
============
Learning Java, Third Edition by Patrick and Jonathan Knudsen. Copyright © 2005, 2002, 2000 O'Reilly Media, Inc.
Java Regular Expression: Taming the java.util.regex Engine Copyright © 2004 by Mehran Habibi.



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
