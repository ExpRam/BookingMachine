import {FC, useRef, useState} from 'react';
import Modal from "./UI/Modal.tsx";
import Title from "./UI/Title.tsx";
import TripCardItem from "./TripCardItem.tsx";
import Button from "./UI/Button.tsx";
import {useReactToPrint} from "react-to-print";
import {Trip} from "../models/Trip.ts";
import {Booking} from "../models/Booking.ts";
import {useNavigate} from "react-router-dom";
import {HOME_PAGE} from "../utils/consts.ts";

interface TicketModalProps {
    trip: Trip,
    booking: Booking
}

const TicketModal: FC<TicketModalProps> = ({trip, booking}) => {
    const navigate = useNavigate();
    const [modal, setModal] = useState(true);
    const contentRef = useRef<HTMLDivElement>(null);
    const reactToPrintFn = useReactToPrint({ contentRef });

    const close = () => {
        setModal(false);
        navigate(HOME_PAGE);
    }

    return (
        <Modal visible={modal} closeModal={close}>
            <div className="max-w-220 pt-20 border" ref={contentRef}>
                <Title>
                    Билет
                </Title>
                <div className="px-10 mt-20">
                    <TripCardItem trip={trip} noButton={true} className="border-0"/>
                </div>
                <div className="mt-20 ml-20 text-[25px]">
                    <p className="break-words">Имя: <strong>{booking.firstName}</strong></p>
                    <p className="break-words">Фамилия: <strong>{booking.lastName}</strong></p>
                    <p className="break-words">Почта: <strong>{booking.email}</strong></p>
                    <p className="break-words">Место: <strong>{booking.seatNumber}</strong></p>
                    <p className="my-10 break-words">ВАЖНО! ВЕРНУТЬ БИЛЕТ МОЖНО ТОЛЬКО ПО КОДУ ВОЗВРАТА: <strong>{booking.refundCode}</strong></p>
                </div>

            </div>
            <div className="mt-10 flex flex-row gap-10 justify-center">
                <Button className="w-auto p-5" onClick={() => reactToPrintFn()}>Распечатать/Скачать</Button>
                <Button className="w-auto p-5" onClick={close}>Закрыть</Button>
            </div>
        </Modal>
    );
};

export default TicketModal;