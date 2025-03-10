import {instance} from "../utils/api.ts";
import {Trip} from "../models/Trip.ts";

export interface BaseSearchRequest {
    departureCity?: string;
    arrivalCity?: string;
    departureTime?: string;
    arrivalTime?: string;
    transportType?: string;
}


class TripService {
    async getAllTrips(): Promise<Trip[]> {
        const response = await instance.get<Trip[]>("/trips")
        return response.data;
    }

    async getTripById(tripId: number): Promise<Trip> {
        const response = await instance.get<Trip>("/trips/search", {
            params: {
                "tripId": tripId
            }
        })
        return response.data;
    }

    async getTripsWithFilter(request: BaseSearchRequest): Promise<Trip[]> {
        const response = await instance.get<Trip[]>("/trips/filter", {
            params: request
        })
        return response.data;
    }

    async getGroupedTrips(request: BaseSearchRequest): Promise<Record<string, Record<string, Trip[]>>> {
        const response = await instance.get<Record<string, Record<string, Trip[]>>>("/trips/filter/grouped", {
            params: request
        })
        return response.data;
    }

    async getSeats(tripId: number): Promise<number[]> {
        const response = await instance.get<number[]>("/trips/seats", {
            params: {
                "tripId": tripId
            }
        })
        return response.data;
    }

}

export default new TripService();