import { Trip } from "../models/Trip.ts";
import { FC } from "react";
import { formatterWithTime } from "../utils/date.ts";
import { TransportType } from "../enums/TransportType.ts";
import Button from "./UI/Button.tsx";
import { useNavigate } from "react-router-dom";
import { BOOKING_PAGE } from "../utils/consts.ts";

interface TripCardProps {
    trip: Trip;
    noButton?: boolean;
    className?: string;
}

const transportTypeColors: Record<TransportType, string> = {
    [TransportType.BUS]: "bg-yellow-100 text-yellow-600",
    [TransportType.AIRLINE]: "bg-red-100 text-red-600",
    [TransportType.RAILWAY]: "bg-blue-100 text-blue-600",
    [TransportType.MIX]: "bg-green-100 text-green-600",
};

const getTransportTypeColors = (transportType: TransportType): string => {
    return transportTypeColors[transportType];
};

const TripCardItem: FC<TripCardProps> = ({ trip, noButton = false, className }) => {
    const navigate = useNavigate();
    const transportType: TransportType = TransportType[trip.route.transportType as unknown as keyof typeof TransportType];

    const redirectToBooking = () => {
        navigate(BOOKING_PAGE, {
            state: {
                stateTrip: trip,
            },
        });
    };

    return (
        <div className={`w-full bg-white rounded-xl shadow-md overflow-hidden border px-4 py-6 sm:px-6 sm:py-8 ${className}`}>
            <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4">
                <span className="text-lg sm:text-xl font-semibold mb-2 sm:mb-0">
                    {trip.route.departureCity} → {trip.route.arrivalCity}
                </span>
                <span className={`text-sm px-3 py-1 rounded ${getTransportTypeColors(transportType)}`}>
                    {transportType}
                </span>
            </div>

            <div className="text-gray-700 text-sm sm:text-base mb-4">
                <p><strong>Отправление:</strong> {formatterWithTime.format(trip.departureTime)}</p>
                <p><strong>Прибытие:</strong> {formatterWithTime.format(trip.arrivalTime)}</p>
            </div>
            <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center text-sm sm:text-lg text-gray-600">
                <p className="mb-2 sm:mb-0">Мест: <strong>{trip.availableSeatsTotal}</strong></p>
                <p className="text-xl sm:text-2xl font-bold text-green-600">{trip.price} ₽</p>
            </div>

            {!noButton && (
                <Button onClick={redirectToBooking} className="mt-6 w-full py-2 sm:py-3">
                    Купить билет
                </Button>
            )}
        </div>
    );
};

export default TripCardItem;