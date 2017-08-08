# Ripple Circle Button

A simple Button with ripple circle effects.

*Inspired on* **MusixMatch** - *Identify Lyrics Module* 

*Created with* **Kotlin**

## Requirements

Android Studio **3.0+**

The library requires Android **API Level 16+**

## Demo

*Under construction*

## Screenshots

*Under construction*

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

- Option to set **circles number** to show while the animation is running

## Next Features 

- Avoid required attributes

- Change animation on running

- Change features on running

## Usage

Add the next dependency into your `build.gradle`:

**Actually, you need download de project and import as a module, because the dependency is not in maven**

```gradle
dependencies {
  compile 'mx.com.pegasus:RippleCircleButton:1.0.0'
}
```

And then you can add the `mx.com.pegasus.RippleCircleButton` view on any XML layout file.

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

*Under construction*

## Credits

*Under construction*

## License

*Under construction*
