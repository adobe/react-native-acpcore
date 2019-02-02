
# adobe-mobile-marketing-core

## Getting started

`$ npm install adobe-mobile-marketing-core --save`

### Mostly automatic installation

`$ react-native link adobe-mobile-marketing-core`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `adobe-mobile-marketing-core` and add `RCTACPCore.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRCTACPCore.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.adobe.marketing.mobile.RCTACPCorePackage;` to the imports at the top of the file
  - Add `new RCTACPCorePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':adobe-mobile-marketing-core'
  	project(':adobe-mobile-marketing-core').projectDir = new File(rootProject.projectDir, 	'../node_modules/adobe-mobile-marketing-core/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':adobe-mobile-marketing-core')
  	```


## Usage
```javascript
import RCTACPCore from 'adobe-mobile-marketing-core';

// TODO: What to do with the module?
RCTACPCore;
```
  