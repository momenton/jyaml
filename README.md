[![stability-stable](https://img.shields.io/badge/stability-stable-green.svg)](https://github.com/emersion/stability-badges#stable)

# JYAML - JMeter Markup Language
 Jyaml /ˈjaməl/ is a framework for designing and building Jmeter scripts with YAML.

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
git clone https://github.com/momenton/jyaml.git
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
gradlew buildJmx --args="--i ./samples/basic/TestPlan.yaml --o ./samples/basic/TestPlan.jmx"
```
