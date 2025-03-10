import {FC, PropsWithChildren} from 'react';

const ItemsList: FC<PropsWithChildren> = ({children}) => {
    return (
        <ul className="flex flex-wrap gap-6 px-8 py-6 w-full">
            {children}
        </ul>
    );
};

export default ItemsList;