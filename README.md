[![stability-unstable](https://img.shields.io/badge/stability-unstable-yellow.svg)](https://github.com/emersion/stability-badges#unstable)

# JYAML - JMeter Markup Language
 Jyaml /ˈjaməl/ is a markup language for Jmeter allowing Jmeter scripts to be described with YAML.

 It is designed to make Jmeter scripts easier to
 * modularize and template
 * integrate into build pipelines
 * simplify management and workflow of scripts

## Prerequistes

* Java 1.8+
* Gradle

## Building

To compile:
```
git clone https://github.com/dreading/jyaml.git
cd jyaml
gradlew clean compileJava
```

To run tests:
```
gradlew clean test
```

To run code coverage:
```
gradlew clean jacocoTestReport
```

## Usage
To create a Jmeter script from an yaml:
```
gradlew buildJmx --input pnv.yaml --output pnv.jmx
```