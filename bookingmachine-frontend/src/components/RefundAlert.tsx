import {Alert} from "@mui/material";
import {FC} from "react";

interface AlertProps {
    success: boolean;
    onClose: () => void;
}

const RefundAlert: FC<AlertProps> = ({success, onClose}) => {
    function selectAlert(result: boolean) {
        if (result) {
            return (
                <Alert severity="success" onClose={onClose}>
                    Вы успешно вернули билет!
                </Alert>
            );
        }

        return (
            <Alert severity="error" onClose={onClose}>
                Что-то пошло не так... Код оказался недействительным!
            </Alert>
        );
    }

    return (
        selectAlert(success)
    );
};

export default RefundAlert;