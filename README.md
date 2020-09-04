
# react-native-xupdate

## Getting started

`$ yarn add react-native-xupdates`

### Mostly automatic installation

`$ react-native link react-native-xupdates`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.dou.xupdate.RNXupdatePackage;` to the imports at the top of the file
  - Add `new RNXupdatePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-xupdate'
  	project(':react-native-xupdate').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-xupdate/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-xupdate')
  	```


## Usage
```javascript
import RNXupdate from 'react-native-xupdate';

RNXupdate;
```

在MainApplication 的onCreate方法中初始化xupdate:
```
RNXupdateModule.initUpdate(this,getPackageName(), this);
```

使用时:
```
RNXupdate.update(jsonUrl);
json={
    "Code": 0,
  "Msg": "",
  "UpdateStatus": 2,
  "VersionCode": 4,
  "VersionName": "1.0.1",
  "ModifyContent": "新版本发布。",
  "DownloadUrl": "xxx",
  "ApkSize": 8408,// MB
  "ApkMd5": "xxx"
}
```
