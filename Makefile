PROJECT_NAME = ACPCore

setup:
	(npm install)
	(cd ios && pod deintegrate || true && pod install)

clean:
	(rm -rf android/build && rm -rf ios/build)
	(cd android && ./gradlew clean)
	(cd ios && xcodebuild clean -workspace RCT${PROJECT_NAME}.xcworkspace -scheme RCT${PROJECT_NAME})

build-android:
	(cd android && ./gradlew build -x lint)

build-ios: setup
	(cd ios && xcodebuild build -workspace RCT${PROJECT_NAME}.xcworkspace -scheme RCT${PROJECT_NAME})

build-sample-android:
	(cd sample/ACP*Sample/android && ./gradlew assembleRelease)

build-sample-ios:
	(cd sample/ACP*Sample/ios && xcodebuild build -project ACPCoreSample.xcodeproj -scheme ACPCoreSample CODE_SIGN_IDENTITY="" CODE_SIGNING_REQUIRED="NO" CODE_SIGNING_ALLOWED="NO")

run-tests:
	jest --testPathIgnorePatterns sample/ node_modules/ --modulePathIgnorePatterns sample/ --runInBand

run-tests-locally: setup
	./node_modules/.bin/jest --testPathIgnorePatterns sample/ node_modules/ --modulePathIgnorePatterns sample/

copy-to-sample:
	cd sample/ACP*Sample/ && sh copy-changes-to-sample.sh

# fetches the latest iOS & Android SDK and put them in the project
update-libs:
	rm -rf acp-sdks # clean if needed
	git clone https://github.com/Adobe-Marketing-Cloud/acp-sdks
	cp -a acp-sdks/iOS/${PROJECT_NAME}/ ios/libs/ # copy iOS lib
	sh update-android-sdk.sh
	rm -rf acp-sdks
