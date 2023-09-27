import { CityDTO } from "./city-dto.model";

export class StopDTO {
    id: number;
    city: CityDTO;
    street: string;

    constructor(
        id: number,
        city: CityDTO,
        street: string
    ){
       this.id = id;
       this.city = city;
       this.street = street;
    }
  }