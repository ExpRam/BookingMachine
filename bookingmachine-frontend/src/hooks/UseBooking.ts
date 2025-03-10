import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { Trip } from "../models/Trip.ts";
import { Booking } from "../models/Booking.ts";
import TripService from "../services/TripService.ts";
import BookingService, { TakeBookingRequest } from "../services/BookingService.ts";
import { ERROR_MESSAGES, HOME_PAGE } from "../utils/consts.ts";

interface ErrorState {
    show: boolean;
    message: string;
}

export const useBooking = () => {
    const navigate = useNavigate();
    const { state } = useLocation();
    const { stateTrip } = state || {};

    const [trip, setTrip] = useState<Trip>();
    const [booking, setBooking] = useState<Booking>();
    const [seats, setSeats] = useState<number[]>([]);
    const [alert, setAlert] = useState<ErrorState>({ show: false, message: "" });

    const [bookingRequest, setBookingRequest] = useState<TakeBookingRequest>({
        tripId: stateTrip?.id || 0,
        firstName: "",
        lastName: "",
        email: "",
        seatNumber: undefined
    });

    useEffect(() => {
        if (!stateTrip) {
            navigate(HOME_PAGE);
            return;
        }
        fetchTrip();
    }, [stateTrip, navigate]);

    const fetchTrip = async () => {
        try {
            const result = await TripService.getSeats(stateTrip.id);
            setTrip({ ...stateTrip, availableSeatsTotal: result.length });
            setSeats(result);
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
        } catch (error) {
            navigate(HOME_PAGE);
        }
    };

    const validateBookingRequest = () => {
        const { email, firstName, lastName } = bookingRequest;
        if (!email || !firstName || !lastName) {
            setAlert({ show: true, message: "Необходимо указать корректный email, имя и фамилию!" });
            return false;
        }
        return true;
    };

    const handleApiError = (error: unknown) => {
        if (!axios.isAxiosError(error)) return;

        const errorType = error?.response?.data.error;
        const errorMessage = ERROR_MESSAGES[errorType] || "Неизвестная ошибка!";
        setAlert({ show: true, message: errorMessage });
    };

    const proceedBooking = async (e: React.MouseEvent) => {
        e.preventDefault();
        setAlert({ show: false, message: "" });

        if (!validateBookingRequest()) return;

        try {
            const result = await BookingService.takeBooking(bookingRequest);
            setBooking(result);
        } catch (error) {
            handleApiError(error);
        }
    };

    return {
        trip,
        booking,
        seats,
        alert,
        bookingRequest,
        setBookingRequest,
        proceedBooking
    };
};
