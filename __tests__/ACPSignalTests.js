import { NativeModules } from 'react-native';
import ACPSignal from '../js/ACPSignal';

describe('ACPSignal', () => {

  test('extensionVersion is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPSignal, 'extensionVersion');
    await ACPSignal.extensionVersion();
    expect(spy).toHaveBeenCalled();
  });

  test('registerExtension is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPSignal, 'registerExtension');
    await ACPSignal.registerExtension();
    expect(spy).toHaveBeenCalled();
  });
});
