export const HOME_PAGE = '/';
export const REFUND_PAGE = '/refund';
export const BOOKING_PAGE = '/booking';
export const NOT_FOUND_PAGE = '*';

export const ERROR_MESSAGES: Record<string, string> = {
    InvalidEmailException: "Указанный email некорректный!",
    EmailAlreadyOnTripException: "Данный email уже используется в этом рейсе!",
    NoSeatsForTripException: "В этом рейсе нет свободных мест!",
    SeatAlreadyTakenException: "Это место уже занято!"
} as const;
