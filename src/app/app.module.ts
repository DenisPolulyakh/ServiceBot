import { NgModule, ApplicationRef } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule, JsonpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { Ng2DropdownModule  } from 'ng2-material-dropdown';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import {SimpleNotificationsModule} from 'angular2-notifications';

// container
import { ProductionContainer  } from './pages/DictionaryList/container';
import { ImportContainer } from './pages/Import/container';

// components
import { DictionaryListComponent } from './pages/DictionaryList/dictionary-list';
import { ImportComponent } from './pages/import/import-form';

import { MenuInfoComponent } from './pages/layouts/menuInfo';
import { TopMenuComponent } from './pages/layouts/topMenu';

import { ButtonSaveComponent } from './common/button';
import { CheckboxComponent } from './common/checkbox';
import { SnippetComponent } from './common/snippet';

import { routing } from './app.routing';
import { MomentModule } from 'angular2-moment';

import { ReplaceletterPipe } from './utils';
import {Ng2PaginationModule} from 'ng2-pagination';
import { removeNgStyles, createNewHosts } from '@angularclass/hmr';

import {SelectModule} from 'ng2-select/ng2-select'
import { DropdownModule } from './common/dropdown/dropdown.module';
// directive
import {Autosize} from './common/textArea';
import { OAuthModule } from 'angular-oauth2-oidc';

import { LocalStorageModule } from 'angular-2-local-storage';
import {ModalModule} from 'ngx-modal';
import { TagInputModule } from 'ng2-tag-input';

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    JsonpModule,
    FormsModule,
    Ng2DropdownModule,
    routing,
    MomentModule,
    SelectModule,
    DropdownModule,
    OAuthModule,
    SimpleNotificationsModule,
    LocalStorageModule.withConfig({
      prefix: 'ls',
      storageType: 'localStorage'
    }),
    ModalModule,
    TagInputModule,
    Ng2PaginationModule
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    ProductionContainer,
    ImportContainer,
    DictionaryListComponent,
    ImportComponent,
    MenuInfoComponent,
    TopMenuComponent,
    ButtonSaveComponent,
    CheckboxComponent,
    SnippetComponent,
    ReplaceletterPipe,
    Autosize,
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(public appRef: ApplicationRef) {}
  hmrOnInit(store) {
    console.log('HMR store', store);
  }
  hmrOnDestroy(store) {
    let cmpLocation = this.appRef.components.map(cmp => cmp.location.nativeElement);
    // recreate elements
    store.disposeOldHosts = createNewHosts(cmpLocation);
    // remove styles
    removeNgStyles();
  }
  hmrAfterDestroy(store) {
    // display new elements
    store.disposeOldHosts();
    delete store.disposeOldHosts;
  }
}
