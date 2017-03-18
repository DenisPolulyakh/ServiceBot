import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { ProductionContainer } from './pages/DictionaryList/container';
import { ImportContainer } from './pages/Import/container';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'list', component: ProductionContainer},
  { path: 'import', component: ImportContainer},
];

export const routing = RouterModule.forRoot(routes);
