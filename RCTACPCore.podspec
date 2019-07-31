require "json"
package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "RCTACPCore"
  s.version      = package["version"]
  s.summary      = "Use Adobe Experience SDKs with React Native"
  s.author       = "Adobe"

  s.homepage     = "https://github.com/adobe/react-native-acpcore"

  s.license      = "Apache License"
  s.platform     = :ios

  s.source       = { :git => "https://github.com/adobe/react-native-acpcore.git", :tag => "#{s.version}" }

  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true

  s.dependency "React"
  s.frameworks = 'UIKit', 'SystemConfiguration', 'WebKit', 'UserNotifications'
  s.library = 'sqlite3.0', 'c++', 'z'
end
