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
  s.dependency "ACPCore"

  s.source_files  = "ios/**/*.{h,m}"
  

end
