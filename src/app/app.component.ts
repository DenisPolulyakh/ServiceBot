import { Component } from '@angular/core';
import { OAuthService } from 'angular2-oauth2/oauth-service';

import '../style/app.scss';

interface AppState {
  counter: number;
}

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [OAuthService],
})

export class AppComponent {
  url = 'https://github.com/preboot/angular2-webpack';

  constructor() {

  }
}
