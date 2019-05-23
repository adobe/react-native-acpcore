import { NativeModules } from 'react-native';
import ACPCore from '../js/ACPCore';

describe('ACPCore', () => {

  test('extensionVersion is called', async () => {
    const spy = jest.spyOn(NativeModules.ACPCore, 'extensionVersion');
    await ACPCore.extensionVersion();
    expect(spy).toHaveBeenCalled();
  });

});
