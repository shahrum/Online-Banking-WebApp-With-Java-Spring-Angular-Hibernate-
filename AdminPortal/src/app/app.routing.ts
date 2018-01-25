import {ModuleWithProviders} from '@angular/core';
import {Routes,RouterModule} from '@angular/router';

const appRoutes : Routes =
  [
    {
      path: '',
      redirectTo : 'login',
      pathMatch : 'full'
    }
    //{
     // path: '**',
      //component: PageNotFoundComponent
   // }
  ];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);