import {ModuleWithProviders} from '@angular/core';
import {Routes,RouterModule} from '@angular/router';
import { LoginComponent } from './login/login.component';

const appRoutes : Routes =
  [
    {
      path: '',
      redirectTo : 'login',
      pathMatch : 'full'
    },
    {
      path:'login',
      component : LoginComponent
    }
    //{
     // path: '**',
      //component: PageNotFoundComponent
   // }
  ];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);