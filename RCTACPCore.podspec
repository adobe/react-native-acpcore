require "json"
package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "RCTACPCore"
  s.version      = package["version"]
  s.summary      = "Core library for Adobe Experience Cloud SDK. Written and Supported by Adobe."
  s.author       = "Adobe Mobile SDK Team"
  s.homepage     = "https://github.com/adobe/react-native-acpcore"
  s.license      = "Apache 2.0 License"
  s.platforms    = { :ios => "10.0", :tvos => "10.0" }
  s.source       = { :git => "https://github.com/adobe/react-native-acpcore.git", :tag => "#{s.version}" }
  s.requires_arc = true
  s.dependency "React"
  s.source_files  = "ios/**/*.{h,m}"
  
  # s.ios.vendored_frameworks = 'ios/libs/ACPCore.xcframework', 'ios/libs/ACPIdentity.xcframework', 'ios/libs/ACPLifecycle.xcframework', 'ios/libs/ACPSignal.xcframework'
  s.dependency = pod "ACPCore"
  s.ios.frameworks = 'UIKit', 'SystemConfiguration', 'WebKit', 'UserNotifications'
  s.ios.library = 'sqlite3.0', 'c++', 'z'

  # s.tvos.vendored_frameworks = 'ios/libs/ACPCoreTV.xcframework', 'ios/libs/ACPIdentityTV.xcframework', 'ios/libs/ACPLifecycleTV.xcframework', 'ios/libs/ACPSignalTV.xcframework'
  s.tvos.frameworks = 'SystemConfiguration'
  s.tvos.library = 'sqlite3.0', 'c++', 'z'


end
