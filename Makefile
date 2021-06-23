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
	(cd sample/ACP*SampleApp/android && ./gradlew clean assembleRelease -x bundleReleaseJsAndAssets)

build-sample-ios:
	(cd sample/ACP*SampleApp/ios && pod update)
	(cd sample/ACP*SampleApp/ && npx react-native run-ios)

run-tests:
	jest --testPathIgnorePatterns sample/ node_modules/ --modulePathIgnorePatterns sample/ --runInBand

run-tests-locally: setup
	./node_modules/.bin/jest --testPathIgnorePatterns sample/ node_modules/ --modulePathIgnorePatterns sample/

copy-to-sample:
	(cd sample/ACP*SampleApp && make sync)
