
Pod::Spec.new do |s|
  s.name         = "RCTACPCore"
  s.version      = "1.0.0"
  s.summary      = "RCTACPCore"
  s.description  = <<-DESC
                  RCTACPCore
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "10.0"
  s.source       = { :git => "https://github.com/author/RCTACPCore.git", :tag => "master" }
  s.source_files  = "RCTACPCore/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "ACPCore"
  #s.dependency "others"

end
