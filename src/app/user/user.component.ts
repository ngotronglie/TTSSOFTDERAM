import { Component, computed, EventEmitter, input, Input, Output, signal } from '@angular/core';
import { DUMMY_USERS } from '../dummy-users';


const randomIndex = Math.floor(Math.random() * DUMMY_USERS.length);
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent {
  @Input({required: true}) id!: string;
  @Input({required: true}) avatar!: string;
  @Input({required: true}) name!: string;
  @Output() select = new EventEmitter<string>();

  // avatar = input.required<string>();
  // name = input.required<string>();
  // name = input('name');

  get imagePath(){
    return `assets/user/${this.avatar}`;
  }

  // imagePath = computed(() => {
  //   return `assets/user/${this.avatar}`;
  // })



  onSelectUser() {
    // this.avatar.set(DUMMY_USERS[randomIndex].avatar);
    this.select.emit(this.id);
  }

}
