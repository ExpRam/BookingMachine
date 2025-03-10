import { useState } from "react";
import { Trip } from "../models/Trip.ts";
import TripService, { BaseSearchRequest } from "../services/TripService.ts";
import { SearchFormFields } from "../components/forms/SearchForm.tsx";

export const useFetchTrips = () => {
    const [trips, setTrips] = useState<Trip[]>([]);
    const [groupedTrips, setGroupedTrips] = useState<Record<string, Record<string, Trip[]>>>({});
    const [isLoaded, setIsLoaded] = useState<boolean>(false);

    const createFinalSearchRequest = (searchRequest: SearchFormFields): BaseSearchRequest => {
        return {
            ...(searchRequest.departureCity && { departureCity: searchRequest.departureCity }),
            ...(searchRequest.arrivalCity && { arrivalCity: searchRequest.arrivalCity }),
            ...(searchRequest.departureDate && { departureTime: `${searchRequest.departureDate}T${searchRequest.departureTime}` }),
            ...(searchRequest.arrivalDate && { arrivalTime: `${searchRequest.arrivalDate}T${searchRequest.arrivalTime}` }),
            ...(searchRequest.transportType && { transportType: searchRequest.transportType }),
        };
    };

    const fetchTrips = async (searchRequest: SearchFormFields) => {
        setIsLoaded(false);

        const baseSearchRequest: BaseSearchRequest = createFinalSearchRequest(searchRequest);

        try {
            if (searchRequest.grouped) {
                const groupedTripsData = await TripService.getGroupedTrips(baseSearchRequest);
                setGroupedTrips(groupedTripsData);
                setTrips([]);
            } else {
                const tripsData = await TripService.getTripsWithFilter(baseSearchRequest);
                setTrips(tripsData);
                setGroupedTrips({});
            }
        } catch (error) {
            console.error("Ошибка при загрузке поездок:", error);
            setTrips([]);
            setGroupedTrips({});
        } finally {
            setIsLoaded(true);
        }
    };

    return { trips, groupedTrips, isLoaded, fetchTrips };
};