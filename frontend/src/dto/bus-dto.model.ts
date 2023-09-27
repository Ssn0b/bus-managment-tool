export class BusDTO {
    id: string;
    brand: string;
    seats: number;
    carNumber: string;
    driverId: string;
    busStatus: string;
    photoUrl: string;

    constructor(
        id: string,
        brand: string,
        seats: number,
        carNumber: string,
        driverId: string,
        busStatus: string,
        photoUrl: string
    ){
       this.id = id;
       this.brand = brand;
       this.seats = seats;
       this.carNumber = carNumber;
       this.driverId = driverId;
       this.busStatus = busStatus;
       this.photoUrl = photoUrl; 
    }
  }