import { Component, OnInit } from '@angular/core';

import { User } from '../_models/index';
import { UserService } from '../_services/index';

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit {
    currentUser: User;
    users: User[] = [];

    constructor(private userService: UserService) {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

    ngOnInit() {
        this.loadAllUsers();
    }

    deleteUser(id: number) {
        // tslint:disable-next-line:semicolon
        this.userService.delete(id).subscribe(() => { this.loadAllUsers()});
    }

    private loadAllUsers() {
        this.userService.getAll().subscribe(users => { this.users = users; });
        console.log(this.users);
    }
}
