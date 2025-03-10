import { FC, useState } from "react";
import Button from "../UI/Button.tsx";
import Title from "../UI/Title.tsx";
import BookingService, { RefundResponse } from "../../services/BookingService.ts";
import Input from "../UI/Input.tsx";

interface RefundProps {
    callback: (result: RefundResponse) => void;
}

const RefundForm: FC<RefundProps> = ({ callback }) => {
    const [refundCode, setRefundCode] = useState("");

    const refundTicket = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();

        try {
            const result = await BookingService.refundBooking({
                refundCode: refundCode,
            });
            callback(result);
        } catch (error) {
            console.error("Ошибка при возврате билета:", error);
        }
    };

    return (
        <section className="py-10">
            <Title>Возврат билета</Title>
            <form className="flex flex-col items-center gap-4 bg-white p-4 pt-10 w-full max-w-md min-w-[100px] mx-auto">
                <Input
                    value={refundCode}
                    onChange={(e) => setRefundCode(e.target.value)}
                    type="text"
                    placeholder="Код возврата"
                />
                <Button onClick={refundTicket} className="p-2 w-full">
                    Вернуть билет
                </Button>
            </form>
        </section>
    );
};

export default RefundForm;
