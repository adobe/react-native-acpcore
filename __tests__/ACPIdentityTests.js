import { NativeModules } from 'react-native';
import ACPIdentity from '../js/ACPIdentity';

describe('ACPIdentity', () => {

  test('extensionVersion is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'extensionVersion');
    await ACPIdentity.extensionVersion();
    expect(spy).toHaveBeenCalled();
  });

  test('registerExtension is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPIdentity, 'registerExtension');
    await ACPIdentity.registerExtension();
    expect(spy).toHaveBeenCalled();
  });
});
