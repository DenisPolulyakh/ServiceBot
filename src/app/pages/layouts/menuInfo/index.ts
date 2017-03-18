import { Component, OnInit, Input, Output } from '@angular/core';
import { Headers} from '@angular/http';

@Component({
  selector: 'menu-info-component',
  templateUrl: './index.html',
  styleUrls: ['./style.scss'],
})
export class MenuInfoComponent implements OnInit {
  @Input()  user: {};

  constructor() {

  }

  ngOnInit() {
  }

}
