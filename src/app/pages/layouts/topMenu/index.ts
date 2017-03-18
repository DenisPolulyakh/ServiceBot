import { Component, OnInit, Input, Output } from '@angular/core';
import { Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'top-menu-component',
  templateUrl: './index.html',
  styleUrls: ['./style.scss'],
})
export class TopMenuComponent implements OnInit {
  @Input()  user: {};

  constructor(
    private router: Router,
    private activatedRoute:ActivatedRoute) {

  }

  ngOnInit() {
  }

}
