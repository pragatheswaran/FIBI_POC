import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { usermodule } from './usermodule';
const platform = platformBrowserDynamic();
platform.bootstrapModule(usermodule);

