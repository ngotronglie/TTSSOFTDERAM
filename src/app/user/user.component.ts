import { Component, computed, signal } from '@angular/core';
import { DUMMY_USERS } from '../dummy-users';


const randomIndex = Math.floor(Math.random() * DUMMY_USERS.length);
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent {
  selectedUser = signal(DUMMY_USERS[randomIndex])

  imagePath = computed(()=> {
    return '/assets/user/' + this.selectedUser().avatar;
  })


  // getImagePath() {
  //   return 'public/images/' + this.selectedUser().arvatar;

  // }


  onSelectUser() {
    const randomIndex = Math.floor(Math.random() * DUMMY_USERS.length);
    this.selectedUser.set(DUMMY_USERS[randomIndex]);
  }

}
