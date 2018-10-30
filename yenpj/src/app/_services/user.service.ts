import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { User } from '../_models/index';
import { AuthenticationService } from '.';
import { Headers, Http, RequestOptions} from '@angular/http';

@Injectable()
export class UserService {
    private headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.authenticationService.getToken()
        });
    constructor(private http: HttpClient, private authenticationService: AuthenticationService) { }

    getAll() {
        return this.http.get<User[]>('http://localhost:8081/test/he', {headers: this.headers});
    }

    getById(id: number) {
        return this.http.get('/api/users/' + id);
    }

    create(user: User) {
        return this.http.post('/api/users', user);
    }

    update(user: User) {
        return this.http.put('/api/users/' + user.id, user);
    }

    delete(id: number) {
        return this.http.delete('/api/users/' + id);
    }
}
