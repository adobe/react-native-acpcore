require "json"
package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "RCTACPCore"
  s.version      = package["version"]
  s.summary      = "Core library for Adobe Experience Cloud SDK. Written and Supported by Adobe."
  s.author       = "Adobe Mobile SDK Team"

  s.homepage     = "https://github.com/adobe/react-native-acpcore"

  s.license      = "Apache 2.0 License"
  s.platform      = :ios, '10.0'

  s.source       = { :git => "https://github.com/adobe/react-native-acpcore.git", :tag => "#{s.version}" }

  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true

  s.dependency "React"
  s.frameworks = 'UIKit', 'SystemConfiguration', 'WebKit', 'UserNotifications'
  s.library = 'sqlite3.0', 'c++', 'z'
  s.vendored_libraries = 'ios/libs/libACPCore_iOS.a', 'ios/libs/libACPIdentity_iOS.a', 'ios/libs/libACPLifecycle_iOS.a', 'ios/libs/libACPSignal_iOS.a'
end
