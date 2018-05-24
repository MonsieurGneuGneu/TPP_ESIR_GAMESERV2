import {Component, Input, Output, EventEmitter, HostListener} from '@angular/core';

@Component({
  selector: 'jhi-my-cell',
  template: "<div>{{value}}</div>",
  styles: [
    `div {height: 90px; width: 90px; background-color: skyblue; float: left; margin: 0 4px 4px 0; color: black;}`
  ]
})
export class CellComponent {
  @Input() value: string;
  @Output('userClick') click = new EventEmitter<string>();

  @HostListener('click')
  clickHandler() {
    this.click.emit('');
  }
}
