import { Component, Input, Output, EventEmitter, HostListener } from '@angular/core';

@Component({
  selector: 'jhi-celld',
  templateUrl: './celld.component.html',
  styleUrls: ['./celld.component.css']
})
export class CelldComponent {
  @Input() value: string;
  @Output('userClick') click = new EventEmitter<string>();

  @HostListener('click')
  clickHandler() {
    this.click.emit('');
  }
}
