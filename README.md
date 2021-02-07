# CSV Processor

Commandline program to convert data to another format. I don't like to use processor name, but nothing better came to my
mind.

## How to use it

By default, the program will try to read data.csv from the input folder and convert it to filtered and sorted CSV saved
to the output folder at the end.

### Supported application props

You can use optional Command line arguments to change the behavior of the app:

- `fileType=csv` (support for csv, html, xls, xlsx) but only html and csv is implemented
- `filename=data.csv` name of a file located inside the input folder
- `dataType=0` defines what processor should be used (for now we have only MarketShareCsvProcessor)

### Filters

<p>Filters are solved inside processor class implementation.</p>
<p>Programmer can edit implementation of processor, or he can just change fields specified in implemented processor constructor called in main class.</p>

### Sorting

<p>Sort changes are only possible by changing processor implementation class for now but the programmer can handle it easily providing a custom comparator function. Sort by a vendor is already implemented inside MarketShareOutput class and <strong>can be used changing private field comparator</strong> inside the processor class.</p>

## CsvBeanProcessor

**Use it as a parent for other processors implementations**

<p>Contains default implementations for reading csv file and handling supported export fileTypes.</p>
<p>Any child of this class can be used inside the main method as a converter. When a new converter is implemented, we can put it to
switch inside the main method and choose it by passing the dataType argument.</p>