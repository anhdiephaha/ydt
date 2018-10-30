import { Component } from '@angular/core';

import '../assets/app.css';
import { User } from './_models';

@Component({
    moduleId: module.id.toString(),
    // tslint:disable-next-line:component-selector
    selector: 'app',
    templateUrl: 'app.component.html'
})

export class AppComponent {
    currentUser: User;

    constructor() {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }
 }
