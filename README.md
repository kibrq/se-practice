# Disease Akinator
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build](https://github.com/kibrq/se-practice/actions/workflows/ci.yml/badge.svg)](https://github.com/kibrq/se-practice/actions/workflows/ci.yml/badge.svg)

Disease Akinator is a web application that determines a person's disease based on a short survey similar to [Akinator](https://akinator.com/).

The app can also help you make an appointment with a doctor who specializes in certain diseases.

## Presentation

[More about the project](https://docs.google.com/presentation/d/1hjnhtnAE8K9_mGRJR7j4W_v9anCDi_qb_CSA3Jila_4/edit?usp=sharing).

## Disclaimer

This project is a part of a Software Engineering course at Higher School of Economics and is not likely to be implemented.

## Usage

To run the application use:
```bash
./gradlew run
```
To run all the tests use:
```basjh
./gradlew test
```

## Docker

To build the application and run tests with docker use:
```bash
docker build -t build-akinator -f dockerfile/build.Dockerfile .
```

To run the application first build the `akinator-minimal` docker image:
```bash
docker build -t akinator-minimal -f dockerfile/minimal.Dockerfile .
```
Then run the `akinator-minimal` image:
```bash
docker run -it akinator-minimal
```


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Contributors

Kirill Brilliantov [kibrq](https://github.com/kibrq)

Maxim Sukhodolskii [maxuh14](https://github.com/maxuh14)

Ruslan Salkaev [salkaevruslan](https://github.com/salkaevruslan)

## License
[Apache-2.0 License](https://choosealicense.com/licenses/apache-2.0/)
