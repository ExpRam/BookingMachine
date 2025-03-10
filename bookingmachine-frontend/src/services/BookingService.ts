import {instance} from "../utils/api.ts";
import {Booking} from "../models/Booking.ts";

export interface TakeBookingRequest {
    tripId: number;
    firstName: string;
    lastName: string;
    email: string;
    seatNumber?: number;
}


export interface RefundRequest {
    refundCode: string;
}

export interface RefundResponse {
    success: boolean;
}


class BookingService {
    async takeBooking(request: TakeBookingRequest): Promise<Booking> {
        const response = await instance.post<Booking>("/booking", request)
        return response.data;
    }

    async refundBooking(request: RefundRequest): Promise<RefundResponse> {
        const response = await instance.delete<RefundResponse>("/booking", {
            data: request
        })
        return response.data;
    }
}

export default new BookingService();