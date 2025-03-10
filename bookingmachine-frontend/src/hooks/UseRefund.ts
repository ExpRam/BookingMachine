import { useState } from "react";
import { RefundResponse } from "../services/BookingService.ts";

export const useRefund = () => {
    const [showAlert, setShowAlert] = useState(false);
    const [result, setResult] = useState(false);

    const proceedResult = (response: RefundResponse) => {
        setResult(response.success);
        setShowAlert(true);
    };

    const onClose = () => setShowAlert(false);

    return { showAlert, result, proceedResult, onClose };
};
