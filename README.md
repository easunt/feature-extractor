<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [URL Features](#url-features)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Roadmap](#roadmap)


<!-- ABOUT THE PROJECT -->
## About The Project

[![Overview][product-screenshot]](images/overview.png)

This is a feature extractor module for supervised learning based phishing detection.
Phishing attacks will be detected by classfication model trained in machine learning.
Classification model will be trained with feature value extracted by featractor and will use them when detect attacks.

Feture extractor use 9 features based on url and it will use HTML based feautures.


### URL Features
* Number of '/' charactor in url.
* Length of Subdomain.
* Value of TTL when send ping.
* Ranking value in Alexa ranking system.
* LevenStein Distance with Google Suggestion(sub domain)
* LevenStein Distance with Google Suggestion(primary domain)
* WHOIS record value
* Number of bad terms in url. Bad terms defined using many research and TF-ITF algorithm.
* Number of prefix and suffix in url.



<!-- GETTING STARTED -->
## Getting Started

You can test easliy using IntelliJ and Spring boot initializer.

### Prerequisites

* IntelliJ
* JDK 8
* Spring Boot
* Gradle

### Installation

1. Clone the repo
```sh
git clone https://github.com/kdgiant174/feature_extractor.git
```
2. Import project in IntelliJ idea.
3. Build project using gradle.
4. Run project.


<!-- ROADMAP -->
## Roadmap

It will be connect with classification model [ovio supervised](https://github.com/kdgiant174/ovio_supervised). It will be serve using google chrome extension.