import {FC} from 'react';
import ItemsList from "../UI/ItemsList.tsx";
import TripCardItem from "../TripCardItem.tsx";
import {Trip} from "../../models/Trip.ts";

interface TripsListProps {
    trips: Trip[];
}

const TripsList: FC<TripsListProps> = ({trips}) => {
    return (
        <ItemsList>
            {trips.map((trip, index) => (
                <TripCardItem trip={trip} key={index}/>
            ))}
        </ItemsList>
    );
};

export default TripsList;