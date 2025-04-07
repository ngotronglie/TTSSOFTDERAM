import { Component, computed, Input, signal } from '@angular/core';
import { DUMMY_USERS } from '../dummy-users';


const randomIndex = Math.floor(Math.random() * DUMMY_USERS.length);
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent {

  @Input({required: true}) avatar!: string;
  @Input({required: true}) name!: string;
  get imagePath(){
    return `assets/user/${this.avatar}`;
  }

  onSelectUser() {

  }

}
