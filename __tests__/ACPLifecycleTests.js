import { NativeModules } from 'react-native';
import ACPLifecycle from '../js/ACPLifecycle';

describe('ACPLifecycle', () => {

  test('extensionVersion is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPLifecycle, 'extensionVersion');
    await ACPLifecycle.extensionVersion();
    expect(spy).toHaveBeenCalled();
  });

  test('registerExtension is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPLifecycle, 'registerExtension');
    await ACPLifecycle.registerExtension();
    expect(spy).toHaveBeenCalled();
  });
});
