import Header from "../components/Header.tsx";
import {Outlet} from "react-router-dom";

const MainLayout = () => {
    return (
        <>
            <div className="px-10 sm:px-30">
                <Header/>
                <Outlet />
            </div>
        </>
    );
};

export default MainLayout;