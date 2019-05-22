
package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "react-native-acpcore"
  s.version      = package['version']
  s.summary      = package['description']
  s.homepage     = "https://github.com/adobe/react-native-acpcore"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = "Adobe"
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/adobe/react-native-acpcore", :tag => "v1.0.2" }

# Platform dependencies
  s.frameworks = "UIKit", "SystemConfiguration", "WebKit",
                 "UserNotifications"

  s.vendored_libraries = "ios/libs/libACPCore_iOS.a",
                         "ios/libs/libACPIdentity_iOS.a",
                         "ios/libs/libACPLifecycle_iOS.a",
                         "ios/libs/libACPSignal_iOS.a"


  s.library = "c++", "z", "sqlite3.0"

  s.requires_arc = true
  s.source_files  = "ios/**/*.{h,m}"

  s.dependency "React"

end

  