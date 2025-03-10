import Button from "../UI/Button.tsx";
import {TakeBookingRequest} from "../../services/BookingService.ts";
import Input from "../UI/Input.tsx";

interface BookingFormProps {
    bookingRequest: TakeBookingRequest;
    setBookingRequest: (request: TakeBookingRequest) => void;
    proceedBooking: (e: React.MouseEvent) => void;
    seats: number[];
}

const BookingForm = ({ bookingRequest, setBookingRequest, proceedBooking, seats }: BookingFormProps) => {
    return (
        <section className="bg-white p-6 w-full max-w-md min-w-[100px] mx-auto rounded-2xl shadow-lg">
            <h2 className="text-xl font-semibold mb-4">Оформление билета</h2>
            <form className="flex flex-col gap-5">
                <Input
                    value={bookingRequest.email}
                    onChange={e => setBookingRequest({ ...bookingRequest, email: e.target.value })}
                    type="email"
                    placeholder="Email"
                />
                <Input
                    value={bookingRequest.firstName}
                    onChange={e => setBookingRequest({ ...bookingRequest, firstName: e.target.value })}
                    type="text"
                    placeholder="Имя"
                />
                <Input
                    value={bookingRequest.lastName}
                    onChange={e => setBookingRequest({ ...bookingRequest, lastName: e.target.value })}
                    type="text"
                    placeholder="Фамилия"
                />
                <select
                    value={bookingRequest.seatNumber}
                    onChange={e => {
                        const value = e.target.value === "random" ? undefined : parseInt(e.target.value);
                        setBookingRequest({ ...bookingRequest, seatNumber: value });
                    }}
                    className="w-full border border-gray-300 p-2 rounded-xl focus:ring-blue-500"
                >
                    <option value="random">Случайное место</option>
                    {seats.map(seat => (
                        <option key={seat} value={seat}>{seat}</option>
                    ))}
                </select>
                <Button onClick={proceedBooking} className="w-full p-2 hover:bg-blue-700 transition">
                    Оформить
                </Button>
            </form>
        </section>
    );
};

export default BookingForm;
