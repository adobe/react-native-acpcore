#import "AppDelegate.h"

#import <React/RCTBridge.h>
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>
#import <RCTACPCore/ACPCore.h>
#import <RCTACPCORE/ACPIdentity.h>
#import <RCTACPCORE/ACPLifecycle.h>
#import <RCTACPCORE/ACPSignal.h>


@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{

  RCTBridge *bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:launchOptions];
  RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:bridge
                                                   moduleName:@"ACPCoreSample"
                                            initialProperties:nil];

  rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];

  self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  UIViewController *rootViewController = [UIViewController new];
  rootViewController.view = rootView;
  self.window.rootViewController = rootViewController;
  [self.window makeKeyAndVisible];
  
  //AEP SDK registration.
  [ACPCore setLogLevel:ACPMobileLogLevelVerbose];
  [ACPCore configureWithAppId:@"yourAppId"];
  [ACPCore setWrapperType:ACPMobileWrapperTypeReactNative];
  [ACPIdentity registerExtension];
  [ACPLifecycle registerExtension];
  [ACPSignal registerExtension];

  const UIApplicationState appState = application.applicationState;
  [ACPCore start:^{
      // only start lifecycle if the application is not in the background
      if (appState != UIApplicationStateBackground) {
          [ACPCore lifecycleStart:nil];
      }
  }];
  
  return YES;
}

 - (NSURL *)sourceURLForBridge:(RCTBridge *)bridge
 {   
// #if DEBUG
//   return [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index" fallbackResource:nil];
// #else
   return [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
// #endif
 }

@end
