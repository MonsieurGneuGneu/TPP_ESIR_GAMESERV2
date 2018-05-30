import {Component, Input, Output, EventEmitter, HostListener} from '@angular/core';

@Component({
  selector: 'jhi-my-cellttt',
  template: "<div>{{value}}</div>",
  styleUrls: ['./cellttt.component.css']
})
export class CelltttComponent {
  @Input() value: string;
  @Output('userClick') click = new EventEmitter<string>();

  @HostListener('click')
  clickHandler() {
    this.click.emit('');
  }
}
