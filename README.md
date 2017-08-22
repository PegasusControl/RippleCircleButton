# Ripple Circle Button

A simple Button with ripple circle effects.

*Inspired on* **MusixMatch** - *Identify Lyrics Module* 

*Created with* **Kotlin**

## Requirements

Android Studio **3.0+**

The library requires Android **API Level 16+**

## Demo

[_Current versión 1.1.6_](https://github.com/PegasusControl/RippleCircleButton/releases/download/1.1.6/sample-1.1.6.apk)

[**Releases**](https://github.com/PegasusControl/RippleCircleButton/releases)

## Screenshots

|Animation constant|Example|Animation constant|Example|
|      :---:       | :---: |       :---:      | :---: |
|**progressive**|![Alt Text](https://media.giphy.com/media/26n6DTQmK6CBvM4eY/giphy.gif)|**randomly**|![Alt Text](https://media.giphy.com/media/l1J3R3n6K1D2kWVvG/giphy.gif)|
|**expandAndDisappear**|![Alt Text](https://media.giphy.com/media/l1J3pcXDrrHs8bfsk/giphy.gif)|**collapseAndAppear**|![Alt Text](https://media.giphy.com/media/26n6AYQbIFugHzFYY/giphy.gif)|

## Features 

- Option to set **main circle color and size**

- Option to set an **icon inside** the main circle

- Option to set **icon size**

- Option to set custom animation :
  - **progressive**
  - **randomly**
  - **expandAndDisappear**
  - **collapseAndAppear**
 
- Option to set **animation duration**
  
- Option to set **main and secondary circle colors**

- Option to set **number of circles** to show while the animation is
running

## What is Next

- *Create aar for use library without write explicitly dependencies* (we are
depending of suppport of
**[fat-aar-plugin](https://github.com/Vigi0303/fat-aar-plugin)** or
**[android-fat-aar](https://github.com/adwiv/android-fat-aar)** on
**Android and Gradle 3.0**)


## How to import

### Gradle

Add the next repository into your `build.gradle` file (Project):

```gradle
repositories {
    maven {
        url 'https://dl.bintray.com/pegasuscontrol/RippleCircleButton/'
    }
    //...
}
```

and then add the next dependencies into your `build.gradle` file (Module):

```gradle
dependencies {
    implementation 'io.reactivex.rxjava2:rxkotlin:2.1.0'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
    implementation 'com.mikhaellopez:circularimageview:3.0.2'

    implementation 'mx.com.pegasus:ripple-circle-button:1.1.6'
}
```

## How to use

### XML

Compile and finally you can add `mx.com.pegasus.RippleCircleButton` view on any XML layout file:

```xml
<mx.com.pegasus.RippleCircleButton
    android:id="@+id/rippleCircleButton"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="center"

    circlesButton:mainCircleSize="100dp"
    circlesButton:mainCircleColor="#CCFFFFFF"

    circlesButton:mainCircleBackgroundImage="@drawable/ic_power_settings_new_blue_700_48dp"
    circlesButton:mainCircleBackgroundImageSize="72dp"

    circlesButton:secondaryCirclesNumber="3"
    circlesButton:secondaryCirclesColor="#88FFFFFF"

    circlesButton:animationDuration="1700"
    circlesButton:secondaryCirclesAnimation="expandAndDisappear"/>
```

### JAVA

```java
RippleCircleButton rippleCircleButton = findViewById(R.id.rippleCircleButton);
rippleCircleButton.setMainCircleSize(100f); //Value is in DPs
rippleCircleButton.setMainCircleColor(Color.parseColor("#CCFFFFFF"));

rippleCircleButton.setMainCircleBackgroundImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_power_settings_new_blue_700_48dp));
rippleCircleButton.setMainCircleBackgroundImageSize(100f); //Value is in DPs

rippleCircleButton.setSecondaryCirclesNumber(3);
rippleCircleButton.setSecondaryCirclesColor(Color.parseColor("#88FFFFFF"));

rippleCircleButton.setAnimationDuration(1700); //Value is in Milliseconds
rippleCircleButton.setSecondaryCirclesAnimation(RippleCircleButton.EXPAND_AND_DISAPPEAR_ANIMATION);
```

### KOTLIN

```
rippleCircleButton.mainCircleSize = 100f //Value is in DPs
rippleCircleButton.mainCircleColor = Color.parseColor("#CCFFFFFF")

rippleCircleButton.mainCircleBackgroundImage = ContextCompat.getDrawable(baseContext, R.drawable.ic_power_settings_new_blue_700_48dp)
rippleCircleButton.mainCircleBackgroundImageSize = 100f //Value is in DPs

rippleCircleButton.secondaryCirclesNumber = 3
rippleCircleButton.secondaryCirclesColor = Color.parseColor("#88FFFFFF")

rippleCircleButton.animationDuration = 1700 //Value is in Milliseconds
rippleCircleButton.secondaryCirclesAnimation = RippleCircleButton.EXPAND_AND_DISAPPEAR_ANIMATION
```

## Additional methods

There are two methods for animation control:

- startAnimation()
- stopAnimation()

## Changelog

Please see the [Changelog](https://github.com/PegasusControl/RippleCircleButton/wiki/Changelog) page to see what's recently changed

## Credits

[Matxh Music](https://play.google.com/store/apps/details?id=com.musixmatch.android.lyrify) app for inspire on the idea.

[@lopspower](https://github.com/lopspower) and his [@CircularImageView](https://github.com/lopspower/CircularImageView) library

[RxAndroid](https://github.com/ReactiveX/RxAndroid) library

[RxKotlin](https://github.com/ReactiveX/RxKotlin) library

Credits too for [@AngeeelD](https://github.com/angeeeld) by develop this library.

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
