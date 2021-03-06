<center><img src="https://github.com/Green-Cloud-Project/GreenCloud/blob/master/Document/img/greencloud_logo.png" width="400" ></center>
 
# GreenClou Project for Android 

[![CircleCI](https://circleci.com/gh/Green-Cloud-Project/GreenCloud.svg?style=svg)](https://circleci.com/gh/Green-Cloud-Project/GreenCloud) [![StackShare](http://img.shields.io/badge/tech-stack-0690fa.svg?style=flat)](https://stackshare.io/bentley/greencloud)
 
## Introduction
GreenCloud 는 갑자기 우산이 필요할 때 손쉽게 우산을 대여할 수 있는 공유우산 프로젝트입니다. 

프로젝트는'비오는 날 갑작스럽게 우산이 필요할 때, 우산을 구매하는 방법 이외에 다른 방법이 왜 없을까?'라는 의문에서 시작되었습니다.

외국 사례를 찾아보니, 유럽/미국/일본과 같은 선진국에서는
이러한 불편함을 해결하기 위해서 '공유우산' 형태로 서비스를 제공하고 있습니다.
한국에서도 지하철역/지자체/카페 에서 무료우산 형태로 진행했지만,
우산 회수의 문제 때문에 모두 실패하고 말았습니다.

아직 한국에서 제대로 자리잡은 공유경제 서비스는 많지 않지만,
우리 나라에 현실에 적합한 공유우산 서비스를 개발하고자 합니다.

## SDK Prerequisites
* Android 6.0 (API level 23) or later
* Java 8 or later
<div class="highlight"><pre class="codehilite"><code> //build.gradle(app)
android {
    defaultConfig {
        minSdkVersion 23
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
     }
} </code></pre></div>

## Feature

#### [로그인 기능 개발](./develop_note/Login.md)

## Library
### Jetpack AAC
* Data Binding
* Lifecycles
* LiveData
* Room
* ViewModel 
### Network
* [Retrofit2](https://github.com/square/retrofit)
* [Okhttp3](https://github.com/square/okhttp)
* [Gson](https://github.com/google/gson)
### Image Loading
* [Glide](https://github.com/bumptech/glide) 
### Log 
* [Timber](https://github.com/JakeWharton/timber)
### Debug 
* [Crashlytics](https://firebase.google.com/docs/crashlytics/get-started-android?hl=ko)
* [Leakcanary](https://github.com/square/leakcanary)
* [Stetho](https://github.com/facebook/stetho)
### Asynchronous Processing
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
### Runtime Permission
* [Rxpermissions](https://github.com/tbruyelle/RxPermissions)
### Location Service
* [RxLocation](https://github.com/patloew/RxLocation) 
