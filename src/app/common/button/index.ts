import { Component, OnInit, Input, ElementRef } from '@angular/core';

export enum ButtonState {
  NORMAL,
  LOADING,
  FINISH,
  ERROR
};

@Component({
  selector: 'button-save',
  template: require('./button.component.html'),
  styles: [require('./style.scss')]
})
export class ButtonSaveComponent implements OnInit {
  private static instances = {};
  static timeoutMessages: number = 1000;
  @Input() id: string;
  @Input() disabled: boolean = false;
  withError: boolean = false;

  @Input() textSuccess: string = 'Saved...';
  @Input() textError: string = 'Error...';
  @Input() type: string = 'primary';
  state: ButtonState = ButtonState.NORMAL;

  private buttonState = ButtonState;

  static setState(id: string, state: ButtonState) {
    let d: ButtonSaveComponent = ButtonSaveComponent.instances[id];
    d.state = state;

    if (state === ButtonState.ERROR) {
      d.withError = true;
    }
    if (state === ButtonState.ERROR || state === ButtonState.FINISH) {
      setTimeout(() => {
        d.state = ButtonState.NORMAL;
      }, ButtonSaveComponent.timeoutMessages);
    }
  }


  onClick() {
    if (this.withError) this.withError = false;
  }

  constructor(private element: ElementRef) {

  }

  ngOnInit() {
    ButtonSaveComponent.instances[this.id] = this;
  }

}
