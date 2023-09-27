import { BusDTO } from "./bus-dto.model";
import { StopDTO } from "./stop-dto.model";

export class TripDTO {
    tripNumber: string;
    bus: BusDTO;
    departureTime: Date;
    arrivalTime: Date;
    startStop: StopDTO;
    finishStop: StopDTO;
    ticketPrice: number;

    constructor(
      tripNumber: string,
      bus: BusDTO,
      departureTime: Date,
      arrivalTime: Date,
      startStop: StopDTO,
      finishStop: StopDTO,
      ticketPrice: number
      ) {
          this.tripNumber = tripNumber;
          this.bus = bus;
          this.departureTime = departureTime;
          this.arrivalTime = arrivalTime;
          this.startStop = startStop;
          this.finishStop = finishStop;
          this.ticketPrice = ticketPrice;
        }
      }
      