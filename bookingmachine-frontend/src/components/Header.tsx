import {FC} from 'react'
import {NavLink} from 'react-router-dom'
import {HOME_PAGE, REFUND_PAGE} from '../utils/consts'

const Header: FC = () => {
    return (
        <header className="pt-10">
            <nav className="flex flex-wrap justify-center gap-10 items-center sm:justify-between sm:gap-0">
                <NavLink to={HOME_PAGE}
                         className="text-white text-lg font-bold bg-blue-600 p-3 rounded-lg rotate-0 sm:-rotate-4">BookingMachine</NavLink>
                <NavLink to={REFUND_PAGE} className="hover:text-black transition-colors text-gray-500 text-lg">Возврат
                    билета</NavLink>
            </nav>
        </header>
    );
};

export default Header;