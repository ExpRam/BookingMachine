import SearchForm from "../components/forms/SearchForm.tsx";
import Title from "../components/UI/Title.tsx";
import TripsList from "../components/lists/TripsList.tsx";
import GroupedTripsList from "../components/lists/GroupedTripsList.tsx";
import {useFetchTrips} from "../hooks/UseFetchTrips.ts";

const IndexPage = () => {
    const { trips, groupedTrips, isLoaded, fetchTrips } = useFetchTrips();

    const renderResults = () => {
        if (!isLoaded) return null;

        if (trips.length === 0 && Object.keys(groupedTrips).length === 0) {
            return <Title>Рейсов не найдено</Title>;
        }

        return trips.length !== 0 ? <TripsList trips={trips} /> : <GroupedTripsList groupedTrips={groupedTrips} />;
    };

    return (
        <main className="py-20 flex flex-col items-center">
            <SearchForm doSearch={fetchTrips}/>
            {renderResults()}
        </main>
    );
}

export default IndexPage;
