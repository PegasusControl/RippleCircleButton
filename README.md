# Ripple Circle Button

A simple Button with ripple circle effects.

*Inspired on* **MusixMatch** - *Identify Lyrics Module* 

*Created with* **Kotlin**

## Requirements

Android Studio **3.0+**

The library requires Android **API Level 16+**

## Demo

[_Current versión 1.0.0_](https://github.com/PegasusControl/RippleCircleButton/releases/download/1.0.0/sample_1.0.0.apk)

[**Releases**](https://github.com/PegasusControl/RippleCircleButton/releases)

## Screenshots

|Animation constant|Example|Animation constant|Example|
|      :---:       | :---: |       :---:      | :---: |
|**progressive**|![Alt Text](https://media.giphy.com/media/26n6DTQmK6CBvM4eY/giphy.gif)|**randomly**|![Alt Text](https://media.giphy.com/media/l1J3R3n6K1D2kWVvG/giphy.gif)|
|**expandingAndDisappearing**|![Alt Text](https://media.giphy.com/media/l1J3pcXDrrHs8bfsk/giphy.gif)|**collapsingAndAppear**|![Alt Text](https://media.giphy.com/media/26n6AYQbIFugHzFYY/giphy.gif)|

## Features 

- Option to set **main circle color and size**

- Option to set an **icon inside** the main circle

- Option to set **icon size**

- Option to set custom animation :
  - **progressive**
  - **randomly**
  - **expandingAndDisappearing**
  - **collapsingAndAppear**
 
- Option to set **animation duration**
  
- Option to set **main and secondary circle colors**

- Option to set **number of circles** to show while the animation is running

## Next Features 

- Avoid required attributes

- Change animation on running

- Change features on running

## How to use

Add the next dependency into your `build.gradle`:

```gradle
dependencies {
  compile 'mx.com.pegasus:RippleCircleButton:1.0.0'
}
```

And then you can add `mx.com.pegasus.RippleCircleButton` view on any XML layout file.

```xml
<mx.com.pegasus.RippleCircleButton
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
  circlesButton:secondaryCirclesAnimation="expandingAndDisappearing"/>
```

## Changelog

Please see the [Changelog](https://github.com/PegasusControl/RippleCircleButton/wiki/Changelog) page to see what's recently changed

## Credits

Credits to Matxh Music app for inspire on the idea.

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
