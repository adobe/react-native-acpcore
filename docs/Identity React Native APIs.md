# Identity React Native APIs

Identity API's exposed in React Native

| API                            | Android | iOS  | Cavats                                                       |
| ------------------------------ | ------- | ---- | ------------------------------------------------------------ |
| `extensionVersion()`           | ✅       | ✅    | None                                                         |
| `registerExtension()`          | ✅       | ✅    | None                                                         |
| `syncIdentifiers(...)`         | ✅       | ✅    | None                                                         |
| `syncIdentifiers(...)`         | ✅       | ✅    | None                                                         |
| `syncIdentifier(...)`          | ⚠️       | ⚠️    | Javascript does not alow overloading, so this function has been renamed to `syncIdentifiersWithAuthState(...)` |
| `appendVisitorInfoForURL(...)` | ✅       | ✅    | None                                                         |
| `getIdentifiers(...)`          | ✅       | ✅    | None                                                         |
| `getExperienceCloudId(...)`    | ✅       | ✅    | None                                                         |

