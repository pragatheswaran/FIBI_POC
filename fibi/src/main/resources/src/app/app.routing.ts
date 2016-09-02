import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';

import { InvestmentsComponent } from './investments/investments.component';
import { HomeComponent } from './home/home.component';

const appRoutes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'investments', component: InvestmentsComponent },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  }
];

export const routing = RouterModule.forRoot(appRoutes);


