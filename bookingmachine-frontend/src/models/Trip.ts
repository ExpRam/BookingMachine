import { Route } from "./Route";

export interface Trip {
    readonly id: number;
    readonly route: Route;
    readonly departureTime: Date;
    readonly arrivalTime: Date;
    readonly maxSeats: number;
    readonly availableSeatsTotal: number;
    readonly price: number;
}
