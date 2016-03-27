The Submission.zip file contains 4 folders and this README.txt file.

1. cls - this folder contains the last compiled class files of the source files
2. src - contains the java source file - all *.java files
3. data - contains the cranfield data files
4. documentation - contains project report

To compile the .java files execute below commands in order below:

Go to folder containing source java files using cd command then:

javac Porter.java
javac Token.java
javac Tokenizer.java

To execute: - Replace argument with the location where the data files are placed. Please include double // for directory location in argument

java Tokenizer "//people//cs//d//dxs134530//IR-Assignment1//data//" {Wait till the program completes execution}

Following is the output that will be displayed:

Total number of tokens in the collection:  222864
Total number of unique tokens in the collection:  10154
Total number of tokens with frequency equal to 1 in the collection:  4562

Most Frequent tokens and their frequencies in Cranfield collection: 
Token: the   Frequency: 19442
Token: of   Frequency: 12672
Token: and   Frequency: 6660
Token: a   Frequency: 5933
Token: in   Frequency: 4642
Token: to   Frequency: 4529
Token: is   Frequency: 4111
Token: for   Frequency: 3490
Token: are   Frequency: 2427
Token: with   Frequency: 2263
Token: on   Frequency: 1940
Token: at   Frequency: 1834
Token: by   Frequency: 1748
Token: flow   Frequency: 1736
Token: that   Frequency: 1565
Token: an   Frequency: 1386
Token: be   Frequency: 1271
Token: pressure   Frequency: 1133
Token: from   Frequency: 1116
Token: as   Frequency: 1111
Token: this   Frequency: 1080
Token: which   Frequency: 974
Token: number   Frequency: 964
Token: boundary   Frequency: 897
Token: results   Frequency: 885
Token: it   Frequency: 852
Token: mach   Frequency: 817
Token: theory   Frequency: 775
Token: layer   Frequency: 728

Average number of tokens in a document in the collection:  159

Time taken to tokenize:  15 seconds

Time required to acquire text characteristic in sec: 1
Number of stems:206038
Number of unique words:4543
Number of words occur only once: 1527
Number of doc scanned:1400
Average number of distinct word stems per document = 78
Top 30 frequency words: 
the=18695
of=11356
and=5711
a=5333
to=4404
in=4254
is=4110
for=3268
ar=2428
with=2084
on=1865
flow=1705
by=1703
at=1592
that=1570
be=1366
an=1261
pressur=1251
number=1246
as=1102
thi=1080
result=1073
from=1067
it=1034
boundari=998
which=969
layer=948
effect=849
method=824
theori=793
