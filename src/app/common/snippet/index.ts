import { Component, OnInit, Input, ElementRef } from '@angular/core';

@Component({
  selector: 'snippet-component',
  template: require('./index.html'),
  styles: [require('./style.scss')]
})
export class SnippetComponent implements OnInit {
  @Input() color: string = '#fff'
  @Input() height: string = '20px'

  constructor(private element: ElementRef) {

  }

  ngOnInit() {
  }

}
