# common-testutil
![Build status](https://circleci.com/gh/Mystes/common-testutil.svg?style=shield&circle-token=1d26db62821d6a3f03e9780657db6af6757e4fd2)
Common-testutil is a set of helper classes for XSLT transformations, XSD validations and running XPATH expressions against XML.

## Features

XSLT
 * Transforming XML with XSLT
 * Supports defining the encoding for transformation

XSD
 * Validating XML against XSD

XPATH
 * Getting a single/multiple strings that matches given XPATH expression and input XML
 * Getting a list of nodes that matches given XPATH expression and input XML

Other
 * Reading classpath resources to InputStream
 * Transforming InputStream to String and the other way around.

## Usage

For more detailed instructions how to use helper classes, please read the javadoc documentation. Javadoc documentation can be generated in the following way:

1) Clone this project

2) Run ```mvn site```

3) Open javadoc document in path: ```target/site/apidocs/index.html``

## Technical Requirements

* Java 6 or higher

## [License](LICENSE)

Copyright &copy; 2016 [Mystes Oy](http://www.mystes.fi). Licensed under the [Apache 2.0 License](LICENSE).
