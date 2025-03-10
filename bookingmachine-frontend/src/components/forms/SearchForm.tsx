import Button from "../UI/Button.tsx";
import Title from "../UI/Title.tsx";
import {FC, useState} from "react";

export interface SearchFormFields {
    departureCity: string,
    arrivalCity: string,
    departureDate: string,
    departureTime: string,
    arrivalDate: string,
    arrivalTime: string,
    transportType: string,
    grouped: boolean
}

interface SearchProps {
    doSearch: (searchRequest: SearchFormFields) => void;
}


const SearchForm: FC<SearchProps> = ({doSearch}) => {
    const [searchRequest, setSearchRequest] = useState<SearchFormFields>({
        departureCity: "",
        arrivalCity: "",
        departureDate: "",
        departureTime: "00:00",
        arrivalDate: "",
        arrivalTime: "23:59",
        transportType: "",
        grouped: false
    });

    const search = (e: React.MouseEvent) => {
        e.preventDefault();
        doSearch(searchRequest);
    }

    return (
        <section>
            <Title>Поиск предложений</Title>
            <form className="flex flex-col items-center gap-4 bg-white p-4 pt-10 mx-auto max-w-6xl">
                <div className="flex flex-wrap gap-1 justify-center items-center w-full">

                    <input type="text"
                           value={searchRequest.departureCity}
                           onChange={e => setSearchRequest({...searchRequest, departureCity: e.target.value.trim()})}
                           placeholder="Откуда"
                           className="border border-gray-200 py-5 px-3 rounded-0 min-[1376px]:rounded-3xl min-[1376px]:rounded-r-none w-40"
                           required/>

                    <input type="text"
                           value={searchRequest.arrivalCity}
                           onChange={e => setSearchRequest({...searchRequest, arrivalCity: e.target.value.trim()})}
                           placeholder="Куда"
                           className="border
                           border-gray-200 py-5 px-3 w-40"
                           required/>

                    <input type="date"
                           value={searchRequest.departureDate}
                           onChange={e => setSearchRequest({...searchRequest, departureDate: e.target.value})}
                           placeholder="Когда"
                           className="border
                           border-gray-200 py-5 px-3 w-50"/>

                    <input type="time"
                           value={searchRequest.departureTime}
                           onChange={e => setSearchRequest({...searchRequest, departureTime: e.target.value ? e.target.value : "00:00"})}
                           placeholder="Когда"
                           className="border
                           border-gray-200 py-5 w-25"/>

                    <input type="date"
                           value={searchRequest.arrivalDate}
                           onChange={e => setSearchRequest({...searchRequest, arrivalDate: e.target.value})}
                           placeholder="Обратно"
                           className="border
                           border-gray-200 py-5 px-3 w-50"/>

                    <input type="time"
                           value={searchRequest.arrivalTime}
                           onChange={e => setSearchRequest({...searchRequest, arrivalTime: e.target.value ? e.target.value : "23:59"})}
                           placeholder="Обратно"
                           className="border
                           border-gray-200 py-5 w-25"/>

                    <select
                        value={searchRequest.transportType}
                        onChange={e => setSearchRequest({...searchRequest, transportType: e.target.value})}
                        className="border border-gray-200 py-5 rounded-0 min-[1376px]:rounded-3xl min-[1376px]:rounded-l-none w-40">
                        <option value="">Любой транспорт</option>
                        <option value="BUS">Автобус</option>
                        <option value="RAILWAY">Ж/Д</option>
                        <option value="AIRLINE">Самолет</option>
                        <option value="MIX">Микс</option>
                    </select>
                </div>
                <div>
                    <input
                        checked={searchRequest.grouped}
                        onChange={e => setSearchRequest({...searchRequest, grouped: e.target.checked})}
                        type="checkbox"
                        id="grouped"
                        name="grouped"/>
                    <label htmlFor="grouped">Сгруппировать по дате/времени</label>
                </div>
                <Button onClick={search} className="p-5 w-50">
                    Найти билеты
                </Button>
            </form>
        </section>
    );
};

export default SearchForm;