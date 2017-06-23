# APITools
Some classes to work with RESTFul APIs easily

# Usage
In build.gradle file add these lines
```
repositories {
    maven { url 'https://jitpack.io' }
}
```

```
dependencies {
    compile 'com.github.molaeiali:apitools:v1.1.1'
}
```
In your project Create a class that extends **APIManager**, make it singleton, if you want add additional headers in requests add them in defaultHeaders List in your class constructor;
