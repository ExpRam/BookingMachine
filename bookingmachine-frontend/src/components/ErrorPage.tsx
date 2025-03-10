import { NavLink } from "react-router-dom";
import { HOME_PAGE } from "../utils/consts";

interface ErrorPageProps {
    code: string;
    message: string;
}

const ErrorPage = ({ code, message }: ErrorPageProps) => {
    return (
        <main className="flex items-center justify-center flex-col h-screen">
        <h1 className="text-9xl font-bold">{code}</h1>
            <p>{message}</p>
            <NavLink to={HOME_PAGE} className="hover:text-black transition-colors text-gray-500">
        Назад
        </NavLink>
        </main>
);
};

export default ErrorPage;
