import {FC, PropsWithChildren} from 'react';

const Title: FC<PropsWithChildren> = ({children}) => {
    return (
        <h1 className="text-center font-bold text-3xl text-gray-800">
            {children}
        </h1>
    );
};

export default Title;