jest.mock('NativeModules', () => ({
  ACPCore: {
    extensionVersion: jest.fn(() => new Promise(resolve => resolve()))
  },
  ACPIdentity: {
    extensionVersion: jest.fn(() => new Promise(resolve => resolve())),
    registerExtension: jest.fn()
  },
  ACPLifecycle: {
    extensionVersion: jest.fn(() => new Promise(resolve => resolve())),
    registerExtension: jest.fn()
  },
  ACPSignal: {
    extensionVersion: jest.fn(() => new Promise(resolve => resolve())),
    registerExtension: jest.fn()
  },
}));
