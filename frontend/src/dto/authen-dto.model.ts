
export class AuthenDTO {
    role: string;
    access_token: string;
    refresh_token: string;

    constructor(
        role: string,
        access_token: string,
        refresh_token: string
    ){
       this.role = role;
       this.access_token = access_token;
       this.refresh_token = refresh_token;
    }
  }