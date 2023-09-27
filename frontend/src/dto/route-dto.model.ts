export class RouteDTO {
    id: number;
    name: string;
    routeNumber: number;
    stopIds: number[];
    distance: number;
    duration: string;
    busId: number;
  
    constructor(
      id: number,
      name: string,
      routeNumber: number,
      stopIds: number[],
      distance: number,
      duration: string,
      busId: number
    ) {
      this.id = id;
      this.name = name;
      this.routeNumber = routeNumber;
      this.stopIds = stopIds;
      this.distance = distance;
      this.duration = duration;
      this.busId = busId;
    }
  }
  