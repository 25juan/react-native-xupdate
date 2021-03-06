import { Platform,Alert,Linking } from "react-native";
import { NativeModules } from 'react-native';
import Device from "react-native-device-info" ;

const { RNXupdate } = NativeModules;

/**
 * 安卓冷更新传url,
 * ios冷更新传appstore 的id
 * @param url
 * @param appId
 */
function update(url,iosAppId,iosAppStore) {
  if(Platform.OS === 'android'){
    RNXupdate.update(url)
  }else {
    updateIOS(iosAppId,iosAppStore);
  }
}
function updateIOS(iosAppId,iosAppStore) {
  const url = `https://itunes.apple.com/cn/lookup?id=${iosAppId}`
  fetch(url).then(res=>res.json()).then(result => {
    const info = (result.results || [])[0] || null;
    Device.getVersion().then(currVersion=> {
      if(!info || !currVersion) {
        return
      }
      const version = +(info.version.split('.').join(""))
      currVersion = +(currVersion.split('.').join(""))
      if(currVersion < version){
        Alert.alert(
          '版本更新',
          '检测到有新版本,是否要更新?',
          [
            {text: '稍后再说', style: 'cancel'},
            {text: '立即安装', onPress: () => {
                Linking.openURL(iosAppStore)
              }
            },
          ],
          { cancelable: false }
        )
      }


    })




  })
}

export default {
  update
};
