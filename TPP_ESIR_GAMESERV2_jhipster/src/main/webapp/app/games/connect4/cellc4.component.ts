import { Component, Input, Output, EventEmitter, HostListener } from '@angular/core';

@Component({
  selector: 'jhi-cellc4',
  templateUrl: './cellc4.component.html',
  styleUrls: ['./cellc4.component.css']
})
export class Cellc4Component {
  @Input() value: string;
  @Output('userClick') click = new EventEmitter<string>();

  @HostListener('click')
  clickHandler() {
    this.click.emit('');
  }
}
