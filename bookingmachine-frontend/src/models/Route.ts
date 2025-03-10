import { TransportType } from "../enums/TransportType";

export interface Route {
    readonly departureCity: string;
    readonly arrivalCity: string;
    readonly transportType: TransportType;
}
