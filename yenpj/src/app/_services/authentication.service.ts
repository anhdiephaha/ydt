import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
// import { Response } from '@angular/http';
import { Headers, RequestOptions, Response, Http} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class AuthenticationService {
    private headers = new Headers({'Content-Type': 'application/json'});
    constructor(private http: Http) { }
    login(username: string, password: string) {
        const cpHeaders = new Headers({ 'Content-Type': 'application/json' });
        const options = new RequestOptions({ headers: cpHeaders });
        return this.http.post('http://localhost:8081/api/auth/signin', { username: username, password: password },
        {headers: this.headers})
            .map((response: Response) => {
                // login successful if there's a jwt token in the response
                const token = response.json() && response.json()['accessToken'];
                if (token) {
                    // store username and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify({ username: username, token: token }));

                    // return true to indicate successful login
                    return true;
                } else {
                    // return false to indicate failed login
                    return false;
                }
            });
    }

    getToken(): String {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
        const token = currentUser && currentUser.token;
        return token ? token : '';
      }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }
}
