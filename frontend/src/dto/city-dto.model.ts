export class CityDTO {
    id: number;
    name: string;
    latitude: number;
    longitude: number;

    constructor(
        id: number,
        name: string,
        latitude: number,
        longitude: number
    ){
       this.id = id;
       this.name = name;
       this.latitude = latitude;
       this.longitude = longitude; 
    }
  }