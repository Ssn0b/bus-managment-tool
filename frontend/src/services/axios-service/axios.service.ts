import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class AxiosService {
  private baseUrl = 'http://localhost:8080/api/v1';

  getBearerToken(): string | null {
    return window.localStorage.getItem('accessToken');
  }
  setBearerToken(token: string | null): void {
    if (token !== null) {
      window.localStorage.setItem("accessToken", token);
    } else {
      window.localStorage.removeItem("accessToken");
    }
  }

  constructor() {
    axios.defaults.baseURL = 'http://localhost:8080/api/v1';
    axios.defaults.headers.post['Content-Type'] = 'application/json';
  }

  request(method: string, url: string, data: any): Promise<any> {
    let headers: any = {};

    if (this.getBearerToken() !== null) {
        headers = {"Authorization": "Bearer " + this.getBearerToken()};
    }

    return axios({
        method: method,
        url: url,
        data: data,
        headers: headers
    });
}

}
