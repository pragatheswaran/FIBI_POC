import {
  Component
} from '@angular/core';

import {NgClass} from '@angular/common';

@Component({
  moduleId: module.id,
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
  directives: [NgClass]
})
export class AppComponent {
  title = 'Easytree';
  isOn = true;
  isDropdownMenuOpen = false;
  isUserDropdownmenuOpen = false;
  toggleHamClass() {
    this.isOn = !this.isOn;
  }
  toggleDropdownMenu() {
    this.isDropdownMenuOpen = !this.isDropdownMenuOpen;
  }
  toggleUserDropdownMenu() {
    this.isUserDropdownmenuOpen = !this.isUserDropdownmenuOpen;
  }
}
