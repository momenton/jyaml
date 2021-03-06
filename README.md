
[![language](https://img.shields.io/badge/language-java-blue.svg)](https://img.shields.io/badge/language-java-blue.svg)
[![codecov](https://codecov.io/gh/momenton/jyaml/branch/master/graph/badge.svg)](https://codecov.io/gh/momenton/jyaml)
[![Build Status](https://travis-ci.org/momenton/jyaml.svg?branch=master)](https://travis-ci.org/momenton/jyaml)
[![stability-stable](https://img.shields.io/badge/stability-stable-green.svg)](https://github.com/emersion/stability-badges#stable)

# JYAML - Build JMeter scripts with YAML
Jyaml /ˈjaməl/ is a framework for designing and building Apache JMeter scripts with YAML. It is a 100% pure Java application built
on JMeter's API and uses YAML to represent JMeter's JMX scripts in a human-readable form.

Jyaml improves the experience of JMeter by providing a simple way to build and configure composable JMeter scripts. It removes the complexity of working with JMeter's scripts, no more managing difficult merge conflicts and adds the ability to build composable scripts. Automation is simplified as you just need to work with YAML instead of having to configure JMeter's jmx files.

Check out the [wiki](https://github.com/momenton/jyaml/wiki) for more details, getting started and technical details. 

## Prerequistes

* Java 1.8+
* Gradle
* Git

## Building
Clone JYAML repo:
```
git clone https://github.com/momenton/jyaml.git
cd jyaml
```

To compile:
```
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
