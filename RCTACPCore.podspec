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
  s.dependency "ACPGriffonBeta", "~> 0.0"
  s.dependency "ACPTargetVEC", "~> 2.0'"
  s.dependency "ACPTarget", "~> 2.1'"
  s.dependency "ACPAudience", "~> 2.0'"
  s.dependency "ACPAnalytics", "~> 2.0'"
  s.dependency "ACPUserProfile", "~> 2.0'"
  s.dependency "ACPCore", "~> 2.0"
  s.frameworks = 'UIKit', 'SystemConfiguration', 'WebKit', 'UserNotifications'
  s.library = 'sqlite3.0', 'c++', 'z'
end
