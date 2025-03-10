import {Trip} from "../../models/Trip.ts";
import TripCardItem from "../TripCardItem.tsx";
import {FC} from "react";
import {baseFormatter} from "../../utils/date.ts";

interface GroupedTripsListProps {
    groupedTrips: Record<string, Record<string, Trip[]>>;
}

const GroupedTripsList: FC<GroupedTripsListProps> = ({groupedTrips}) => {
    return (
        <div className="container mx-auto py-6 w-full">
            {Object.entries(groupedTrips).map(([date, timeGroup]) => (
                <details key={date} className="mb-4 bg-gray-100 p-6 rounded-lg shadow w-full" open>
                    <summary className="cursor-pointer text-lg font-semibold">{baseFormatter.format(new Date(date))}</summary>
                    <div className="mt-3">
                        {Object.entries(timeGroup).map(([time, trips]) => (
                            <details key={time} className="mb-2 bg-white p-4 rounded-lg shadow w-full" open>
                                <summary className="cursor-pointer font-medium">{time}</summary>
                                {trips.map((trip, index) => (
                                    <TripCardItem className="mt-3" trip={trip} key={index}/>
                                ))}
                            </details>
                        ))}
                    </div>
                </details>
            ))}
        </div>
    );
};

export default GroupedTripsList;