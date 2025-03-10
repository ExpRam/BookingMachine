import { Trip } from "./Trip";

export interface Booking {
    readonly trip: Trip;
    readonly firstName: string;
    readonly lastName: string;
    readonly email: string;
    readonly seatNumber: number;
    readonly refundCode?: string;
}
