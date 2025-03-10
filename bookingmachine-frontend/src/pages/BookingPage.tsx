import { Alert } from "@mui/material";
import TripCardItem from "../components/TripCardItem.tsx";
import TicketModal from "../components/TicketModal.tsx";
import BookingForm from "../components/forms/BookingForm.tsx";
import {useBooking} from "../hooks/UseBooking.ts";

const BookingPage = () => {
    const { trip, booking, seats, alert, bookingRequest, setBookingRequest, proceedBooking } = useBooking();

    const shouldRenderTicketModal = trip && booking;
    const shouldRenderAlert = alert.show;
    const shouldRenderTripCard = trip;

    return (
        <main className="flex items-center flex-col">
            {shouldRenderTicketModal && <TicketModal trip={trip} booking={booking} />}
            {shouldRenderAlert && <Alert className="mb-5" severity="error">{alert.message}</Alert>}
            <BookingForm
                bookingRequest={bookingRequest}
                setBookingRequest={setBookingRequest}
                proceedBooking={proceedBooking}
                seats={seats}
            />
            {shouldRenderTripCard && <TripCardItem noButton className="border-0 my-10" trip={trip} />}
        </main>
    );
};

export default BookingPage;
