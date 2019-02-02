# Core React Native APIs

Core API's exposed in React Native

| API                                      | Android | iOS  | Cavats                                                       |
| ---------------------------------------- | ------- | ---- | ------------------------------------------------------------ |
| `extensionVersion()`                     | ✅       | ✅    | None                                                         |
| `start()`                                | ✅       | ✅    | None                                                         |
| `setApplication(...)`                    | ❌       | N/A  | We make our call to `setApplication` in `RCTACPCoreModule.java` default constructor |
| `configureWithAppId(...)`                | ✅       | ✅    | None                                                         |
| `configureWithFileInPath(...)`           | ✅       | ✅    | None                                                         |
| `updateConfiguration(...)`               | ✅       | ✅    | None                                                         |
| `setLogLevel(...)`                       | ✅       | ✅    | None                                                         |
| `setPrivacyStatus(...)`                  | ✅       | ✅    | None                                                         |
| `getPrivacyStatus()`                     | ✅       | ✅    | None                                                         |
| `getSdkIdentities()`                     | ✅       | ✅    | None                                                         |
| `trackAction()`                          | ✅       | ✅    | None                                                         |
| `trackState()`                           | ✅       | ✅    | None                                                         |
| `setAdvertisingIdentifier()`             | ✅       | ✅    | None                                                         |
| `setPushIdentifier()`                    | ✅       | ✅    | None                                                         |
| `lifecycleStart(...)`                    | ✅       | ✅    | None                                                         |
| `lifecyclePause()`                       | ✅       | ✅    | None                                                         |
| `collectPii()`                           | ✅       | ✅    | None                                                         |
| `collectLaunchInfo()`                    | ✅       | ✅    | None                                                         |
| `setAppGroup(...)`                       | ❌       | ✅    | `setAppGroup()` is not found in `MobileCore.java`            |
| `downloadRules()`                        | ❌       | ✅    | `downloadRules()` is not found in `MobileCore.java`          |
| `dispatchEvent(...)`                     | ⚠️       | ⚠️    | Feasible, not implemented in POC                             |
| `dispatchEventWithResponseCallback(...)` | ⚠️       | ⚠️    | Feasible, not implemented in POC                             |
| `dispatchResponseEvent(...)`             | ⚠️       | ⚠️    | Feasible, not implemented in POC                             |
| `registerExtension()`                    | ❌       | ❌    | See "3rd Party Considerations" on the parent page.           |

