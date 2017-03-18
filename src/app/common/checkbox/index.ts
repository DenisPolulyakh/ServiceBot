import { Component, OnInit, Input, Output, ElementRef, EventEmitter } from '@angular/core';

@Component({
  selector: 'checkbox-cmponent',
  template: require('./index.html'),
  styles: [require('./style.scss')]
})
export class CheckboxComponent implements OnInit {

  @Input() id: string;
  @Input() disabled: boolean = false;
  @Input() checked: boolean = false;
  @Input() name: string;
  @Output() checkedChange = new EventEmitter();

  constructor(private element: ElementRef) {

  }

  _change = (value) => {
    this.checked = (this.checked) ? false : true;
    this.checkedChange.emit(this.checked);
   }

  ngOnInit() {
   }

}
